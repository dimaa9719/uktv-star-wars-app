package com.example.uktvstarwarsapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultsSection(
    swapiResults: List<Map<String, Any>>?,
    isLoading: Boolean,
    sortBy: String,
    onSortSelected: (String) -> Unit
) {
    if (isLoading) {
        Spacer(modifier = Modifier.height(12.dp))
        CircularProgressIndicator()
    }

    swapiResults?.let {
        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text("Sort by:")
            Spacer(Modifier.width(8.dp))
            DropdownMenuSort(sortBy) { selected ->
                onSortSelected(selected)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(swapiResults) { item ->
                ResultCard(item)
            }
        }
    }
}
