package com.orlovdanylo.fromonetoninegame.presentation.core

fun interface ClickListener<T> {
    fun click(model: T)
}