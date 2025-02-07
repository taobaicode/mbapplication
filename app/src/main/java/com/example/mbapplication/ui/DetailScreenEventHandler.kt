package com.example.mbapplication.ui

interface DetailScreenEventHandler {
    fun addMessage(message: String)
    fun onBack()

    interface Default : DetailScreenEventHandler {
        override fun addMessage(message: String) = Unit
        override fun onBack() = Unit
    }

    object NoOp : Default
}
