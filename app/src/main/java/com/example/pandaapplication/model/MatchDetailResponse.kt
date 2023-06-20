package com.example.pandaapplication.model

data class MatchDetailResponse(
    val begin_at: String,
    val detailed_stats: Boolean,
    val id: Int,
    val league: LeagueX,
    val name: String,
    val serie: Serie,
    val serie_id: Int,
)