package com.orlovdanylo.fromonetoninegame.presentation.alert_dialog

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.orlovdanylo.fromonetoninegame.R

class CustomAlertDialog(
    context: Context,
    private val title: String? = null,
    private val message: String? = null,
    private val positiveButtonText: String? = null,
    private val negativeButtonText: String? = null,
    private val onPositiveButtonClick: (() -> Unit)? = null,
    private val onNegativeButtonClick: (() -> Unit)? = null
) : AlertDialog(context, R.style.AlertDialogStyle) {

    override fun create() {
        super.create()
        setContentView(R.layout.custom_alert_dialog)
        setCancelable(false)

        findViewById<TextView>(R.id.tvTitle).apply {
            title?.let { text = it } ?: run {
                visibility = View.GONE
            }
        }

        findViewById<AppCompatButton>(R.id.btnOk).apply {
            positiveButtonText?.let { text = it } ?: run {
                visibility = View.GONE
            }
            onPositiveButtonClick?.let { action ->
                setOnClickListener {
                    action.invoke()
                    dismiss()
                }
            }
        }
        show()
    }
}