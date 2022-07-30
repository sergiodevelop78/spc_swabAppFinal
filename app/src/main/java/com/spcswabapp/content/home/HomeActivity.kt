package com.spcswabapp.content.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.spcswabapp.R
import com.spcswabapp.base.ActivityViewBinding
import com.spcswabapp.content.home.adapter.OrderAdapter
import com.spcswabapp.content.incidences.IncidenceActivity
import com.spcswabapp.content.new_order.NewOrderActivity
import com.spcswabapp.databinding.ActivityHomeBinding
import com.spcswabapp.services.SharedPreferencesManager.Companion.KEYAPP
import com.spcswabapp.utils.openActivity
import com.spcswabapp.utils.showMessageDialogError
import org.koin.android.ext.android.inject

@Suppress("SpellCheckingInspection")
class HomeActivity : ActivityViewBinding<ActivityHomeBinding, HomeVM>() {

    private val adapter: OrderAdapter by inject()
    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityHomeBinding.inflate(layoutInflater)

    override fun viewListeners() {
        binding.rvOrder.adapter = adapter
        listeners()
        viewModel.getOrder()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getOrder()
        }

        adapter.onEditClickListener = { it, title ->
            openActivity(NewOrderActivity::class.java, extras = {
                putInt("idSwabReport", it.idSwabReporte)
                putBoolean("isExist", true)
                putString("title", title)
            })
        }

        adapter.onIncidenceClickListener = { it, title ->
            openActivity(IncidenceActivity::class.java, extras = {
                putInt("idSwabReport", it.idSwabReporte)
                putInt("idEstadoSwab", it.idEstadoSwab)
                putBoolean("isExist", true)
                putString("title", title)
            })
        }

        adapter.onRegistClickListener = {
            alertDialog.message = "¿Esta seguro que desea finalizar la orden?"
            alertDialog.onAcceptClickListener = {
                viewModel.completeReport(it)
            }
            alertDialog.show()
        }

        // Temporal Debug
        /*adapter.onDebugClickListener = { it, title ->
            val mensaje = "$it /// $title"
            val dialogo = AlertDialog.Builder(this)
            dialogo.setTitle("DEBUG")
                .setMessage(mensaje)
                .setPositiveButton("Ok") { _, _ ->
                    // no hacer nada
                }
            dialogo.create()
            dialogo.show()


        }
        */

    }

    private fun listeners() {

        binding.toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener {
            when (it.itemId) {
                R.id.newRegister -> {
                    openActivity(NewOrderActivity::class.java)
                }
                R.id.sync -> {
                    alertDialog.message = "¿Está seguro que desea sincronizar los datos?"
                    alertDialog.onAcceptClickListener = {
                        progressDialog.message = "Sincronizando con el servidor"
                        progressDialog.show()
                        viewModel.syncrhonize()
                    }
                    alertDialog.show()

                }
                R.id.salirApp -> {
                    alertDialog.message = "¿Está seguro que desea salir de la APP?"
                    alertDialog.onAcceptClickListener = {
                        /* SERGIO - SALIR DE APP Y LIMPIAR SESION */
                        val st = this.getSharedPreferences(KEYAPP, Context.MODE_PRIVATE )
                        //Log.d("SERGIO", "Shared Preferences = "+st.getString(USERKEY, "XX") )
                        st.edit().clear().apply()
                        this.finish()
                        finishAndRemoveTask();

                        finishAffinity();

                        /*val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_HOME)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)

                         */


                    }
                    alertDialog.show()

                }
            }
            return@OnMenuItemClickListener true
        })

    }

    override fun viewModelListeners() {
        viewModel.apply {
            getOrderSuccess.observe(this@HomeActivity) {

                adapter.list = it
                binding.swipeRefreshLayout.isRefreshing = false
                if (it.count() > 0) binding.txtMessage.visibility =
                    View.GONE else binding.txtMessage.visibility = View.VISIBLE
            }

            onDeleteSuccess.observe(this@HomeActivity) {
                progressDialog.hide()
                //showMessage(it)
                showMessageDialogError(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getOrder()
    }



}