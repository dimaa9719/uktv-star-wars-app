package com.example.uktvstarwarsapp

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalConfiguration
import com.example.uktvstarwarsap.SearchInputSection
import com.example.uktvstarwarsapp.model.Film
import com.example.uktvstarwarsapp.network.RetrofitClient

@Composable
fun SearchScreen() {
    val scope = rememberCoroutineScope()

    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var swapiResults by remember { mutableStateOf<List<Map<String, Any>>?>(null) }
    var sortBy by remember { mutableStateOf("name") }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun performSearch(query: String) {
        if (query in listOf("films", "vehicles", "starships")) {
            errorMessage = ""
            isLoading = true
            swapiResults = null

            scope.launch {
                try {
                    when (query) {
                        "films" -> {
                            val films = RetrofitClient.api.getFilms()
                            swapiResults = films.map { film ->
                                mapOf(
                                    "Title" to film.title,
                                    "Episode" to film.episode_id,
                                    "Director" to film.director,
                                    "Producer" to film.producer,
                                    "Release Date" to film.release_date,
                                    "Opening Crawl" to film.opening_crawl
                                )
                            }
                        }

                        else -> {
                            val results = RetrofitClient.api.getData(query)
                            swapiResults = results
                        }
                    }
                } catch (e: Exception) {
                    Log.e("SWAPI_ERROR", "Exception: ${e.localizedMessage}", e)
                    errorMessage = "Failed to load data. Please try again."
                } finally {
                    isLoading = false
                }
            }
        } else {
            errorMessage = "Invalid input. Try: films, starships, or vehicles"
        }
    }

    if (isLandscape) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            ) {
                SearchInputSection(
                    inputText,
                    onInputChanged = { inputText = it },
                    onSearch = {performSearch(inputText.text.trim().lowercase())},
                    onClear = {
                        swapiResults = null
                        inputText = TextFieldValue("")
                        errorMessage = ""
                    },
                    errorMessage
                )
            }

            Box(modifier = Modifier.weight(2f)) {
                ResultsSection(swapiResults, isLoading, sortBy) { selected ->
                    sortBy = selected
                    swapiResults = swapiResults?.sortedBy {
                        (it[sortBy] ?: "") as? String ?: ""
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            SearchInputSection(
                inputText,
                onInputChanged = { inputText = it },
                onSearch = { performSearch(query = inputText.text.trim().lowercase()) },
                onClear = {
                    swapiResults = null
                    inputText = TextFieldValue("")
                    errorMessage = ""
                },
                errorMessage
            )

            ResultsSection(swapiResults, isLoading, sortBy) { selected ->
                sortBy = selected
                swapiResults = swapiResults?.sortedBy {
                    (it[sortBy] ?: "") as? String ?: ""
                }
            }
        }
    }

//    if (isLandscape) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(24.dp)
//        ) {
//            // Left side: search box
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(end = 12.dp)
//            ) {
//                SearchInputSection(
//                    inputText,
//                    onInputChanged = { inputText = it },
//                    onSearch = { /* trigger logic */ },
//                    onClear = { /* clear logic */ },
//                    errorMessage
//                )
//            }
//
//            // Right side: result list
//            Box(modifier = Modifier.weight(2f)) {
//                ResultsSection(swapiResults, isLoading, sortBy) {
//                    sortBy = it
//                    swapiResults = swapiResults?.sortedBy { (it[sortBy] ?: "") as? String ?: "" }
//                }
//            }
//        }
//    } else {
//        // Portrait (your existing layout)
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(24.dp),
//            verticalArrangement = Arrangement.Center
//        ) {
//            OutlinedTextField(
//                value = inputText,
//                onValueChange = { inputText = it },
//                label = { Text("Enter: films, starships, or vehicles") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = {
//                    val query = inputText.text.trim().lowercase()
//                    if (query in listOf("films", "vehicles", "starships")) {
//                        errorMessage = ""
//                        isLoading = true
//                        swapiResults = null
//                        scope.launch {
//                            try {
//                                when (query) {
//                                    "films" -> {
//                                        val films: List<Film> = RetrofitClient.api.getFilms()
//                                        swapiResults = films.map { film ->
//                                            mapOf(
//                                                "Title" to film.title,
//                                                "Episode" to film.episode_id,
//                                                "Director" to film.director,
//                                                "Producer" to film.producer,
//                                                "Release Date" to film.release_date,
//                                                "Opening Crawl" to film.opening_crawl
//                                            )
//                                        }
//                                    }
//                                    else -> {
//                                        val results = RetrofitClient.api.getData(query)
//                                        swapiResults = results
//                                    }
//                                }
//                            } catch (e: Exception) {
//                                Log.e("SWAPI_ERROR", "Exception: ${e.localizedMessage}", e)
//                                errorMessage = "Failed to load data. Please try again."
//                            }
//                            isLoading = false
//                        }
//                    } else {
//                        errorMessage = "Invalid input. Try: films, starships, or vehicles"
//                    }
//                },
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            ) {
//                Text("Search")
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Button(
//                onClick = {
//                    swapiResults = null
//                    inputText = TextFieldValue("")
//                    errorMessage = ""
//                },
//                modifier = Modifier.align(Alignment.CenterHorizontally),
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
//            ) {
//                Text("Clear")
//            }
//
//            if (errorMessage.isNotEmpty()) {
//                Spacer(modifier = Modifier.height(12.dp))
//                Text(
//                    text = errorMessage,
//                    color = MaterialTheme.colorScheme.error
//                )
//            }
//
//            if (isLoading) {
//                Spacer(modifier = Modifier.height(12.dp))
//                CircularProgressIndicator()
//            }
//
//            if (swapiResults != null) {
//                Spacer(modifier = Modifier.height(24.dp))
//
//                Row {
//                    Text("Sort by:")
//                    Spacer(Modifier.width(8.dp))
//                    DropdownMenuSort(sortBy) { selected ->
//                        sortBy = selected
//                        swapiResults = swapiResults?.sortedBy {
//                            (it[sortBy] ?: "") as? String ?: ""
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    items(swapiResults!!) { item ->
//                        ResultCard(item)
//                    }
//                }
//            }
//        }
//
//    }

}
