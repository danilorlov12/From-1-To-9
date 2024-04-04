package com.orlovdanylo.fromonetoninegame.utils

import android.content.Context
import android.view.View
import com.orlovdanylo.fromonetoninegame.ButtonActions
import com.orlovdanylo.fromonetoninegame.presentation.MainActivity

fun View.logEventClickListener(context: Context, buttonName: ButtonActions, block: () -> Unit) {
    setOnClickListener {
        if (context is MainActivity) {
            context.analytics.logButtonEvent(buttonName.name)
        }
        block.invoke()
    }
}