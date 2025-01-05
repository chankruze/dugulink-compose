package com.geekofia.dugulink.onboarding.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.geekofia.dugulink.onboarding.data.getOnboardingPages
import com.geekofia.dugulink.onboarding.ui.components.OnboardingFooter
import com.geekofia.dugulink.onboarding.ui.components.OnboardingPageContent
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pages = getOnboardingPages()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        pages.size
    }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingPageContent(page = pages[page])
        }

        OnboardingFooter(
            currentPage = pagerState.currentPage,
            totalPages = pages.size,
            onNextClicked = {
                if (pagerState.currentPage < pages.size - 1) {
                    coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                } else {
                    onFinish()
                }
            },
            onSkipClicked = onFinish
        )
    }
}
