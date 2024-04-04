package com.orlovdanylo.fromonetoninegame.presentation.info_game

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.ButtonActions
import com.orlovdanylo.fromonetoninegame.utils.logEventClickListener
import com.orlovdanylo.fromonetoninegame.presentation.core.BaseFragment
import com.orlovdanylo.fromonetoninegame.presentation.info_game.adapter.InfoGameAdapter

class InfoGameFragment : BaseFragment<InfoGameViewModel>() {

    override val layoutId: Int = R.layout.fragment_info_game
    override val viewModel: InfoGameViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val ivNextPage: AppCompatButton = view.findViewById(R.id.btnNext)
        val ivPreviousPage: AppCompatButton = view.findViewById(R.id.btnBack)

        val infoAdapter = InfoGameAdapter()

        val rvDescription = view.findViewById<RecyclerView>(R.id.rvDescription).apply {
            layoutManager = GridLayoutManager(context, 5)
            adapter = infoAdapter
            itemAnimator = null
        }

        viewModel.initFirstPage()

        viewModel.currentPage.observe(viewLifecycleOwner) { pageInfo ->
            if (pageInfo == null) {
                navController.navigateUp()
                return@observe
            }
            tvDescription.text = getString(pageInfo.descriptionResId)
            if (pageInfo.listOfModels.isNotEmpty()) {
                infoAdapter.submitList(pageInfo.listOfModels)
                tvDescription.minLines = 4
                rvDescription.visibility = View.VISIBLE
            } else {
                tvDescription.minLines = 0
                rvDescription.visibility = View.GONE
            }
        }

        ivNextPage.logEventClickListener(requireActivity(), ButtonActions.NEXT_PAGE) {
            viewModel.nextPage()
        }

        ivPreviousPage.logEventClickListener(requireActivity(), ButtonActions.PREVIOUS_PAGE) {
            viewModel.previousPage()
        }
    }

    override fun clear() = Unit
}
