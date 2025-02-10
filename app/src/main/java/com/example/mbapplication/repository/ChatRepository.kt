package com.example.mbapplication.repository

import com.example.mbapplication.model.ChatMessage
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor() {
    private val locker = Mutex()
    private val messages = mutableListOf<MutableList<ChatMessage>>()
    private val messageId = AtomicLong(0)

    suspend fun addRootMessage(message: String) {
        withContext(Dispatchers.Default) {
            locker.withLock {
                messages.add(
                    mutableListOf(
                        ChatMessage(messageId.incrementAndGet(), message, true),
                        ChatMessage(messageId.incrementAndGet(), message, false)
                    )
                )
                _rootMessages.emit(messages.map { it.toList() })
            }
        }
    }

    suspend fun addThreadMessage(headMessage: ChatMessage, message: String) {
        withContext(Dispatchers.Default) {
            locker.withLock {
                messages.firstOrNull { it.firstOrNull()?.id == headMessage.id }?.addAll(
                    listOf(
                        ChatMessage(messageId.incrementAndGet(), message, true),
                        ChatMessage(messageId.incrementAndGet(), message, false)
                    )
                )
                _rootMessages.emit(messages.map { it.toList() })
            }
        }
    }

    private val _rootMessages = MutableSharedFlow<List<List<ChatMessage>>>(replay = 1)
    val rootMessages = _rootMessages.asSharedFlow()

    fun getThreadMessages(rootMessage: ChatMessage) : Flow<List<ChatMessage>> = rootMessages.map {
        it.firstOrNull { it.firstOrNull()?.id == rootMessage.id } ?: emptyList()
    }
}