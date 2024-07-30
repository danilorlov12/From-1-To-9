package com.orlovdanylo.fromonetoninegame.presentation.alert_dialog

import androidx.appcompat.widget.AppCompatButton

class ButtonSetting(
    private val messageResId: Int,
    private val action: () -> Unit
) {
    fun adjustButton(button: AppCompatButton, dismiss: () -> Unit) = with(button) {
        setText(messageResId)
        setOnClickListener {
            action.invoke()
            dismiss.invoke()
        }
    }
}