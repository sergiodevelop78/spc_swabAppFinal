package com.spcswabapp.sergiofunciones

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog

class SergioFunciones() {

    fun showLogVerbose(s: String) {
        Log.e("Sergio", s)

    }

    fun showLogJson(yourString: String) {
        val maxLogSize = 1000
        val stringLength = yourString.length
        for (i in 0..stringLength / maxLogSize) {
            val start = i * maxLogSize
            var end = (i + 1) * maxLogSize
            end = if (end > yourString.length) yourString.length else end
            Log.v("Sergio JSON", yourString.substring(start, end))
        }
    }

    fun logUnlimited(tag: String, string: String) {
        val maxLogSize = 1000
        string.chunked(maxLogSize).forEach { Log.v(tag, it) }
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

    fun getTextosAleatorios(tamano : Int): String = List(tamano) {
        (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
    }.joinToString("")


    fun showAlertDialogo_malo(context: Context, titulo: String, mensaje: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            // no hacer nada
        }
        builder.show()
    }


}