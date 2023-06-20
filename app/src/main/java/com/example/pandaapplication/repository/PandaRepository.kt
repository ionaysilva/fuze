package com.example.pandaapplication.repository

import com.example.pandaapplication.model.MatchDetailResponse
import com.example.pandaapplication.model.MatchesItem
import com.example.pandaapplication.model.OpponentsResponse
import com.example.pandaapplication.networking.PandaScoreApi
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

class PandaRepository(private val pandaScoreApi: PandaScoreApi) {


    suspend fun getMatches(today: String, pageNumber: Int): Response<List<MatchesItem>> {
        return pandaScoreApi.getMatches(today, pageNumber = pageNumber)
    }

    suspend fun getMatchDetail(matchId: Int):  Response<MatchDetailResponse> {
        return pandaScoreApi.getMatchDetail(matchId)
    }

    suspend fun getMatchOpponents(matchId: Int):  Response<OpponentsResponse> {
        return pandaScoreApi.getMatchOpponents(matchId)
    }

}