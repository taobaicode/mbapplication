package com.example.mbapplication.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.mbapplication.model.ChatMessage

@Composable
fun MessageList(
    messages: List<ChatMessage>,
    modifier: Modifier = Modifier,
    onClick: (ChatMessage)-> Unit = { }
) {
    val state = rememberLazyListState()
    LazyColumn(
        modifier = modifier.fillMaxWidth().semantics { contentDescription = "MessageList" },
        state = state
    ) {
        items(
            count = messages.size, key = { messages[it].id },
            contentType = {messages[it].question}
        ) { index ->
            val question = messages[index].question
            Row(horizontalArrangement = if (question) Arrangement.Start else Arrangement.End,
                modifier = Modifier.fillMaxWidth()
                    .clickable { onClick(messages[index]) }) {
                Text(text = messages[index].message, modifier = Modifier.widthIn(max = 300.dp))
            }
        }
    }
}