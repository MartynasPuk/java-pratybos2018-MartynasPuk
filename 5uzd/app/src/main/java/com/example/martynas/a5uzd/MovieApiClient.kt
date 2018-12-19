package com.example.martynas.a5uzd

import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*

interface MovieApiClient {

    @GET("movies") fun getMovies(@Header("Authorization") token:String): Observable<MovieEmbedded>
    @POST("movies") fun addMovie(@Body movie: Movie): Completable
    @DELETE("movies/{id}") fun deleteMovie(@Path("id") id: Int) : Completable
    @PUT("movies/{id}") fun updateMovie(@Path("id") id: Int, @Body movie: Movie) : Completable

    companion object {
        fun create(): MovieApiClient {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://127.0.0.1:8080/")
                .build()

            return retrofit.create(MovieApiClient::class.java)
        }
    }
}