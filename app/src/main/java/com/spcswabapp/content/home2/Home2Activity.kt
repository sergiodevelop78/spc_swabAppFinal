package com.spcswabapp.content.home2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.spcswabapp.R
import com.spcswabapp.base.ActivityViewBinding
import com.spcswabapp.content.form_anexoo.NewFormAnexoOActivity
import com.spcswabapp.content.home2.adapter.Order2Adapter
import com.spcswabapp.content.form_anexo_incidencias.FormAnexooIncidenceActivity
import com.spcswabapp.databinding.ActivityHome2Binding
import com.spcswabapp.services.SharedPreferencesManager
import com.spcswabapp.utils.openActivity
import com.spcswabapp.utils.showMessage
import org.koin.android.ext.android.inject

class Home2Activity : ActivityViewBinding<ActivityHome2Binding, Home2VM>() {

    private val adapter: Order2Adapter by inject()
    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityHome2Binding.inflate(layoutInflater)

    override fun viewListeners() {
        binding.rvOrder.adapter = adapter
        listeners()
        viewModel.getOrder()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getOrder()
        }

        adapter.onEditClickListener = { it, title ->
            openActivity(NewFormAnexoOActivity::class.java, extras = {
                putInt("idFormAnexoo", it.idFormAnexoo)
                putBoolean("isExist", true)
                putString("title", title)
            })
        }

        adapter.onIncidenceClickListener = {
            openActivity(FormAnexooIncidenceActivity::class.java, extras = {
                putInt("idFormAnexoo", it.idFormAnexoo)
            })
        }

        adapter.onRegistClickListener = {
            alertDialog.message = "¿Esta seguro que desea finalizar la orden?"
            alertDialog.onAcceptClickListener = {
                viewModel.completeReport(it)
            }
            alertDialog.show()
        }


    }

    private fun listeners() {

        binding.toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener {
            when (it.itemId) {
                R.id.newAnexoo -> {
                    openActivity(NewFormAnexoOActivity::class.java)
                }
                R.id.sync -> {
                    alertDialog.message = "¿Esta seguro que desea sincronizar los datos?"
                    alertDialog.onAcceptClickListener = {
                        progressDialog.message = "Sincronizando con el servidor"
                        progressDialog.show()
                        viewModel.syncrhonize()
                    }
                    alertDialog.show()

                }
                R.id.salirApp -> {
                    alertSalirDialog.message = "¿Está seguro que desea salir de la APP?"
                    alertSalirDialog.onAcceptClickListener = {
                        /* SERGIO - SALIR DE APP Y LIMPIAR SESION */
                        val st = this.getSharedPreferences(SharedPreferencesManager.KEYAPP, Context.MODE_PRIVATE )
                        //Log.d("SERGIO", "Shared Preferences = "+st.getString(USERKEY, "XX") )
                        st.edit().clear().apply()
                        this.finish()
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_HOME)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)

                    }
                    alertSalirDialog.show()

                }
            }
            return@OnMenuItemClickListener true
        })

    }

    override fun viewModelListeners() {
        viewModel.apply {
            getFormAnexooSuccess.observe(this@Home2Activity) {
                adapter.list = it
                binding.swipeRefreshLayout.isRefreshing = false
                if (it.count() > 0) binding.txtMessage.visibility =
                    View.GONE else binding.txtMessage.visibility = View.VISIBLE
            }

            onDeleteFormAnexooSuccess.observe(this@Home2Activity) {
                progressDialog.hide()
                showMessage(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getOrder()
    }



}