package com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.core.presentation.MainActivityAction
import com.snister.carnagealpha.core.presentation.MainActivityState
import com.snister.carnagealpha.core.utils.CurrencyFormatter
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.ui.theme.*

@Composable
fun SourceLedgerListWidget(
    state: MainActivityState,
    onAction: (MainActivityAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        contentPadding = PaddingValues(top = 14.dp)
    ) {
        itemsIndexed(state.sourceLedgerList){
            index, item ->
            SourceLedgerItemWidget(
                sourceLedgerEntity = item,
                onAction = onAction,
                state = state,
                index = index
            )
        }
    }
}

@Composable
fun SourceLedgerItemWidget(
    sourceLedgerEntity: SourceLedgerEntity,
    onAction: (MainActivityAction) -> Unit,
    state: MainActivityState,
    index: Int
) {

    ElevatedCard(
        onClick = {
            sourceLedgerEntity.sourceLedgerId?.let {
                onAction(MainActivityAction.OnSourceLedgerItemSelected(
                    selectedSourceLedgerIndex = index,
                    selectedSourceLedgerId = it,
                ))
            }
        },

        modifier = when{
            state.selectedSourceLedgerIndexFromList == index -> {
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(bottom = 14.dp)
                    .border(1.dp, cDC5F00, RoundedCornerShape(4.dp))
            }
            else -> {
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(bottom = 14.dp)
                    .border(1.dp, c373A40, RoundedCornerShape(4.dp))
            }


        },


        shape = RoundedCornerShape(4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = sourceLedgerEntity.sourceLedgerName,
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = CurrencyFormatter.formatToRupiah(sourceLedgerEntity.sourceLedgerBalance),
                color = cDC5F00,
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    }
}

@Preview
@Composable
fun SourceLedgerItemWidgetPreview(modifier: Modifier = Modifier) {
    SourceLedgerItemWidget(
        sourceLedgerEntity = SourceLedgerEntity(
            sourceLedgerId = 1,
            sourceLedgerName = "Debit Card",
            sourceLedgerBalance = 100000,
        ),
        onAction = {},
        state = MainActivityState(),
        index = 1
    )
}