package com.example.pandaapplication.networking

import com.example.pandaapplication.model.MatchDetailResponse
import com.example.pandaapplication.model.MatchesItem
import com.example.pandaapplication.model.OpponentsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PandaScoreApi {

    @GET("/csgo/matches/")
    suspend fun getMatches(
        @Query("filter[begin_at]") today: String,
        @Query("sort") alwaysJson: String = "-status",
        @Query("page[size]") pageSize: Int = 50,
        @Query("page[number]") pageNumber: Int = 1

    ): Response<List<MatchesItem>>


    @GET("/matches/{match_id}")
    suspend fun getMatchDetail(
        @Path("match_id") matchId: Int,
    ): Response<MatchDetailResponse>


    @GET("/matches/{match_id}/opponents/")
    suspend fun getMatchOpponents(
        @Path("match_id") matchId: Int,
    ): Response<OpponentsResponse>



}