package com.orlovdanylo.fromonetoninegame.presentation.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orlovdanylo.fromonetoninegame.Repositories
import com.orlovdanylo.fromonetoninegame.presentation.core.BaseViewModel
import kotlinx.coroutines.launch

class MenuViewModel : BaseViewModel() {

    private val gameRepository = Repositories.gameRepository

    val hasStoredGame: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkStoredGame() {
        viewModelScope.launch {
            hasStoredGame.value = gameRepository.isGameSavedInDatabase()
        }
    }
}