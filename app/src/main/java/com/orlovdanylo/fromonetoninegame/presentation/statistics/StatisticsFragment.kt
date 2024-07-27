package com.orlovdanylo.fromonetoninegame.presentation.statistics

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.presentation.core.BaseFragment

class StatisticsFragment : BaseFragment<StatisticsViewModel>() {

    override val layoutId: Int = R.layout.fragment_statistics
    override val viewModel: StatisticsViewModel by activityViewModels()

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
            view.findViewById<TextView>(R.id.tvWinRate).apply {
                text = statistics.winRate
            }
            view.findViewById<TextView>(R.id.tvMinPairs).apply {
                text = statistics.minPairs.toString()
            }
            view.findViewById<TextView>(R.id.tvMaxPairs).apply {
                text = statistics.maxPairs.toString()
            }
            view.findViewById<TextView>(R.id.tvBestTime).apply {
                text = statistics.bestTime
            }
        }
    }

    override fun clear() = Unit
}