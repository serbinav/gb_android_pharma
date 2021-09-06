package com.example.comparepharma.utils

import android.app.AlertDialog
import android.content.Context

class DialogAlert(
    private val context: Context,
    private val title: String,
    private val message: String
) {
    private val dialog = AlertDialog.Builder(context)

    constructor(
        context: Context,
        rStringTitleId: Int,
        rStringMessageId: Int
    ) : this(
        context,
        context.getString(rStringTitleId),
        context.getString(rStringMessageId)
    )

    private fun build(): AlertDialog.Builder {
        return dialog.setTitle(title).setMessage(message)
    }

    private fun show() {
        dialog.create().show()
    }

    fun showBaseDialogAlert(rStringNegativeButtonId: Int) {
        build().setNegativeButton(context.getString(rStringNegativeButtonId)) { dialog, _ ->
            dialog.dismiss()
        }
        show()
    }

    fun showDialogAlertWithPositiveButton(
        rStringPositiveButtonId: Int,
        rStringNegativeButtonId: Int,
        doSomething: () -> Unit)
    {
        build()
            .setPositiveButton(context.getString(rStringPositiveButtonId)) { _, _ ->
                doSomething()
            }
            .setNegativeButton(context.getString(rStringNegativeButtonId)) { dialog, _ ->
                dialog.dismiss()
            }
        show()
    }
}