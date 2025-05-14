package com.example.uktvstarwarsapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultCard(item: Map<String, Any>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            item.forEach { (key, value) ->
                Text("$key: $value", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
