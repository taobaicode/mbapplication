package com.example.mbapplication.ui

import com.example.mbapplication.model.ChatMessage

interface MainEventHandler {
    fun onNewMessage(message: String)
    fun onReply(chatMessage: ChatMessage)

    interface Default : MainEventHandler {
        override fun onNewMessage(message: String) = Unit
        override fun onReply(chatMessage: ChatMessage) = Unit
    }

    object NoOp: Default
}