package com.example.uktvstarwarsapp

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun DropdownMenuSort(selected: String, onSortSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text("Sort by: $selected")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("name") },
                onClick = {
                    expanded = false
                    onSortSelected("name")
                }
            )
            DropdownMenuItem(
                text = { Text("title") },
                onClick = {
                    expanded = false
                    onSortSelected("title")
                }
            )
        }
    }
}

