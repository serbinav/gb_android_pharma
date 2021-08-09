package com.example.comparepharma.view

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.util.*

fun View.showSnackBar(text: String, actionText: String, action: (View) -> Unit) {
    Snackbar.make(this, text, Snackbar.LENGTH_INDEFINITE)
        .setAction(actionText, action).show()
}

fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

fun Any.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this.toString(), duration).apply { show() }
}

fun String.addCurrencySymbol(): String {
    val template = "%1s %2s"
    return when (Locale.getDefault()) {
        Locale.UK -> template.format(this, "£")
        Locale.US -> template.format(this, "$")
        else -> {
            String.format(template, " ₽")
        }
    }
}