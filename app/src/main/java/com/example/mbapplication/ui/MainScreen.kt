package com.example.mbapplication.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mbapplication.model.ChatMessage

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onOpenThread: (ChatMessage) -> Unit = {}
 ) {
    val mainViewModel : MainViewModel = hiltViewModel<MainViewModel>()
    MainScreen(
        input = MainScreenInput(
            mainViewModel.rootMessages.collectAsState().value.mapNotNull {it.firstOrNull()}
        ),
        modifier = modifier,
        eventHandler = object : MainEventHandler {
            override fun onNewMessage(message: String) {
                mainViewModel.addRootMessage(message)
            }

            override fun onReply(chatMessage: ChatMessage) {
                mainViewModel.setCurrentThread(chatMessage)
                onOpenThread(chatMessage)
            }
        }
    )
}

@Composable
fun MainScreen(
    input: MainScreenInput,
    modifier: Modifier = Modifier,
    eventHandler: MainEventHandler = MainEventHandler.NoOp
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            MessageInputBox {
                eventHandler.onNewMessage(it)
            }
        },
        topBar = {
            Row {
                Text("Title", style = MaterialTheme.typography.titleLarge)
            }
        },
//        contentWindowInsets = WindowInsets.statusBars
    ) { paddingValue ->
        Column(modifier = Modifier.padding(paddingValue)) {
            MessageList(input.messages) {
                eventHandler.onReply(it)
            }
        }
    }
}