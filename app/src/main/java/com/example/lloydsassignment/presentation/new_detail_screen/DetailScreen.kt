package com.example.lloydsassignment.presentation.new_detail_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.lloydsassignment.R
import com.example.lloydsassignment.core.Dimensions
import com.example.lloydsassignment.data.remote.model.NewsItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    newsItem: NewsItems,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "News Detail")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Background color
                    titleContentColor = colorResource(id = R.color.white), // Title color
                    navigationIconContentColor = colorResource(id = R.color.white) // Back icon color
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(Dimensions.contentPadding) // Adding some padding around the content
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(newsItem.urlToImage)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.imageHeight)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(Dimensions.spacerHeightLarge))
            Text(
                text = newsItem.title ?: "",
                style = MaterialTheme.typography.headlineLarge,
                color = colorResource(id = R.color.black),
            )
            Spacer(modifier = Modifier.height(Dimensions.spacerHeightSmall))
            Text(
                text = newsItem.content ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.black)
            )
        }
    }
}