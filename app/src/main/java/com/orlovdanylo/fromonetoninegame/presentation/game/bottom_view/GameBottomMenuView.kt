package com.orlovdanylo.fromonetoninegame.presentation.game.bottom_view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.ButtonActions
import com.orlovdanylo.fromonetoninegame.utils.logEventClickListener

class GameBottomMenuView : ConstraintLayout {

    var actions: GameBottomMenuActions? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.view_game_bottom_menu, this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val btnUndo = findViewById<AppCompatImageButton>(R.id.btnUndo)
        val btnRedo = findViewById<AppCompatImageButton>(R.id.btnRedo)
        val btnAddDigits = findViewById<AppCompatImageButton>(R.id.btnAddDigits)
        val btnTip = findViewById<AppCompatImageButton>(R.id.btnTip)

        btnAddDigits.isEnabled = false

        btnUndo.logEventClickListener(context, ButtonActions.UNDO) {
            actions?.undo()
        }

        btnRedo.logEventClickListener(context, ButtonActions.REDO) {
            actions?.redo()
        }

        btnAddDigits.logEventClickListener(context, ButtonActions.UPDATE_NUMBERS) {
            actions?.update()
        }

        btnTip.logEventClickListener(context, ButtonActions.TIP) {
            actions?.showTip()
        }
    }
}