package com.example.basicassignment.main_screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.basicassignment.main_screen.composables.VideoInfoColumn
import com.example.basicassignment.model.VideoData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel: MainViewModel = hiltViewModel()

            MainScreen(
                state = viewModel.state.value,
                onEvent = viewModel::onEvent
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainStates,
    onEvent: (MainEvents) -> Unit
) {

    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    Surface {

        Column {

            SearchBar(
                query = searchText,
                onQueryChange = {
                    searchText = it
                    onEvent(MainEvents.GetFilteredVideoList(searchText))
                    Log.d("TAG", "MainScreen: executed")
                },
                onSearch = {
                    isSearching = false
                },
                active = isSearching,
                onActiveChange = { isSearching = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                placeholder = {
                    Text(text = "Search now")
                },
            ){

                LazyColumn {
                    items(state.filteredVideos){video ->
                        VideoInfoColumn(video = video)
                    }
                }
            }
            if (state.isLoading){
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = androidx.compose.ui.Alignment.Center){
                CircularProgressIndicator()
                }
            } else {
                LazyColumn {
                    items(state.allVideos) { video ->
                        VideoInfoColumn(video = video)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VideoInfoColumn(video = VideoData(
        id = 782,
        title = "I have to make this Title at least two lines long so i can check it out, it can't be more than two lines so I'll ve checking it ",
        description = "Test",
        channel = "Ashraf",
        likes = 5313,
        comments = 8587,
        views = 1261,
        date = 9636
    ))
}