package com.spcswabapp.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Context.showMessage(message:String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}

fun Context.showMessageShort(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun Context.showMessageDialogError(message:String){
    //Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Atención")
    builder.setMessage(message)

    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
        // no hacer nada
    }
    builder.show()
}

fun <T> AppCompatActivity.openActivity(activityClass: Class<T>,delay : Long = 0,finishExisting : Boolean = false, extras: Bundle.() -> Unit = {}) {
    Handler(Looper.getMainLooper()).postDelayed({
        val intent = Intent(this, activityClass)
        intent.putExtras(Bundle().apply(extras))

        if(finishExisting) finish()

        startActivity(intent)

    }, delay)
}

fun getActivity(context: Context?): Activity? {
    if (context == null) {
        return null
    } else if (context is ContextWrapper) {
        return if (context is Activity) {
            context
        } else {
            getActivity(context.baseContext)
        }
    }
    return null
}


fun View.disable(){
    this.isFocusable = false
    this.isClickable = false
    this.isFocusableInTouchMode = false
    this.setOnClickListener {  }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}