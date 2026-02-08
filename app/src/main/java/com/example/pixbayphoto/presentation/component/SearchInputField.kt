package com.example.pixbayphoto.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pixbayphoto.R
import com.example.pixbayphoto.ui.theme.Gray4
import com.example.pixbayphoto.ui.theme.Primary100

@Composable
fun SearchInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "label",
    onSearchAction: (String) -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = label,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search_normal),
                    contentDescription = "Search Button",
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchAction(value) }),
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary100,
                unfocusedBorderColor = Gray4,
                focusedLabelColor = Primary100,
                unfocusedLabelColor = Gray4,
                focusedTrailingIconColor = Primary100,
                unfocusedTrailingIconColor = Gray4
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchInputFieldPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SearchInputField(
            value = "",
            onValueChange = {},
            label = "Search"
        )
    }
}