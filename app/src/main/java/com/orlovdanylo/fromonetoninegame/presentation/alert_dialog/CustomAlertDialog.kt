package com.orlovdanylo.fromonetoninegame.presentation.alert_dialog

import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.orlovdanylo.fromonetoninegame.R

class CustomAlertDialog(
    context: Context,
    private val title: String? = null,
    private val buttons: List<ButtonSetting> = emptyList()
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

        findViewById<LinearLayout>(R.id.container).let { container ->
            buttons.forEach {
                container.addView(adjustButton(it))
            }
        }
        show()
    }

    private fun adjustButton(setting: ButtonSetting): AppCompatButton {
        val marginTop = context.resources.getDimensionPixelSize(R.dimen.margin_32)
        return AppCompatButton(ContextThemeWrapper(context, R.style.ButtonMenu),null, R.style.ButtonMenu).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).also {
                it.setMargins(0, marginTop, 0, 0)
            }
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gold))
            setting.adjustButton(this) { dismiss() }
        }
    }
}