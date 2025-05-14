package com.example.uktvstarwarsapp.network

import com.example.uktvstarwarsapp.model.Film
import com.example.uktvstarwarsapp.model.SwapiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://swapi.info/api/"

    val api: SwapiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SwapiService::class.java)
    }
}

interface SwapiService {
    @GET("{type}")
    suspend fun getData(@Path("type") type: String): List<Map<String, Any>>
    //suspend fun getData(@Path("type") type: String): SwapiResponse

    @GET("films")
    suspend fun getFilms(): List<Film>
}
