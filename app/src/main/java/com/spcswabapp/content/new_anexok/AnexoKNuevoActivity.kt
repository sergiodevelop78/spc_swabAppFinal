package com.spcswabapp.content.new_anexok
import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spcswabapp.R
import com.spcswabapp.content.new_anexok.adapter.AnexoKDataAdapter
import com.spcswabapp.content.new_anexok.database.dao.DBSergio
import com.spcswabapp.content.new_anexok.entities.SwabTipsAnalsisRiesgoEntity
import com.spcswabapp.content.new_anexok.entities.TipsAnalisisRiesgoEntity
import com.spcswabapp.database.DatabaseMain
import com.spcswabapp.database.entities.SwabReportEntity
import com.spcswabapp.databinding.ActivityAnexoKnuevoBinding
import com.spcswabapp.utils.disable
import com.spcswabapp.utils.hideKeyboard
import kotlinx.coroutines.*
import okhttp3.internal.notifyAll
import com.spcswabapp.base.ActivityViewBinding
import com.spcswabapp.content.new_order.NewOrderVM
import com.spcswabapp.databinding.ActivityNewOrderBinding
import com.spcswabapp.utils.showMessage
import com.spcswabapp.widgets.CustomProgressDialog

@Suppress("SpellCheckingInspection")

//class AnexoKNuevoActivity : AppCompatActivity(), AnexoKDataAdapter.OnItemClickListener {
class AnexoKNuevoActivity : AppCompatActivity(), AnexoKDataAdapter.OnItemClickListener {

    lateinit var binding: ActivityAnexoKnuevoBinding
    private lateinit var dbSergio : DBSergio
    private lateinit var db2 : DatabaseMain
    lateinit var progressDialog : CustomProgressDialog

    //private val itemsTipsAnalisisRiesgo = mutableListOf<AnexoKItemsModel>()
    private val itemsTipsAnalisisRiesgo = mutableListOf<SwabTipsAnalsisRiesgoEntity>()
    private val itemsTipsAnalisisRiesgo2 = mutableListOf<SwabTipsAnalsisRiesgoEntity>()

    private val itemsSeletected = mutableListOf<SwabTipsAnalsisRiesgoEntity>()

    private var ingresadosBD = listOf<SwabTipsAnalsisRiesgoEntity>()

    private lateinit var tipsAnalisisRiesgoEntity : TipsAnalisisRiesgoEntity

    private var idRegistroIncidencia : Int = 0
    var idEstadoSwab : Int = -1

    private var adapter = AnexoKDataAdapter(itemsTipsAnalisisRiesgo, this, idEstadoSwab)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityAnexoKnuevoBinding.inflate(layoutInflater)

        progressDialog = CustomProgressDialog(this)

        setContentView(binding.root)

        // Valores desde pantalla anterior
        idRegistroIncidencia = intent.getIntExtra("idRegistro",-1)
        val idEstadoSwab : Int = intent.getIntExtra("idEstadoSwab",-1)
        val idSwabReport : Int = intent.getIntExtra("idSwabReport",-1)

        Log.d("SERGIO", " -- idSwabReport = $idSwabReport")
        Log.d("SERGIO", " -- idEstadoSwab = $idEstadoSwab")

        binding.toolbar.title = intent.getStringExtra("title")
        ///

        //val db2 = Database()
        Log.d("SERGIO", "TEMP IDREGISTRO INCIDENCIA = $idRegistroIncidencia")

        adapter = AnexoKDataAdapter(itemsTipsAnalisisRiesgo, this, idEstadoSwab)
        binding.rvAnexoK.adapter = adapter
        binding.rvAnexoK.layoutManager = LinearLayoutManager(this)

        //binding.rvAnexoK.getLayoutManager()?.smoothScrollToPosition(binding.rvAnexoK, RecyclerView.State(), 5)

        binding.rvAnexoK.setNestedScrollingEnabled(false)

        // SOPORTE DE TOOLBAR
        setSupportActionBar(binding.toolbar)

        dbSergio = DBSergio.invoke(this)
        db2 = DatabaseMain.invoke(this)

        GlobalScope.launch {
            //llenarTablaDesdeBD()
            validarTipsRegistados(idRegistroIncidencia)
        }

        binding.toolbar.apply {
            var radiogroup = R.id.rgSiNo

            /* Validar estado de Swab para mostrar botones*/
            if (idEstadoSwab==3 ) {
                binding.imgSave.visibility = View.GONE

                /* SERGIO TEMPORAL PARA PRUEBAS */
                binding.imgSelectAll.visibility =View.GONE
                binding.imgVacios.visibility =View.GONE
                binding.imgSelectNone.visibility =View.GONE
                /* FIN SERGIO */
            }
            else{
                binding.imgSave.visibility = View.VISIBLE
                /* SERGIO TEMPORAL PARA PRUEBAS */
                binding.imgSelectAll.visibility =View.VISIBLE
                binding.imgVacios.visibility =View.VISIBLE
                binding.imgSelectNone.visibility =View.VISIBLE
                /* FIN SERGIO */
            }

            binding.imgSave.setOnClickListener {
                Log.e("SERGIO", "-- Anexo K - Clic en boton Guardar")
                val resp = submitForm()
                if (!resp) {
                    invalidForm()
                }
                else {
                    GlobalScope.launch {
                        // Borrar primero data en BD Local
                        db2.daoSwabTipsAnalisisRiesgo().deleteById(idRegistroIncidencia)
                        Log.e("SERGIO", "Data de BD Borrada -- idRegistroIncidencia = $idRegistroIncidencia ")

                        val lista = itemsTipsAnalisisRiesgo
                        // Guardar cambios en Modelo
                        var i : Int = 0
                        //var idRegistroIncidencia = idRegistro

                        while (i < lista.size) {
                            val idTipsAnalisisRiesgo = lista[i].idtips_analisisderiesgo
                            val estadoLista = lista[i].estado
                            val tipsAnalisisRiesgoText = lista[i].tips_analisisderiesgo
                            val grupoText = lista[i].grupo
                            val newModelo = SwabTipsAnalsisRiesgoEntity (
                                estado = estadoLista,
                                idregistro_incidencia = idRegistroIncidencia,
                                idtips_analisisderiesgo = idTipsAnalisisRiesgo,
                                tips_analisisderiesgo = tipsAnalisisRiesgoText,
                                grupo = grupoText
                            )
                            Log.e("Sergio", "imgSave - NewModelo=$newModelo")
                            db2.daoSwabTipsAnalisisRiesgo().insertar(newModelo)
                            //println("INSERTADO $i ")
                            i++
                        }

                        // Cambiar el estado de la OrdenSwab
                        Log.e("SERGIO", "### idSwabReport=$idSwabReport")
                        Log.e("SERGIO", "### Update Swab Reporte En Progreso -- idSwabReport=$idSwabReport")
                        db2.swabReportDao().updateEstadoProgreso(idSwabReport)

                        Log.e("SERGIO", "## Update Swab Incidenca $idRegistroIncidencia TieneAnexoK = 1")
                        db2.swabIncidencesDao().updateTieneAnexoK(idRegistroIncidencia)

                        val swabOrden = db2.swabReportDao().getById(idSwabReport)
                        Log.e("SERGIO", "XXX -- Data de BD Ingresada -- idRegistroIncidencia = $idRegistroIncidencia ")
                        Log.e("SERGIO", "XXX -- Swab Orden = $swabOrden Estado = "+swabOrden.idEstadoSwab)
                    }

                    progressDialog.message = "Guardando ..."
                    progressDialog.show()
                    hideKeyboard()
                    Handler(Looper.myLooper()!!).postDelayed({
                        showMessage("Datos guardados correctamente.")
                        progressDialog.hide()
                        finish()
                    }, 1500)

                }
            }

            binding.imgVacios.setOnClickListener {
                onUnClickSelectAll()
            }

            binding.imgSelectAll.setOnClickListener {

                /*progressDialog.message = "Marcando todos..."
                progressDialog.show()
                hideKeyboard()
                Handler(Looper.myLooper()!!).postDelayed({
                    showMessage("Seleccionados todos!")
                    progressDialog.hide()
                }, 1500)
                */
                onClickSelectAll()
            }


            binding.imgSelectNone.setOnClickListener {
                onClickSelectNone()
            }


        }
    }

    public fun submitForm() : Boolean{
        println("SERGIO -- ENTRANDO A FUNCION submitForm")
        var respuesta = true
        var i = 0
        val tamañoLista = itemsTipsAnalisisRiesgo.size
        while (i<tamañoLista) {
            if (!(itemsTipsAnalisisRiesgo[i].estado in 1..2))
                respuesta = false
            i++
        }
        println("SERGIO -- TAMAÑO LISTA = $tamañoLista")
        println("SERGIO -- REVISION LISTA RADIOBUTTON = $respuesta")
/*
        binding.rvAnexoK.  .helperText = validarRadioButton(binding.etPresion.text.toString())
        binding.mdContainer.helperText = validarRadioButton(binding.etMD.text.toString())

        val validPresion = binding.presionContainer.helperText==null
        val validMD = binding.mdContainer.helperText==null

        println("SERGIO -- validar campos")
        if (validPresion && validMD && validPist && validMant && validParada && validInicial
            && validFinal && validCorr && validPet && validAgua && validInicio && validTermino) {
            println("SERGIO -- Formulario OK")
            //guardarForm()
            return true
        }
        else {
            println("SERGIO -- Formulario ERROR")
            return false
        }


*/
        return respuesta
    }

    fun validarRadioButton(campoRadioButton: RadioButton) : String?{
        if (campoRadioButton.isChecked == false) {
            return "Debe ingresar valor a este campo."
        }
        return null
    }

    private fun invalidForm() {
        val mensaje = "Faltan llenar los campos requeridos"
        //Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        val dialogo = AlertDialog.Builder(this)

        dialogo.setTitle("Error")
            .setMessage(mensaje)
            .setPositiveButton("Ok") { _, _ ->
                // no hacer nada
            }
        dialogo.create()
        dialogo.show()
    }

    /*private fun getRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://spcswabapi.sybpruebas.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
        return retrofit
    }

    private fun getClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
        return client

    }
    */

    /*@SuppressLint("NotifyDataSetChanged")
    private fun cargarTipsAnalisisRiesgo() {
        CoroutineScope(Dispatchers.IO).launch {

            val accionEnvio = AccionEnvio("accion", "1")

            val call =
                getRetrofit().create(APIService::class.java).getAllTipsAnalisisRiesgo(accionEnvio)
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    var error = response?.error
                    var messageText = response?.message
                    itemsTipsAnalisisRiesgo.clear()
                    itemsSeletected.clear()
                    response?.data?.forEach { result ->
                        itemsTipsAnalisisRiesgo.add(result)
                    }

                    itemsTipsAnalisisRiesgo[0].estado = 1
                    adapter.notifyDataSetChanged()

                } else {
                    Log.d("Sergio", call.errorBody()!!.string())
                    Log.d("Sergio", call.message())

                    showError()
                }
            }
        }
    }
     */

    /* SERGIO */


    private suspend fun validarTipsRegistados(idRegistroIncidencia: Int) {
        println("SERGIO - validarTipsRegistados / idRegistroIncidencia $idRegistroIncidencia")
        println("SERGIO - ## Before itemsTipsAnalisisRiesgo, total = "+itemsTipsAnalisisRiesgo.size)
        itemsTipsAnalisisRiesgo.clear()
        println("SERGIO - Limpiada itemsTipsAnalisisRiesgo, total = "+itemsTipsAnalisisRiesgo.size)
        val totalRecords = db2.daoSwabTipsAnalisisRiesgo().getAllById(idRegistroIncidencia).size
        println("SERGIO - totalRecords = $totalRecords")
        if(totalRecords<=0) {
            llenarTablaDesdeBD(idRegistroIncidencia)
        }
        else {
            llenarTablaDesdeBDById(idRegistroIncidencia)
        }

    }

    private suspend fun llenarTablaDesdeBD(idRegistroIncidencia: Int) {
        println("SERGIO - llenarTablaDesdeBD sin ID $idRegistroIncidencia")

        // Borrar todos los datos de la tabla local

        db2.daoSwabTipsAnalisisRiesgo().deleteById(idRegistroIncidencia)
        itemsTipsAnalisisRiesgo.clear()
        val lstTips = db2.getTipsAnalisisRiesgoDao().getAll()
        var contador=0
        lstTips.forEach { result ->
            //println(contador)
            val tmp = SwabTipsAnalsisRiesgoEntity(
                idswab_tips_analisisderiesgo = 0,
                idregistro_incidencia = idRegistroIncidencia,
                idtips_analisisderiesgo = result.idtips_analisisderiesgo,
                tips_analisisderiesgo = result.tips_analisisderiesgo,
                grupo = result.grupo,
                estado = 0
            )
            db2.daoSwabTipsAnalisisRiesgo().insertar(tmp)
            itemsTipsAnalisisRiesgo.add(tmp)
            //println("SERGIO ** TMP CREADO = $tmp")
            contador++
        }
        println("SERGIO: Insertado array itemsTipsAnalisisRiesgo , tamaño =  "+itemsTipsAnalisisRiesgo.size)
        //println("TEMP 0: Insertada tabla room con ")
/*        val items = db2.daoSwabTipsAnalisisRiesgo().getAll()
        println("SERGIO ** items count = "+items.size)
        contador = 0
        items.forEach { result ->
            //println(contador)
            itemsTipsAnalisisRiesgo.add(result)
            contador++
        }
 */
        GlobalScope.launch(Dispatchers.Main) {
            adapter.notifyDataSetChanged()
        }
    }

    private suspend fun llenarTablaDesdeBDById(idRegistroIncidencia: Int) {
        println("SERGIO - llenarTablaDesdeBDById $idRegistroIncidencia")
        itemsTipsAnalisisRiesgo.clear()
        val items = db2.daoSwabTipsAnalisisRiesgo().getAllById(idRegistroIncidencia)
        var contador = 0
        items.forEach { result ->
            //println("SERGIO - IDRegistroIncidencia $idRegistroIncidencia /  idtips_analisisderiesgo = "+result.idtips_analisisderiesgo+
            //        "/ Estado = "+result.estado)
            itemsTipsAnalisisRiesgo.add(result)
            contador++
        }
        //println("TEMP 2 = " + itemsTipsAnalisisRiesgo.toString())
        //println("TEMP 3 SIZE ItemsTips = " + itemsTipsAnalisisRiesgo.size.toString())
        GlobalScope.launch(Dispatchers.Main) {
            adapter.notifyDataSetChanged()
        }

    }


    private fun showMessage(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun showError() {
        Toast.makeText(this, "Ocurrió un Error", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(position: Int) {
        //Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()

        /*val clickedItem = itemsTipsAnalisisRiesgo[position]
        itemsTipsAnalisisRiesgo[position].estado = 4

        val idSelected = clickedItem.idswab_tips_analisisderiesgo
        val idRegistroIncidencia = clickedItem.idregistro_incidencia
        val idTipsAnalisisRiesgo = clickedItem.idtips_analisisderiesgo
        val estadoSelected = clickedItem.estado

         */
        adapter.notifyItemChanged(position)

    }

    private fun onClickSelectAll() {
        println("SERGIO -- funcion onClickSelectAll")
        var i = 0
        var tamañoLista = itemsTipsAnalisisRiesgo.size
        while (i<tamañoLista) {
            itemsTipsAnalisisRiesgo[i].estado=1
            i++
        }
        adapter.notifyDataSetChanged()

    }

    private fun onUnClickSelectAll() {
        println("SERGIO -- funcion onClickSelectAll")
        var i = 0
        val tamañoLista = itemsTipsAnalisisRiesgo.size
        while (i<tamañoLista) {
            itemsTipsAnalisisRiesgo[i].estado=2
            i++
        }
        adapter.notifyDataSetChanged()
    }

    private fun onClickSelectNone() {
        println("SERGIO -- funcion onClickSelectNone")
        var i = 0
        val tamañoLista = itemsTipsAnalisisRiesgo.size
        while (i<tamañoLista) {
            itemsTipsAnalisisRiesgo[i].estado=0

            i++
        }
        adapter.notifyDataSetChanged()
    }


}

