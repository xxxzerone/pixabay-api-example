package com.example.pixbayphoto.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pixbayphoto.ui.theme.Gray4
import com.example.pixbayphoto.ui.theme.Primary100

@Composable
fun SearchInputField(
    modifier: Modifier = Modifier,
    text: String = "",
    placeholder: String = "Placeholder",
    onValueChange: (String) -> Unit = {},
    onSearchAction: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = placeholder, color = Gray4, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Input",
                    tint = Primary100
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchAction(text) }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary100,
                unfocusedBorderColor = Gray4
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchInputFieldPreview() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        SearchInputField(
            text = "",
            placeholder = "Search",
            modifier = Modifier.padding(20.dp)
        )
    }
}