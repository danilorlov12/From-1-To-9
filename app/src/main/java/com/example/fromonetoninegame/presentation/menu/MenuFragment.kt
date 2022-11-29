package com.example.fromonetoninegame.presentation.menu

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import com.example.fromonetoninegame.R
import com.example.fromonetoninegame.base.BaseFragment

class MenuFragment : BaseFragment<MenuViewModel>() {

    override val layoutId: Int = R.layout.fragment_menu

    override fun viewModelClass() = MenuViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<AppCompatButton>(R.id.btnContinue).apply {
            setOnClickListener {
                val action = MenuFragmentDirections.actionMenuFragmentToGameFragment(true)
                navController.navigate(action)
            }
            visibility = if (viewModel.hasStoredGame()) View.VISIBLE else View.GONE
        }

        view.findViewById<AppCompatButton>(R.id.btnNewGame).setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToGameFragment(false)
            navController.navigate(action)
        }

        view.findViewById<AppCompatImageButton>(R.id.btnInfo).setOnClickListener {

        }
    }
}