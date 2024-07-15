package com.example.foodie2306.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.foodie2306.R
import kotlinx.coroutines.delay


@Composable
fun AnimationScreen(
    modifier: Modifier,
    onAnimationComplete: () -> Unit
) {
    val lottie by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.animation))
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(2000) // Delay for 2 seconds
        onAnimationComplete()
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF15412))
    ) {
        LottieAnimation(
            composition = lottie,
            modifier = Modifier.fillMaxSize(),
            iterations = LottieConstants.IterateForever
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Anim() {
  //  AnimationScreen(modifier = Modifier)
}