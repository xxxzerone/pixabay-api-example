package com.example.pixbayphoto.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchInput(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    onSearchAction: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Input")
            },
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(10.dp),
            label = { Text(label, fontSize = 24.sp, fontWeight = FontWeight.SemiBold) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchAction(value) }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF009688),
                unfocusedBorderColor = Color(0xFFBFBFBF),
                focusedTrailingIconColor = Color(0xFF009688),
                unfocusedTrailingIconColor = Color(0xFFB6B6B6),
                unfocusedLabelColor = Color(0xFFB6B6B6)
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchInputPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchInput(
            value = "",
            label = "Search",
            onValueChange = {},
            onSearchAction = {}
        )
    }
}