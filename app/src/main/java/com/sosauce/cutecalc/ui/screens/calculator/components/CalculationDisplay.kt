package com.sosauce.cutecalc.ui.screens.calculator.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sosauce.cutecalc.data.datastore.rememberDecimal
import com.sosauce.cutecalc.data.datastore.rememberUseSystemFont
import com.sosauce.cutecalc.ui.screens.calculator.CalculatorViewModel
import com.sosauce.cutecalc.ui.theme.nunitoFontFamily
import com.sosauce.cutecalc.utils.FormatTransformation
import com.sosauce.cutecalc.utils.formatNumber
import com.sosauce.cutecalc.utils.isErrorMessage

@Composable
fun CalculationDisplay(
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel
) {

    val useSystemFont by rememberUseSystemFont()
    val shouldFormat by rememberDecimal()
    val scrollState = rememberScrollState()
    val previewScrollState = rememberScrollState()
    val previewCanShowErrors by viewModel.previewShowErrors.collectAsStateWithLifecycle()


    LaunchedEffect(viewModel.textFieldState.text) {
        scrollState.animateScrollTo(scrollState.maxValue)
        previewScrollState.animateScrollTo(previewScrollState.maxValue)
    }


    Column(modifier) {
        Text(
            text = viewModel.evaluatedCalculation
                .formatNumber(shouldFormat)
                .takeIf { !it.isErrorMessage() || previewCanShowErrors } ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(previewScrollState),
            style = MaterialTheme.typography.displaySmall.copy(
                textAlign = TextAlign.End,
                fontWeight = FontWeight.SemiBold,
                color = if (!viewModel.evaluatedCalculation.isErrorMessage()) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else MaterialTheme.colorScheme.error
            )
        )
        DisableSoftKeyboard {
            BasicTextField(
                state = viewModel.textFieldState,
                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = MaterialTheme.typography.displayMedium.copy(
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = if (!useSystemFont) nunitoFontFamily else null,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.fillMaxWidth(),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                scrollState = scrollState,
                outputTransformation = if (shouldFormat) FormatTransformation else null
            )
        }
    }


}