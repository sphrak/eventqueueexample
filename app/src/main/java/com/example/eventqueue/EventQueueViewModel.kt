package com.example.eventqueue

import EventQueue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mutableEventQueue

class EventQueueViewModel(): ViewModel() {
    private val _eventQueue = mutableEventQueue<Event>()
    val events: EventQueue<Event> = _eventQueue

    val list: List<String> = buildList {
        for (x in 1..100) {
            add("item $x")
        }
    }.toList()

    init {
        viewModelScope.launch {
            delay(4_000)
            val last = list.lastIndex
            _eventQueue.push(ScrollToStart(last))
        }
    }

    val state: StateFlow<List<String>> = MutableStateFlow(
        list
    )

}

sealed interface Event
data class ScrollToStart(val index: Int): Event