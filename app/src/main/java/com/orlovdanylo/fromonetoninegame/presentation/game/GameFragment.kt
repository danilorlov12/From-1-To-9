package com.orlovdanylo.fromonetoninegame.presentation.game

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.base.BaseFragment
import com.orlovdanylo.fromonetoninegame.domain.model.TimeModel
import com.orlovdanylo.fromonetoninegame.presentation.alert_dialog.CustomAlertDialog
import com.orlovdanylo.fromonetoninegame.presentation.game.adapter.ClickListener
import com.orlovdanylo.fromonetoninegame.presentation.game.adapter.GameAdapter
import com.orlovdanylo.fromonetoninegame.presentation.game.models.GameModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameFragment : BaseFragment<GameViewModel>() {

    override val layoutId: Int = R.layout.fragment_game
    override val viewModel: GameViewModel by activityViewModels()

    private val stopwatchScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private var adapter: GameAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvGameTime = view.findViewById<TextView>(R.id.tvGameTime)
        val tvRemovedNumbers = view.findViewById<TextView>(R.id.tvRemovedNumbers)
        adapter = GameAdapter(object : ClickListener {
            override fun click(model: GameModel) {
                viewModel.tap(model.id)
            }
        })

        view.findViewById<RecyclerView>(R.id.rvDigits).apply {
            layoutManager = GridLayoutManager(context, 9)
            adapter = this@GameFragment.adapter
            itemAnimator = null
        }
        viewModel.initGame(GameFragmentArgs.fromBundle(requireArguments()).isNewGame)

        viewModel.startTime.observe(viewLifecycleOwner) { time: Long? ->
            if (time != null) startStopwatch(time)
        }
        viewModel.gameTime.observe(viewLifecycleOwner) { time ->
            val timeModel = TimeModel(time)
            tvGameTime.text = timeModel.displayableTime()
        }
        viewModel.gameModels.observe(viewLifecycleOwner) { models ->
            if (models.isNullOrEmpty()) return@observe
            adapter?.submitList(models)
        }
        viewModel.updatedPair.observe(viewLifecycleOwner) { pair ->
            if (pair != null) {
                adapter?.notifyItemChanged(pair.first)
                adapter?.notifyItemChanged(pair.second)
                viewModel.selectedModel.value = null
            }
        }
        viewModel.selectedModel.observe(viewLifecycleOwner) { model ->
            val selectedItem = if (model != null) {
                val item = viewModel.gameModels.value?.firstOrNull { it.id == model.id } ?: return@observe
                item.isSelected = true
                item
            } else {
                val item = viewModel.gameModels.value?.firstOrNull { it.isSelected } ?: return@observe
                item.isSelected = false
                item
            }
            adapter?.notifyItemChanged(selectedItem.id)
        }
        viewModel.pairNumbers.observe(viewLifecycleOwner) { pair ->
            pair.toList().forEach {
                adapter?.notifyItemChanged(it)
            }
        }
        viewModel.removedNumbers.observe(viewLifecycleOwner) { numbers ->
            tvRemovedNumbers.text = numbers.toString()
        }
        viewModel.isGameFinished.observe(viewLifecycleOwner) { isGameFinished ->
            if (isGameFinished) {
                CustomAlertDialog(
                    context = requireContext(),
                    title = getString(R.string.congratulations_you_won),
                    positiveButtonText = getString(R.string.okay),
                    onPositiveButtonClick = { navController.navigateUp() }
                ).create()
                stopwatchScope.coroutineContext.cancelChildren()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initGameTime()
    }

    private fun startStopwatch(time: Long) {
        stopwatchScope.launch {
            var elapsedTime = time
            while (isActive) {
                viewModel.gameTime.value = elapsedTime
                elapsedTime += 1000
                delay(1000)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopwatchScope.coroutineContext.cancelChildren()
        viewModel.checkCurrentGame()
    }

    override fun clear() {
        adapter = null
    }
}