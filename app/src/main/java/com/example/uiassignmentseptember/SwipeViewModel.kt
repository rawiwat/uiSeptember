package com.example.uiassignmentseptember

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SwipeViewModel(initialOffset: Int) : ViewModel() {
    private val _currentOffset = MutableStateFlow(initialOffset)
    val currentOffset: StateFlow<Int> = _currentOffset
    private val fullScreenHeight = initialOffset * 2

    fun changeOffset(input:Int) {
        _currentOffset.value += input
        if (currentOffset.value < 0) {
            _currentOffset.value -= currentOffset.value
        }

        if (_currentOffset.value >= (fullScreenHeight * 3 / 4)) {
            _currentOffset.value = fullScreenHeight /2
        }
    }

    fun setOffsetToDefault() {
        _currentOffset.value = fullScreenHeight / 2
    }
}