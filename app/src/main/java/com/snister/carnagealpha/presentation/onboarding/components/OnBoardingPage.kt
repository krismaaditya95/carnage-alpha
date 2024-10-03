package com.snister.carnagealpha.presentation.onboarding.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.snister.carnagealpha.R
import com.snister.carnagealpha.presentation.Dimensions.MediumPadding24dp
import com.snister.carnagealpha.presentation.Dimensions.MediumPadding30dp
import com.snister.carnagealpha.presentation.onboarding.Page
import com.snister.carnagealpha.presentation.onboarding.pages
import com.snister.carnagealpha.ui.theme.CarnageAlphaTheme

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page
){
    Column(modifier = modifier){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.8f),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(MediumPadding24dp))
        Text(
            text = page.title,
            modifier = Modifier.padding(horizontal = MediumPadding30dp),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.black)
        )
        Text(
            text = page.description,
            modifier = Modifier.padding(horizontal = MediumPadding30dp),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.purple_700)
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun OnBoardingPagePreview(){
    CarnageAlphaTheme {
        OnBoardingPage(
            page = pages[0]
        )
    }
}