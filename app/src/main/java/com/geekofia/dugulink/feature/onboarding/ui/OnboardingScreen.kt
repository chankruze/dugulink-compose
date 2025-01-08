package com.geekofia.dugulink.feature.onboarding.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.geekofia.dugulink.feature.onboarding.data.getOnboardingPages
import com.geekofia.dugulink.feature.onboarding.ui.components.OnboardingPageContent
import com.geekofia.dugulink.feature.onboarding.ui.components.OnboardingPageIndicator
import com.geekofia.dugulink.feature.onboarding.viewmodel.OnboardingViewModel
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onNavigateToAuth: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val pages = getOnboardingPages()
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val isLastPage = pagerState.currentPage == pages.size - 1

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Main page content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { position ->
            OnboardingPageContent(pages[position])
        }
        // Page indicator and buttons
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Page indicator
            OnboardingPageIndicator(
                pageCount = pages.size,
                currentPage = pagerState.currentPage,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            // Next or Get Started button
            this@Column.AnimatedVisibility(
                visible = true,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Button(
                    onClick = {
                        if (isLastPage) {
                            viewModel.onOnboardingComplete(onNavigateToAuth)
                        } else {
                            // Scroll to next page
                            viewModel.viewModelScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }
                ) {
                    Text(if (isLastPage) "Get Started" else "Next")
                }
            }
        }
    }
}
