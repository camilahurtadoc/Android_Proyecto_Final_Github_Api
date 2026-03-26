package com.example.feature.repos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.repos.domain.interfaces.ReposRepository
import com.example.feature.repos.ui.ReposState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(private val repository: ReposRepository) : ViewModel() {

    private val _reposState: MutableStateFlow<ReposState> = MutableStateFlow(ReposState.Loading)
    val reposState: StateFlow<ReposState> = _reposState

    private var currentPage: Int = 0

    fun getRepos() {
        viewModelScope.launch {
            _reposState.value = ReposState.Loading
            try {
                val result = repository.getRepos(currentPage)
                _reposState.value = ReposState.Success(result)
            } catch (e: Exception) {
                e.printStackTrace()
                _reposState.value = ReposState.Error
            }
        }
    }

    fun loadMore() {
        currentPage++
        viewModelScope.launch {
            try {
                val result = repository.getRepos(currentPage)
                _reposState.value = ReposState.LoadMore(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}