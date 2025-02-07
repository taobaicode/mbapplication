package com.example.mbapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mbapplication.model.ChatMessage

@Composable
fun DetailScreen(
    headMessage : ChatMessage,
    modifier: Modifier = Modifier,
    onBack : ()->Unit = {}
) {
    val detailViewModel : ThreadViewModel = hiltViewModel { factory: ThreadViewModel.Factory ->
        factory.create(headMessage)
    }
    DetailScreen(
        input = DetailScreenInput(
            detailViewModel.threadMessage.collectAsState().value
        ),
        modifier = modifier,
        eventHandler = object : DetailScreenEventHandler {
            override fun addMessage(message: String) {
                detailViewModel.addMessage(message)
            }
            override fun onBack() {
                onBack()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    input: DetailScreenInput,
    modifier: Modifier = Modifier,
    eventHandler: DetailScreenEventHandler = DetailScreenEventHandler.NoOp
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(input.messages.firstOrNull()?.message?:"")},
                navigationIcon = {
                    TextButton(onClick = {eventHandler.onBack()}, modifier = Modifier.padding(8.dp).size(48.dp).semantics { contentDescription = "BackButton" }) {
                        Text("<")
                    }
                }
            )
        },
        bottomBar = {
            MessageInputBox {
                eventHandler.addMessage(it)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            MessageList(input.messages)
        }
    }
}