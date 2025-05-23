package com.orlovdanylo.fromonetoninegame.presentation.menu

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import com.orlovdanylo.fromonetoninegame.ButtonActions
import com.orlovdanylo.fromonetoninegame.GameMode
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.presentation.alert_dialog.AlertDialogManager
import com.orlovdanylo.fromonetoninegame.presentation.core.BaseFragment
import com.orlovdanylo.fromonetoninegame.presentation.game.models.GameSettingsBundle
import com.orlovdanylo.fromonetoninegame.utils.logEventClickListener

class MenuFragment : BaseFragment<MenuViewModel>() {

    override val layoutId: Int = R.layout.fragment_menu
    override val viewModel: MenuViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkStoredGame()

        view.findViewById<AppCompatButton>(R.id.btnContinue).apply {
            logEventClickListener(requireActivity(), ButtonActions.CONTINUE) {
                navigateToGame(false, GameMode.UNKNOWN)
            }
            viewModel.hasStoredGame.observe(viewLifecycleOwner) {
                isEnabled = it
            }
        }

        view.findViewById<AppCompatButton>(R.id.btnNewGame)
            .logEventClickListener(requireActivity(), ButtonActions.NEW_GAME) {
                AlertDialogManager(requireContext()).showGameModes(
                    actionClassic = {
                        navigateToGame(true, GameMode.CLASSIC)
                    },
                    actionRandom = {
                        navigateToGame(true, GameMode.RANDOM)
                    }
                )
            }

        view.findViewById<AppCompatButton>(R.id.ivStatistics)
            .logEventClickListener(requireActivity(), ButtonActions.STATISTICS) {
                val action = MenuFragmentDirections.actionMenuFragmentToStatisticsFragment()
                navController.navigate(action)
            }

        view.findViewById<AppCompatButton>(R.id.btnHowToPlay)
            .logEventClickListener(requireActivity(), ButtonActions.HOW_TO_PLAY) {
                val action = MenuFragmentDirections.actionMenuFragmentToHowToPlayFragment()
                navController.navigate(action)
            }

        view.findViewById<ImageView>(R.id.ivInfo)
            .logEventClickListener(requireActivity(), ButtonActions.INFO) {
                val action = MenuFragmentDirections.actionMenuFragmentToAboutGameFragment()
                navController.navigate(action)
            }
    }

    private fun navigateToGame(isNewGame: Boolean, gameMode: GameMode) {
        val action = MenuFragmentDirections.actionMenuFragmentToGameFragment(GameSettingsBundle(isNewGame, gameMode))
        navController.navigate(action)
    }

    override fun clear() = Unit
}