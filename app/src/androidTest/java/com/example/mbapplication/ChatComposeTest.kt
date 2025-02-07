package com.example.mbapplication

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mbapplication.ui.MBNavHost
import com.example.mbapplication.ui.theme.MBApplicationTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatComposeTest {

    @get:Rule val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testMain() {
        with(composeRule) {
            onRoot().printToLog("TAG")
            onNodeWithText("Title").assertExists()
            val messageList = onNodeWithContentDescription("MessageList")
            messageList.assertExists()
            performInput("Hello")
            onAllNodesWithText("Hello").assertCountEquals(1)
            val helloNode = onNode(hasParent(hasContentDescription("MessageList")).and(hasText("Hello")))
            helloNode.assertExists()
            helloNode.performClick()
            val backButton = onNodeWithContentDescription("BackButton")
            backButton.assertExists()
            onAllNodesWithText("Hello").assertCountEquals(3)
            performInput("world")
            onAllNodesWithText("world").assertCountEquals(2)
            backButton.performClick()
            onNodeWithText("Title").assertExists()
        }
    }

    private fun performInput(message: String) {
        with(composeRule) {
            val messageInputBox = onNodeWithContentDescription("MessageInputBox")
            messageInputBox.assertExists()
            messageInputBox.performTextInput(message)
            onNodeWithContentDescription("SendButton").performClick()
        }
    }
}