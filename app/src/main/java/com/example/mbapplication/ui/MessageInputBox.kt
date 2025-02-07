package com.example.mbapplication.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageInputBox(
    modifier: Modifier = Modifier,
    onSubmit : (String) -> Unit = {}
) {
    var value by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(value = value, onValueChange = {value = it},
        modifier = modifier.fillMaxWidth().padding(vertical = 32.dp).semantics { contentDescription = "MessageInputBox" },
        trailingIcon = {
            TextButton(
                onClick = {
                    onSubmit(value)
                    keyboardController?.hide()
                    value=""
                          },
                modifier = Modifier.size(48.dp).semantics { contentDescription = "SendButton" }) {
                Text("+", modifier = Modifier.size(32.dp), fontSize = 32.sp)
            }
        })
}