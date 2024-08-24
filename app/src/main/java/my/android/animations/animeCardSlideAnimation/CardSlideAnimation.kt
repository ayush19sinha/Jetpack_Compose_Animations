package my.android.animations.animeCardSlideAnimation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardSlideAnimation() {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { animeList.size })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        BackgroundImage(pagerState)
        AnimeCarousel(pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BackgroundImage(pagerState: PagerState) {
    Crossfade(
        targetState = animeList[pagerState.currentPage].imgRes,
        label = "Background Image",
        animationSpec = tween(500)
    ) { targetState ->
        Image(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.5f },
            painter = painterResource(id = targetState),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AnimeCarousel(pagerState: PagerState) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        contentPadding = PaddingValues(64.dp),
        key = { animeList[it].imgRes },
    ) { index ->
        AnimeCard(index, pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AnimeCard(index: Int, pagerState: PagerState) {
    val isCurrentPage = index == pagerState.currentPage
    val imgScale by animateFloatAsState(
        animationSpec = tween(500),
        targetValue = if (isCurrentPage) 1.4f else 1f,
        label = "Image Scale"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimeImage(index, imgScale)
        Spacer(modifier = Modifier.height(100.dp))
        AnimeTitle(index, isCurrentPage)
        Spacer(modifier = Modifier.height(10.dp))
        AnimeDescription(index, isCurrentPage)
    }
}

@Composable
private fun AnimeImage(index: Int, imgScale: Float) {
    Image(
        modifier = Modifier
            .size(200.dp)
            .aspectRatio(3 / 4f)
            .scale(imgScale)
            .shadow(32.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)),
        painter = painterResource(id = animeList[index].imgRes),
        contentDescription = animeList[index].title,
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun AnimeTitle(index: Int, isCurrentPage: Boolean) {
    AnimatedVisibility(
        visible = isCurrentPage,
        enter = fadeIn(animationSpec = tween(1000)),
        exit = fadeOut(animationSpec = tween(500))
    ) {
        Text(
            text = animeList[index].title,
            fontSize = 25.sp,
            color = Color.White,
        )
    }
}

@Composable
private fun AnimeDescription(index: Int, isCurrentPage: Boolean) {
    AnimatedVisibility(
        visible = isCurrentPage,
        enter = fadeIn(animationSpec = tween(1000, delayMillis = 500)),
        exit = fadeOut(animationSpec = tween(800))
    ) {
        Text(
            text = stringResource(id = animeList[index].desc),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 6.dp)
        )
    }
}

@Preview(device = Devices.PIXEL_6)
@Composable
private fun CardSlideAnimationPreview() {
    CardSlideAnimation()
}
