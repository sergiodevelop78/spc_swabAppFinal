package com.spcswabapp.content.incidences.detail
import android.app.Activity
import android.view.View
import android.widget.AdapterView
import android.widget.Toast

class SpinnerController : Activity(), AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        //Obteniendo el id del Spinner que recibió el evento
        val idSpinner: Int = parent.id

        Toast.makeText(this, "Item seleccionado", Toast.LENGTH_SHORT).show()

    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}
