package com.example.basicassignment.main_screen.composables

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.example.basicassignment.R
import com.example.basicassignment.model.VideoData

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