package com.example.uktvstarwarsap

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun SearchInputSection(
    inputText: TextFieldValue,
    onInputChanged: (TextFieldValue) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit,
    errorMessage: String
) {
    Column {
        OutlinedTextField(
            value = inputText,
            onValueChange = onInputChanged,
            label = { Text("Enter: films, starships, or vehicles") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSearch,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onClear,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Clear")
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}
