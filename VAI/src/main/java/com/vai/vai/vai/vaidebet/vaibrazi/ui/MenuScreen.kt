//package com.vai.vai.vai.vaidebet.vaibrazi.ui
//
//import androidx.compose.animation.core.*
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.rotate
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import com.vai.vai.vai.vaidebet.vaibrazi.data.LevelRepository
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun MenuScreen(onStartGame: (Int) -> Unit) {
//    val context = LocalContext.current
//    val pagerState = rememberPagerState(pageCount = { 10 })
//    val levelRepository = LevelRepository()
//    val scope = rememberCoroutineScope()
//
//    var startAnimation by remember { mutableStateOf(false) }
//    val rotationAnim by animateFloatAsState(
//        targetValue = if (startAnimation) 360f else 0f,
//        animationSpec = tween(durationMillis = 1000)
//    )
//
//    LaunchedEffect(key1 = true) {
//        startAnimation = true
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "VAI SWIPE",
//            modifier = Modifier.rotate(rotationAnim)
//        )
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            ThemedButton(text = "<", onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } })
//            Spacer(modifier = Modifier.width(16.dp))
//            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) {
//                page ->
//                LevelCard(level = page + 1, board = levelRepository.getLevel(page + 1))
//            }
//            Spacer(modifier = Modifier.width(16.dp))
//            ThemedButton(text = ">", onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } })
//        }
//        Spacer(modifier = Modifier.height(32.dp))
//        ThemedButton(text = "Start Game", onClick = { onStartGame(pagerState.currentPage + 1) })
//        Spacer(modifier = Modifier.height(32.dp))
//        ThemedButton(text = "Privacy Policy...", onClick = { openUrl(context, "https://haliol.top/Privacyy".toCharArray()) })
//        Spacer(modifier = Modifier.height(16.dp))
//        ThemedButton(text = "FAQ", onClick = { openUrl(context, "https://haliol.top/FAQQ".toCharArray()) })
//    }
//}
//
