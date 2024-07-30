package com.orlovdanylo.fromonetoninegame.presentation.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orlovdanylo.fromonetoninegame.Repositories
import com.orlovdanylo.fromonetoninegame.presentation.core.BaseViewModel
import com.orlovdanylo.fromonetoninegame.domain.model.StatisticsModel
import kotlinx.coroutines.launch

class StatisticsViewModel : BaseViewModel() {

    private val statisticsRepository = Repositories.statisticsRepository

    val statistics: MutableLiveData<List<StatisticsModel>> = MutableLiveData()

    fun loadStatistics() {
        viewModelScope.launch {
            statistics.value = statisticsRepository.getStatistics()
        }
    }
}