package com.example.mbapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbapplication.model.ChatMessage
import com.example.mbapplication.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {
    val rootMessages : StateFlow<List<List<ChatMessage>>> = chatRepository.rootMessages.stateIn(viewModelScope, WhileSubscribed(stopTimeoutMillis = 5000), emptyList())

    fun addRootMessage(message : String) {
        viewModelScope.launch {
            chatRepository.addRootMessage(message)
        }
    }

    var currentThread : ChatMessage? = null

        private set
    fun setCurrentThread(chatMessage: ChatMessage) {
        currentThread = chatMessage
    }
}