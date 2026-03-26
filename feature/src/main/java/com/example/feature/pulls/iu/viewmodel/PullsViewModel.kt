package com.example.feature.pulls.iu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.pulls.domain.interfaces.PullsRepository
import com.example.feature.pulls.iu.PullsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PullsViewModel @Inject constructor(private val repository: PullsRepository) : ViewModel() {

    private val _pullsState: MutableStateFlow<PullsState> = MutableStateFlow(PullsState.Loading)
    val pullsState: StateFlow<PullsState> = _pullsState

    fun getPulls(owner: String, repo: String) {
        viewModelScope.launch {
            try {
                val result = repository.getPulls(owner, repo)
                _pullsState.value = PullsState.Success(result)
            } catch (e: Exception) {
                e.printStackTrace()
                _pullsState.value = PullsState.Error
            }
        }
    }
}