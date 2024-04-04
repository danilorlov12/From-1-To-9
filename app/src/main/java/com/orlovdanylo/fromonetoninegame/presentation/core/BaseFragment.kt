package com.orlovdanylo.fromonetoninegame.presentation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.orlovdanylo.fromonetoninegame.presentation.MainActivity

abstract class BaseFragment<VM: BaseViewModel> : Fragment() {

    protected abstract val viewModel: VM
    protected abstract val layoutId: Int
    protected lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).analytics.logScreenEvent(javaClass.simpleName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clear()
    }

    abstract fun clear()
}