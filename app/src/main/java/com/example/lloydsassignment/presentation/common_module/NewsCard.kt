package com.example.lloydsassignment.presentation.common_module

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.lloydsassignment.R
import com.example.lloydsassignment.core.Dimensions
import com.example.lloydsassignment.core.toReadableDate
import com.example.lloydsassignment.data.remote.model.NewsItems


@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    newsItems: NewsItems,
    onClick: ((NewsItems) -> Unit)? = null
) {

    val context = LocalContext.current
    Row(
        modifier = modifier
            .padding(all = Dimensions.padding5)
            .clickable { onClick?.invoke(newsItems) },
    ) {
        AsyncImage(
            modifier = Modifier
                .size(Dimensions.height120)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context).data(newsItems.urlToImage).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(top = Dimensions.heightSmall, start = Dimensions.heightSmall)
        ) {
            Text(
                text = newsItems.title.toString(),
                fontSize = Dimensions.fontSize15,
                style = MaterialTheme.typography.bodyMedium.copy(),
                color = colorResource(id = R.color.black),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(Dimensions.padding5))
            Text(
                text = newsItems.source?.name.toString(),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = colorResource(id = R.color.teal_200),
                fontSize = Dimensions.fontSize13,
            )
            Spacer(modifier = Modifier.height(Dimensions.padding5))
            Text(
                text = newsItems.publishedAt?.toReadableDate().toString(),
                style = MaterialTheme.typography.labelSmall,
                color = colorResource(id = R.color.black),
                fontSize = Dimensions.fontSize12,
            )

        }
    }
}

/*
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardPreview() {
    JetpackComposeSampleProjectTheme(dynamicColor = false) {
        ArticleCard(
            article = ArticlesItem(
                author = "",
                content = "",
                description = "",
                publishedAt = "2 hours",
                source = Source(id = "", name = "BBC"),
                title = "Her train broke down. Her phone died. And then she met her Saver in a",
                url = "",
                urlToImage = "https://ichef.bbci.co.uk/live-experience/cps/624/cpsprodpb/11787/production/_124395517_bbcbreakingnewsgraphic.jpg"
            )
        )
    }
}*/
