package com.example.basicassignment.main_screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.basicassignment.R
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

@Composable
fun VideoInfoColumn(video: VideoData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()) {

        Image(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.thumbnail),
            contentScale = ContentScale.Crop,
            contentDescription = "video")

        Row {

            Box(modifier = Modifier
                .padding(5.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(shape = RoundedCornerShape(50))
                        .background(Color.Gray)
                        .padding(4.dp),
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "account"
                )
            }

            Column(modifier = Modifier
                .padding(5.dp)
                ) {

                Text(
                    text = video.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall
                )

                Row {
                    VideoInfoText(label = video.channel)
                    VideoInfoText(label = " • ${video.views}K views")
                    VideoInfoText(label = " • ${video.date}days ago")
                }

                Row {
                    StatsCounter(amount = video.likes.toString(), type = " Likes")
                    Spacer(modifier = Modifier.width(5.dp))
                    StatsCounter(amount = video.comments.toString(), type = " Comments")
                }
            }
        }

        Text(
            modifier = Modifier.padding(5.dp),
            text = video.description,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun VideoInfoText(label: String){
    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = Color.Gray
    )
}

@Composable
fun StatsCounter(
    amount: String,
    type: String
) {
    val annotatedString = buildAnnotatedString {
        append(amount)
        withStyle(style = SpanStyle(
            color = Color.Blue,
            fontWeight = FontWeight.Bold),
        ) {
            append(type)
        }
    }

    Text(text = annotatedString)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VideoInfoColumn(video = VideoData(
        id = 782,
        title = "I have to make this Title at least two lines long so i can check it out, it can't be more than two lines so I'll ve checking it ",
        description = "Jarett",
        channel = "Ashraf",
        likes = 5313,
        comments = 8587,
        views = 1261,
        date = 9636
    ))
}