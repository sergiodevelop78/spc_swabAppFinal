package com.spcswabapp.content.incidences.detail

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.spcswabapp.R
import com.spcswabapp.base.ActivityViewBinding
import com.spcswabapp.database.dao.PozosDao
import com.spcswabapp.database.dao.SwabReportDao
import com.spcswabapp.database.entities.PozosEntity
import com.spcswabapp.database.entities.SwabIncidencesEntity
import com.spcswabapp.databinding.ActivityIncidenceDetailBinding
import com.spcswabapp.sergiofunciones.SergioFunciones
import com.spcswabapp.utils.disable
import com.spcswabapp.utils.hideKeyboard
import com.spcswabapp.utils.showMessage
import java.text.SimpleDateFormat
import java.util.*

@Suppress("SpellCheckingInspection")
class IncidenceDetailActivity : ActivityViewBinding<ActivityIncidenceDetailBinding, IncidenceDetailVM>() {

    private lateinit var incidence : SwabIncidencesEntity
    private lateinit var etSelected : EditText
    private lateinit var pozosDao : PozosDao
    private lateinit var adapterSpinnerPozo: ArrayAdapter<PozosEntity>

    val pozosListArray =  mutableListOf<PozosListaLocal>()

    var sergioFunciones = SergioFunciones()

    lateinit var  bateriaList :  MutableList<String>
    var idPozoElegido : Int = 0
    override fun inflateLayout(layoutInflater: LayoutInflater) = ActivityIncidenceDetailBinding.inflate(layoutInflater)

    var isComplete = false
    var itemElegido: String? = null
    private var estadoEditable : Int = 0

    var idEstadoSwab : Int = 0

    private fun showAlertDialogo(titulo: String, mensaje: String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            // no hacer nada
        }
        builder.show()
    }

    override fun onBackPressed() {
        //Toast.makeText(this, "ATRAS", Toast.LENGTH_SHORT).show()
        showAlertDialogo("Error", "No puede salir sin haber registrado datos.\nSi quiere regresar a la ventana anterior, elimine esta orden.")
        //startActivity(Intent(this, NewOrderActivity::class.java))
    }



    override fun viewListeners() {
        val idRegistro = intent.getIntExtra("idRegistro", -1)
        isComplete = intent.getBooleanExtra("isComplete", false)
        idEstadoSwab = intent.getIntExtra("idEstadoSwab", -1)

        Log.e("SERGIO", "idEstadoSwab = $idEstadoSwab")


        viewModel.getIncidence(idRegistro)

        /* Sergio Pozos */
        viewModel.getPozos()

        /* Fin Sergio */

        binding.toolbar.title = intent.getStringExtra("title")

        //Toast.makeText(this, "HOLA", Toast.LENGTH_SHORT).show()

        val contexto = this

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            etSelected.setText(SimpleDateFormat("HH:mm").format(cal.time))
        }

        validadHelpersInicio()

        fun validarCampoTexto(textoValidar : String) : String?{
            if (textoValidar.length < 1) {
                return "Debe ingresar valor a este campo."
            }
            return null
        }

        fun validarCampoNumero(textoValidar : String) : String?{
            if (textoValidar.length < 1) {
                return "Debe ingresar valor a este campo."
            }
            if (!textoValidar.matches(".*[0-9].*".toRegex())) {
                return "Solo se permiten números."
            }

            return null
        }

        fun validarCampoHora(textoValidar : String) : String?{
            if (textoValidar.length < 1) {
                return "Debe ingresar valor a este campo."
            }
            if (!textoValidar.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]".toRegex())) {
                return "Solo se permiten valores en formato hora."
            }

            return null
        }

        fun invalidForm() {
            var mensaje = "Faltan llenar los campos requeridos"
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

        fun confirmarGuardarConDatosVacios(): Boolean {
            var resp = false
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Atención")
            builder.setMessage("¿Seguro que desea guardar el Anexo I con datos incompletos?")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                resp = true
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                resp = false
            }
            builder.show()
            showLogVerbose("SERGIO -- confirmarGuardarConDatosVacios = presionó $resp")
            return resp

        }

        fun submitForm(contexto: Context) : Boolean{

            var resp = false

            binding.presionContainer.helperText = validarCampoNumero(binding.etPresion.text.toString())
            binding.mdContainer.helperText = validarCampoNumero(binding.etMD.text.toString())
            binding.pistContainer.helperText = validarCampoNumero(binding.etPist.text.toString())
            binding.mantContainer.helperText = validarCampoNumero(binding.etMant.text.toString())
            binding.paradaContainer.helperText = validarCampoNumero(binding.etParada.text.toString())
            binding.inicialContainer.helperText = validarCampoNumero(binding.etInicial.text.toString())
            binding.finalContainer.helperText = validarCampoNumero(binding.etFinal.text.toString())
            binding.corrContainer.helperText = validarCampoNumero(binding.etCorr.text.toString())
            binding.petContainer.helperText = validarCampoNumero(binding.etPet.text.toString())
            binding.aguaContainer.helperText = validarCampoNumero(binding.etAgua.text.toString())
            binding.inicioContainer.helperText = validarCampoHora(binding.etInicio.text.toString())
            binding.terminoContainer.helperText = validarCampoHora(binding.etTermino.text.toString())


            val validPresion = binding.presionContainer.helperText==null
            val validMD = binding.mdContainer.helperText==null
            val validPist = binding.pistContainer.helperText==null
            val validMant = binding.mantContainer.helperText==null
            val validParada = binding.paradaContainer.helperText==null
            val validInicial = binding.inicialContainer.helperText==null
            val validFinal = binding.finalContainer.helperText==null
            val validCorr = binding.corrContainer.helperText==null
            val validPet = binding.petContainer.helperText==null
            val validAgua = binding.aguaContainer.helperText==null
            val validInicio = binding.inicioContainer.helperText==null
            val validTermino = binding.terminoContainer.helperText==null


            //showLogVerbose("-- validar campos")
            if (validPresion && validMD && validPist && validMant && validParada && validInicial
                && validFinal && validCorr && validPet && validAgua && validInicio && validTermino) {
                showLogVerbose("SERGIO -- Formulario OK")
                //guardarForm()
                resp = true
            }
            else {
                showLogVerbose("-- Formulario ERROR")
                resp = false
            }

            return resp
        }

        fun getStringValorAleatorioPozos(): Int {
            showLogVerbose("-- Funcion getStringValorAleatorioPozos")
            var i = 0
            var valor = 0
            val list: MutableList<Int> = mutableListOf(0, 1)
            list.clear()
            while (i < binding.spPozo.adapter.count) {
                valor = binding.spPozo.adapter.getItemId(i).toInt()
                list.add(valor)
                //eshowLogVerbose("-- ID Pozo Valor = $valor")
                i++
            }
            showLogVerbose("-- List ID Pozos = $list")
            var res = list.random()
            showLogVerbose("-- List ID Pozos = $res")
            return res
        }

        fun getStringValorAleatorioNumerico(): String {
            var rnds = (0..150).random()
            return rnds.toString()
        }

        fun getStringMinutosRandom() : String {
            var horasX = (0..23).random()
            var minutosX = (0..59).random()
            var horas : String = horasX.toString().padStart(2, '0')
            var minutos : String = minutosX.toString().padStart(2, '0')
            return "$horas:$minutos"
        }

        fun llenarCamposEjempo() {

            binding.spPozo.setSelection(getStringValorAleatorioPozos())
            showLogVerbose("llenarCamposEjemplo -- ID POZO ELEGIDO = "+binding.spPozo.selectedItemId)
            binding.etPresion.setText(getStringValorAleatorioNumerico())
            binding.etMD.setText(getStringValorAleatorioNumerico())
            binding.etPist.setText(getStringValorAleatorioNumerico())
            binding.etMant.setText(getStringValorAleatorioNumerico())
            binding.etParada.setText(getStringValorAleatorioNumerico())
            binding.etInicial.setText(getStringValorAleatorioNumerico())
            binding.etFinal.setText(getStringValorAleatorioNumerico())
            binding.etCorr.setText(getStringValorAleatorioNumerico())
            binding.etPet.setText(getStringValorAleatorioNumerico())
            binding.etAgua.setText(getStringValorAleatorioNumerico())
            binding.etInicio.setText(getStringMinutosRandom())
            binding.etTermino.setText(getStringMinutosRandom())

            binding.presionContainer.helperText=null
            binding.mdContainer.helperText=null
            binding.pistContainer.helperText=null
            binding.mantContainer.helperText=null
            binding.paradaContainer.helperText=null
            binding.inicialContainer.helperText=null
            binding.finalContainer.helperText=null
            binding.corrContainer.helperText=null
            binding.petContainer.helperText=null
            binding.aguaContainer.helperText=null
            binding.inicioContainer.helperText=null
            binding.terminoContainer.helperText=null
        }


        //Creando Adaptador para GenreSpinner
        /*genreSpinnerAdapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_spinner_item,
            it.getPozosDao(),
            arrayOf(DataBaseScript.GenreColumns.NAME_GENRE),
            intArrayOf(android.R.id.text1),
            SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )

         */

        binding.apply {

            imgLlenar.setOnClickListener {
                llenarCamposEjempo()
                //Toast.makeText(this@IncidenceDetailActivity, "Campos de ejemplo llenados", Toast.LENGTH_SHORT).show()
            }

            btGuardar.setOnClickListener {
                imgSave.callOnClick()
                /*val resp = submitForm()
                if (!resp) {
                    invalidForm()
                }*/
            }

            etPresion.setOnFocusChangeListener {_, focused ->
                if (!focused)
                {
                    binding.presionContainer.helperText = validarCampoNumero(etPresion.text.toString())
                }
            }
            etMD.setOnFocusChangeListener {_, focused ->
                if (!focused)
                {
                    binding.mdContainer.helperText = validarCampoNumero(etMD.text.toString())
                }
            }
            etPist.setOnFocusChangeListener {_, focused ->
                if (!focused)
                {
                    binding.pistContainer.helperText = validarCampoNumero(etPist.text.toString())
                }
            }
            etMant.setOnFocusChangeListener {_, focused ->
                if (!focused)
                {
                    binding.mantContainer.helperText = validarCampoNumero(etMant.text.toString())
                }
            }
            etParada.setOnFocusChangeListener {_, focused ->
                if (!focused)
                {
                    binding.paradaContainer.helperText = validarCampoNumero(etParada.text.toString())
                }
            }
            etInicial.setOnFocusChangeListener {_, focused ->
                if (!focused)
                {
                    binding.inicialContainer.helperText = validarCampoNumero(etInicial.text.toString())
                }
            }
            etFinal.setOnFocusChangeListener {_, focused ->
                if (!focused)
                {
                    binding.finalContainer.helperText = validarCampoNumero(etFinal.text.toString())
                }
            }
            etCorr.setOnFocusChangeListener {_, focused ->
                if (!focused)
                {
                    binding.corrContainer.helperText = validarCampoNumero(etCorr.text.toString())
                }
            }
            etPet.setOnFocusChangeListener {_, focused ->
                if (!focused)
                {
                    binding.petContainer.helperText = validarCampoNumero(etPet.text.toString())
                }
            }
            etAgua.setOnFocusChangeListener {_, focused ->
                if (!focused)
                {
                    binding.aguaContainer.helperText = validarCampoNumero(etAgua.text.toString())
                }
            }


            imgDelete.setOnClickListener {
                alertDialog.message = "¿Está seguro que desea eliminar este reporte?"
                alertDialog.isDelete = true
                alertDialog.onAcceptClickListener = {
                    progressDialog.message = "Eliminando Reporte..."
                    progressDialog.show()
                    viewModel.deleteIncidence(idRegistro)
                }
                alertDialog.show()
            }

            etInicio.setOnClickListener {
                etSelected = etInicio
                TimePickerDialog(this@IncidenceDetailActivity,
                    R.style.DialogTheme,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            }

            etTermino.setOnClickListener {
                etSelected = etTermino
                TimePickerDialog(this@IncidenceDetailActivity,
                    R.style.DialogTheme,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

            }


            imgSave.setOnClickListener {
                var datosCompletos = false

                val resp = submitForm(this@IncidenceDetailActivity)
                /*if (!resp) {
                    invalidForm()
                }
                 */
                if (resp) {
                    datosCompletos = true
                    guardarDatos(datosCompletos)
                } else {
                    datosCompletos = false
                    val mensaje =
                        "Faltan llenar los campos requeridos. ¿Desea guardar datos incompletos?"
                    val dialogo = AlertDialog.Builder(contexto)

                    dialogo.setTitle("Advertencia")
                        .setMessage(mensaje)
                        .setPositiveButton("Si") { _, _ ->
                            showLogVerbose("Presionaste SI")
                            guardarDatos(datosCompletos)
                        }
                        .setNegativeButton("NO") { _, _ ->
                            showLogVerbose("Presionaste NO")
                        }
                    dialogo.create()
                    dialogo.show()

                    //if (resp2) {
                    //    guardarDatos()
                    /*
                        var idPozoId = binding.spPozo.selectedItemId
                        val pozoTxt = binding.spPozo.selectedItem.toString()
                        showLogVerbose("imgSave #1 = selectedItemId =idPozo=$idPozoId / pozoTxt=$pozoTxt")

                        alertDialog.message = "¿Está seguro que desea guardar los cambios?"
                        alertDialog.isDelete = false
                        alertDialog.onAcceptClickListener = {
                            hideKeyboard()
                            progressDialog.message = "Guardando datos..."
                            progressDialog.show()
                            incidence.apply {
                                //idPozoId = binding.spPozo.selectedItemId
                                val pozoTxt2 = binding.spPozo.selectedItem.toString()

                                /* PONER EL ID DE POZO CORRECTO */
                                //Obteniendo el id del Spinner que recibió el evento
                                val label: String = pozoTxt2
                                var idLabelBD: Int = 0

                                pozosListArray.forEach { filaPozos ->
                                    if (filaPozos.labelBD == label)
                                        idLabelBD = filaPozos.idBD
                                }

                                showLogVerbose("imgSave = idLabelBD = $idLabelBD / pozoTxt=$pozoTxt2")

                                //idpozo = idPozoId.toString()
                                idpozo = idLabelBD.toString()
                                pozo = etPozo.text.toString()
                                bat = etBat.text.toString()
                                tipsReviso = if (rbSi.isChecked) 1 else 2
                                horasPresion = etPresion.text.toString()
                                horasInicio = etInicio.text.toString()
                                horasMd = etMD.text.toString()
                                horasPist = etPist.text.toString()
                                horasMant = etMant.text.toString()
                                horasParada = etParada.text.toString()
                                horasTermino = etTermino.text.toString()
                                diamCstb = etCsTb.text.toString()
                                diamNa = etNA.text.toString()
                                nivelesInicial = etInicial.text.toString()
                                nivelesFinal = etFinal.text.toString()
                                corr = etCorr.text.toString()
                                produccionPet = etPet.text.toString()
                                produccionAgua = etAgua.text.toString()
                                estadoApp = 1
                                estadoIncidencia = 0

                            }
                            showLogVerbose("SAVE: variable estadoEditable = $estadoEditable")
                            if (estadoEditable != 1) {
                                viewModel.saveIncidence(incidence, false)
                            } else {
                                viewModel.saveIncidence(incidence, true)
                                /*Handler(Looper.myLooper()!!).postDelayed({
                            showMessageShort("Datos guardados correctamente")
                            progressDialog.hide()
                            finish()

                        }, 500)
                         */
                            }
                        }
                        alertDialog.show()
                     */
                    //}
                }
            }
        }

        checkStatus()
    }

    private fun guardarDatos(datosCompletos : Boolean) {
        var datosCompletosInt = 0
        val idPozoId = binding.spPozo.selectedItemId
        val pozoTxt = binding.spPozo.selectedItem.toString()
        showLogVerbose("imgSave #1 = selectedItemId =idPozo=$idPozoId / pozoTxt=$pozoTxt")

        alertDialog.message = "¿Está seguro que desea guardar los cambios?"
        alertDialog.isDelete = false
        alertDialog.onAcceptClickListener = {

            incidence.apply {
                //idPozoId = binding.spPozo.selectedItemId
                val pozoTxt2 = binding.spPozo.selectedItem.toString()

                /* PONER EL ID DE POZO CORRECTO */
                //Obteniendo el id del Spinner que recibió el evento
                val label: String = pozoTxt2
                var idLabelBD: Int = 0

                pozosListArray.forEach { filaPozos ->
                    if (filaPozos.labelBD == label)
                        idLabelBD = filaPozos.idBD
                }

                var valorTieneAnexoK = 0
                var valorEstadoIncidencia = 0

                if (datosCompletos) {
                    datosCompletosInt = 0
                    valorTieneAnexoK = 0
                    valorEstadoIncidencia = 1
                }
                else {
                    datosCompletosInt = 0
                    valorTieneAnexoK = 0
                    valorEstadoIncidencia = 0

                }

                val nuevoEstado = 2   // Pone la Orden en estado En Curso

                showLogVerbose("imgSave = idLabelBD = $idLabelBD / pozoTxt=$pozoTxt2")
                with(binding) {
                    //idpozo = idPozoId.toString()
                    idpozo = idLabelBD.toString()
                    pozo = etPozo.text.toString()
                    bat = etBat.text.toString()
                    tipsReviso = if (rbSi.isChecked) 1 else 2
                    horasPresion = etPresion.text.toString()
                    horasInicio = etInicio.text.toString()
                    horasMd = etMD.text.toString()
                    horasPist = etPist.text.toString()
                    horasMant = etMant.text.toString()
                    horasParada = etParada.text.toString()
                    horasTermino = etTermino.text.toString()
                    diamCstb = etCsTb.text.toString()
                    diamNa = etNA.text.toString()
                    nivelesInicial = etInicial.text.toString()
                    nivelesFinal = etFinal.text.toString()
                    corr = etCorr.text.toString()
                    produccionPet = etPet.text.toString()
                    produccionAgua = etAgua.text.toString()
                    estadoApp = 1
                    estadoIncidencia = valorEstadoIncidencia   // datosCompletos
                    tieneAnexoK = valorTieneAnexoK
                }

            }
            showLogVerbose("SAVE INCIDENCIA datosCompletosInt = $datosCompletosInt")
            showLogVerbose("SAVE INCIDENCIA estadoEditable = $estadoEditable")


            if (estadoEditable != 1) {
                viewModel.saveIncidence(incidence, false, datosCompletos)
            } else {
                viewModel.saveIncidence(incidence, true, datosCompletos)
            }
        }
        alertDialog.show()


    }

    private fun validarPozoIngresadoPreviamente(idpozo: String) {

    }

    private fun validadHelpersInicio() {
        with(binding) {
            etPresion.text?.isNotEmpty().apply {
                presionContainer.helperText = null
            }
            etMD.text?.isNotEmpty().apply {
                mdContainer.helperText = null
            }
            etPist.text?.isNotEmpty().apply {
                pistContainer.helperText = null
            }
            etMant.text?.isNotEmpty().apply {
                mantContainer.helperText = null
            }
            etParada.text?.isNotEmpty().apply {
                paradaContainer.helperText = null
            }
            etInicial.text?.isNotEmpty().apply {
                inicialContainer.helperText = null
            }
            etFinal.text?.isNotEmpty().apply {
                finalContainer.helperText = null
            }
            etCorr.text?.isNotEmpty().apply {
                corrContainer.helperText = null
            }
            etPet.text?.isNotEmpty().apply {
                petContainer.helperText = null
            }
            etAgua.text?.isNotEmpty().apply {
                aguaContainer.helperText = null
            }
            etInicio.text?.isNotEmpty().apply {
                inicioContainer.helperText = null
            }
            etTermino.text?.isNotEmpty().apply {
                terminoContainer.helperText = null
            }

        }
    }

    private fun checkStatus(){
        binding.apply {
            if(isComplete){
                imgDelete.visibility = View.GONE
                imgSave.visibility = View.GONE
                spPozo.disable()
                spPozo.isEnabled = false

                etPozo.disable()
                etBat.disable()
                rbSi.disable()
                rbNo.disable()
                rbSi.isEnabled = false
                rbNo.isEnabled = false
                etPresion.disable()
                etInicio.disable()
                etMD.disable()
                etPist.disable()
                etMant.disable()
                etParada.disable()
                etTermino.disable()
                etCsTb.disable()
                etNA.disable()
                etInicial.disable()
                etFinal.disable()
                etCorr.disable()
                etPet.disable()
                etAgua.disable()
                btGuardar.visibility = View.GONE
                btGuardar.disable()
            }
            else {
                imgDelete.visibility = View.VISIBLE
                imgSave.visibility = View.VISIBLE
                /* Sergio Temporal*/
                //imgLlenar.visibility = View.VISIBLE
                imgLlenar.visibility = View.GONE

                btGuardar.visibility = View.VISIBLE
            }
        }

    }

    fun pintarCamposPozos(valorPozo: String, valorBateria: String, valorDiametro: String, valorNA: String) {
        with(binding){
            etPozo.setText(valorPozo)
            etBat.setText(valorBateria)
            etCsTb.setText(valorDiametro)
            etNA.setText(valorNA)
        }
    }

    override fun viewModelListeners() {
        viewModel.apply {
            deleteSuccess.observe(this@IncidenceDetailActivity){
                showMessage(it)
                progressDialog.hide()
                finish()
            }

            saveSuccess.observe(this@IncidenceDetailActivity){
                showMessage(it)
                progressDialog.hide()
                finish()
            }

            saveError.observe(this@IncidenceDetailActivity){
                showMessage(it)
                progressDialog.hide()
            }


            getIncidenceSuccess.observe(this@IncidenceDetailActivity){
                incidence = it
                binding.apply {
                    //var valorBateria = bateriaList.get(spinnerPosition)
                    //var valorDiametro = diametroList.get(spinnerPosition)
                    //var valorNA = naList.get(spinnerPosition)

                    showLogVerbose("getIncidenceSuccess / Incidence it= $it")
                    showLogVerbose("getIncidenceSuccess / Incidence idRegistro= " + it.idregistro.toString())

                    var estadoApp: Int = it.estadoApp
                    showLogVerbose("estadoApp = $estadoApp")

                    if (estadoApp==1) { // entonces existe
                        estadoEditable = 1
                        idPozoElegido = it.idpozo.toInt()
                    }
                    else if (estadoApp==0) { // entonces es nuevo
                        estadoEditable = 0
                        idPozoElegido = 0
                    }

                    showLogVerbose("SERGIO -- getIncidenceSuccess -- estadoEditable = $estadoEditable estadoApp $estadoApp")
                    showLogVerbose("SERGIO -- getIncidenceSuccess -- POZO TEXT ELEGIDO = "+it.pozo)
                    showLogVerbose("SERGIO -- getIncidenceSuccess -- ID POZO ELEGIDO = "+it.idpozo)
                    showLogVerbose("SERGIO -- getIncidenceSuccess -- idPozoElegido=$idPozoElegido")

                    spPozo.setSelection(idPozoElegido)
                    etPozo.setText(it.pozo)
                    etBat.setText(it.bat)
                    rbSi.isChecked = it.tipsReviso == 1
                    rbNo.isChecked = it.tipsReviso != 1
                    etPresion.setText(it.horasPresion)
                    etInicio.setText(it.horasInicio)
                    etMD.setText(it.horasMd)
                    etPist.setText(it.horasPist)
                    etMant.setText(it.horasMant)
                    etParada.setText(it.horasParada)
                    etTermino.setText(it.horasTermino)
                    etCsTb.setText(it.diamCstb)
                    etNA.setText(it.diamNa)
                    etInicial.setText(it.nivelesInicial)
                    etFinal.setText(it.nivelesFinal)
                    etCorr.setText(it.corr)
                    etPet.setText(it.produccionPet)
                    etAgua.setText(it.produccionAgua)
                }
            }

            /* SERGIO POZOS */
            getPozosSucess.observe(this@IncidenceDetailActivity){

                //idPozoElegido = binding.spPozo.selectedItemId.toInt()
                showLogVerbose("Sergio -- getPozosSucess -- idPozoElegido = $idPozoElegido")

                val pozosList =  mutableListOf<String>()
                pozosList.clear()
                it.forEach { fila ->
                    pozosList.add(fila.pozo)
                }
                showLogVerbose("Sergio -- getPozosSucess -- POZOSLIST = "+pozosList.toString())

                it.forEach { fila ->
                    val tmp = PozosListaLocal(fila.idpozo, fila.pozo)
                    pozosListArray.add(tmp)
                }

                showLogVerbose("Sergio -- getPozosSucess -- pozosListArray = "+pozosListArray.toString())

                bateriaList =  mutableListOf<String>()
                it.forEach { fila ->
                    bateriaList.add(fila.bateria)
                }

                val diametroList =  mutableListOf<String>()
                it.forEach { fila ->
                    diametroList.add(fila.diametro_fraccion)
                }

                val naList =  mutableListOf<String>()
                it.forEach { fila ->
                    naList.add(fila.na_td)
                }

                showLogVerbose("Sergio -- getPozosSucess -- Array pozosEntityList = $pozosList")

                // LLENADO DE POZOS
                val spinnerPozo: Spinner = binding.spPozo

                val adapter = ArrayAdapter(this@IncidenceDetailActivity,
                    android.R.layout.simple_spinner_item, pozosList)

                //showLogVerbose("Sergio -- getPozosSucess idPozoElegido = $idPozoElegido")

                spinnerPozo.adapter = adapter

                var posPozoElegidoValidado = 0
                var posFila = 0
                pozosListArray.forEach { filaPozos ->
                    if ( idPozoElegido == filaPozos.idBD)
                        posPozoElegidoValidado = posFila
                    posFila += 1
                }
                //showLogVerbose("Sergio -- getPozosSucess idPozoElegidoValidado = $idPozoElegido")


                //spinnerPozo.setSelection(idPozoElegido)
                /* SERGIO -- SE PONE LA POSICION DEL ARRAY, NO EL ID*/
                spinnerPozo.setSelection(posPozoElegidoValidado)

                pintarCamposPozos("","","","")

                spinnerPozo.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        showLogVerbose("onNothingSelected")
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                        //Obteniendo el id del Spinner que recibió el evento
                        val label: String = parent?.getItemAtPosition(position).toString()
                        var idLabelBD : Int = 0
                        var labelElegido = ""

                        var valorPozo : String
                        val valorBateria : String
                        val valorDiametro : String
                        val valorNA : String
                        val spinnerPosition: Int

                        //showLogVerbose("Sergio -- estadoEditable  -- $estadoEditable")
                        //showLogVerbose("Sergio -- IdPozoElegido  -- $idPozoElegido")
                        //showLogVerbose("Sergio -- Spinner Label Seleccionado onclick -- $label")

                        valorPozo = spinnerPozo.selectedItem.toString()

                        /*
                        showLogVerbose("Sergio -- Label Despues -- $labelElegido")
                        showLogVerbose("Sergio -- onItemSelected -- idLabelBD=$idLabelBD")
                        showLogVerbose("Sergio -- onItemSelected -- label=$labelElegido")
                        showLogVerbose("onItemSelected -- valorPozo=$valorPozo")
                        showLogVerbose("onItemSelected -- selectedItemId="+spinnerPozo.selectedItemId)
                        showLogVerbose("onItemSelected -- selectedItem="+spinnerPozo.selectedItem)
                        */

                        // Poner en variables locales lo seleccionado en Spinner
                        spinnerPosition = adapter.getPosition(valorPozo)
                        showLogVerbose("Spinner Position =$spinnerPosition")

                        spinnerPozo.setSelection(spinnerPosition)
                        valorBateria = bateriaList.get(spinnerPosition)
                        valorDiametro = diametroList.get(spinnerPosition)
                        valorNA = naList.get(spinnerPosition)
                        showLogVerbose("Sergio onItemSelected spinnerPosition = $spinnerPosition bateria = $valorBateria valorDiametro=$valorDiametro")

                        //showLogVerbose("onItemSelectedListener / estadoEditable = $estadoEditable")
                        //showLogVerbose("onItemSelectedListener / Item elegido Pos # "+position.toString() + " valor="+valorPozo)

                        pintarCamposPozos(valorPozo, valorBateria, valorDiametro, valorNA)

                    }
                }

            }
        }
    }

    private fun showLogVerbose(s: String) {
        Log.e("Sergio", s)

    }

}


data class PozosListaLocal (var idBD: Int, var labelBD: String)


