package com.orlovdanylo.fromonetoninegame.presentation.game

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.domain.model.TimeModel
import com.orlovdanylo.fromonetoninegame.presentation.alert_dialog.AlertDialogManager
import com.orlovdanylo.fromonetoninegame.presentation.core.BaseFragment
import com.orlovdanylo.fromonetoninegame.presentation.game.adapter.GameAdapter
import com.orlovdanylo.fromonetoninegame.presentation.game.adapter.GridSpacingItemDecoration
import com.orlovdanylo.fromonetoninegame.presentation.game.bottom_view.GameBottomMenuActions
import com.orlovdanylo.fromonetoninegame.presentation.game.bottom_view.GameBottomMenuView
import com.orlovdanylo.fromonetoninegame.presentation.game.models.GameSettingsBundle
import com.orlovdanylo.fromonetoninegame.utils.serializable
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
    private var animatedPair: Pair<Int, Int>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvGameTime = view.findViewById<TextView>(R.id.tvGameTime)
        val tvRemovedNumbers = view.findViewById<TextView>(R.id.tvRemovedNumbers)

        adapter = GameAdapter { model -> viewModel.tap(model.id) }

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvDigits).apply {
            layoutManager = GridLayoutManager(context, 9)
            adapter = this@GameFragment.adapter
            itemAnimator = null
            addItemDecoration(GridSpacingItemDecoration(9, 12, true))
        }

        handleNewGameArgument(false) {
            settings -> viewModel.initGame(settings.isNewGame, settings.mode)
        }

        viewModel.startTime.observe(viewLifecycleOwner) { time: Long? ->
            time?.let { startStopwatch(it) }
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
                adapter?.notifyPairItemsChanged(pair.first, pair.second)
                viewModel.selectedModel.value = null
            }
        }

        viewModel.selectedModel.observe(viewLifecycleOwner) { model ->
            val selectedItem = if (model != null) {
                val item = viewModel.gameModels.value?.find { it.id == model.id } ?: return@observe
                item.also { it.isSelected = true }
            } else {
                val item = viewModel.gameModels.value?.find { it.isSelected } ?: return@observe
                item.also { it.isSelected = false }
            }
            adapter?.notifyItemChanged(selectedItem.id)
        }

        viewModel.pairNumbers.observe(viewLifecycleOwner) { pair ->
            adapter?.notifyItemChanged(pair.first)
            adapter?.notifyItemChanged(pair.second)
        }

        viewModel.removedNumbers.observe(viewLifecycleOwner) { numbers ->
            tvRemovedNumbers.text = numbers.toString()
        }

        viewModel.isGameFinished.observe(viewLifecycleOwner) { isGameFinished ->
            if (isGameFinished) {
                val manager = AlertDialogManager(requireContext())
                manager.showCongratulations { navController.navigateUp() }
                stopwatchScope.coroutineContext.cancelChildren()
            }
        }

        viewModel.availablePairs.observe(viewLifecycleOwner) {
            stopPreviousAnimation(recyclerView)
        }

        initBottomView(view, recyclerView)
    }

    private fun initBottomView(view: View, recyclerView: RecyclerView) {
        val btnUndo = view.findViewById<AppCompatImageButton>(R.id.btnUndo)
        val btnRedo = view.findViewById<AppCompatImageButton>(R.id.btnRedo)
        val btnAddDigits = view.findViewById<AppCompatImageButton>(R.id.btnAddDigits)
        val btnTip = view.findViewById<AppCompatImageButton>(R.id.btnTip)

        view.findViewById<GameBottomMenuView>(R.id.gameBottomMenu).apply {
            actions = object : GameBottomMenuActions {
                override fun undo() {
                    viewModel.undo(viewModel.gameModels.value!!, viewModel.removedNumbers)
                    viewModel.updateAvailablePairs()
                }

                override fun redo() {
                    viewModel.redo(viewModel.gameModels.value!!, viewModel.removedNumbers)
                    viewModel.updateAvailablePairs()
                }

                override fun update() {
                    viewModel.updateNumbers()
                }

                override fun showTip() {
                    viewModel.fetchAvailablePair()?.let { pair ->
                        stopPreviousAnimation(recyclerView)
                        startPulseAnimation(recyclerView, pair)
                    }
                }
            }
        }

        viewModel.gameModelsCount.observe(viewLifecycleOwner) { count ->
            btnAddDigits.isEnabled = count < 1000
        }

        viewModel.undoStack.observe(viewLifecycleOwner) { stack ->
            btnUndo.isEnabled = stack.isNotEmpty()
        }

        viewModel.redoStack.observe(viewLifecycleOwner) { stack ->
            btnRedo.isEnabled = stack.isNotEmpty()
        }

        viewModel.availablePairs.observe(viewLifecycleOwner) { pairs ->
            btnTip.isEnabled = pairs.isNotEmpty()
        }
    }

    private fun startPulseAnimation(recyclerView: RecyclerView, pair: Pair<Int, Int>) {
        adapter?.startPulseAnimation(
            firstView = recyclerView.findViewHolderForAdapterPosition(pair.first)?.itemView,
            secondView = recyclerView.findViewHolderForAdapterPosition(pair.second)?.itemView
        )
        animatedPair = pair
    }

    private fun stopPreviousAnimation(recyclerView: RecyclerView) {
        animatedPair?.let { previousPair ->
            adapter?.stopPreviousAnimation(
                firstView = recyclerView.findViewHolderForAdapterPosition(previousPair.first)?.itemView,
                secondView = recyclerView.findViewHolderForAdapterPosition(previousPair.second)?.itemView,
            )
        }
    }

    override fun onResume() {
        super.onResume()
        handleNewGameArgument(true) {
            settings -> viewModel.initializeGameTime(settings.isNewGame)
        }
    }

    private fun handleNewGameArgument(clearArguments: Boolean, action: (settings: GameSettingsBundle) -> Unit) {
        arguments?.serializable<GameSettingsBundle>("settings")?.let {
            action.invoke(it)
            if (clearArguments) arguments?.clear()
        }
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