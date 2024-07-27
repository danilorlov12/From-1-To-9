package com.orlovdanylo.fromonetoninegame.presentation.how_to_play.rule_content

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.presentation.core.BaseFragment
import com.orlovdanylo.fromonetoninegame.presentation.how_to_play.HowToPlayViewModel
import com.orlovdanylo.fromonetoninegame.presentation.how_to_play.adapter.RuleContentAdapter

class RuleContentFragment : BaseFragment<HowToPlayViewModel>() {

    override val layoutId: Int = R.layout.fragment_rule_content
    override val viewModel: HowToPlayViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvDescription: TextView = view.findViewById(R.id.tvDescription)

        val infoAdapter = RuleContentAdapter()

        val rvDescription = view.findViewById<RecyclerView>(R.id.rvDescription).apply {
            layoutManager = GridLayoutManager(context, 5)
            adapter = infoAdapter
            itemAnimator = null
        }

        arguments?.getInt(ARG_SECTION_NUMBER)?.let {
            val pageInfo = viewModel.obtainPageInfoByPosition(it)
            if (pageInfo == null) {
                navController.navigateUp()
                return@let
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
    }

    override fun clear() = Unit

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): RuleContentFragment {
            return RuleContentFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}