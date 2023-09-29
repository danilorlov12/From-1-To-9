package com.orlovdanylo.fromonetoninegame.presentation.statistics

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.base.BaseFragment

class StatisticsFragment : BaseFragment<StatisticsViewModel>() {

    override val layoutId: Int = R.layout.fragment_statistics

    override fun viewModelClass() = StatisticsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadStatistics()

        viewModel.statistics.observe(viewLifecycleOwner) { statistics ->
            view.findViewById<TextView>(R.id.tvGamesPlayed).apply {
                text = statistics.gamesPlayed.toString()
            }
            view.findViewById<TextView>(R.id.tvGamesFinished).apply {
                text = statistics.gamesFinished.toString()
            }
            view.findViewById<TextView>(R.id.tvBestTime).apply {
                text = statistics.bestTime
            }
            view.findViewById<TextView>(R.id.tvMinPairs).apply {
                text = statistics.minPairs.toString()
            }
            view.findViewById<TextView>(R.id.tvMaxPairs).apply {
                text = statistics.maxPairs.toString()
            }
        }
    }
}