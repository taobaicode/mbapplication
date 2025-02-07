package com.example.mbapplication.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbapplication.model.ChatMessage
import com.example.mbapplication.repository.ChatRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@HiltViewModel(assistedFactory = ThreadViewModel.Factory::class)
class ThreadViewModel @AssistedInject constructor(
    @Assisted private val currentThread: ChatMessage,
    private val chatRepository: ChatRepository
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(currentThread: ChatMessage): ThreadViewModel
    }

    private val _threadMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val threadMessage = _threadMessages.asStateFlow()

    init {
        viewModelScope.launch {
            chatRepository.getThreadMessages(currentThread).collect {
                Log.d("ThreadViewModel", "threadMessages: $it")
                _threadMessages.emit(it)
            }
        }
    }

    fun addMessage(message: String) {
        viewModelScope.launch {
            chatRepository.addThreadMessage(currentThread, message)
        }
    }
}