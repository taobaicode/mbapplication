package com.example.mbapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mbapplication.model.ChatMessage
import com.example.mbapplication.repository.ChatRepository
import com.example.mbapplication.ui.MBNavHost
import com.example.mbapplication.ui.MainScreen
import com.example.mbapplication.ui.MainViewModel
import com.example.mbapplication.ui.ThreadViewModel
import com.example.mbapplication.ui.theme.MBApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val threadViewModel : ThreadViewModel by viewModels(factoryProducer = { object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ThreadViewModel(currentThread = ChatMessage(1, "",false), ChatRepository()) as T
        }
    } })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MBApplicationTheme {
                MBNavHost(modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars.add(WindowInsets.ime)))
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val mainViewModel : MainViewModel = viewModel()
    val firstMessage  =  mainViewModel.rootMessages.collectAsState().value.firstOrNull()?.firstOrNull()?.message
    mainViewModel.currentThread?.run {
        val threadViewModel = hiltViewModel { factory: ThreadViewModel.Factory ->
            factory.create(this)
        }
    }

    Text(
        text = "Hello $name!$firstMessage",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MBApplicationTheme {
        Greeting("Android")
    }
}