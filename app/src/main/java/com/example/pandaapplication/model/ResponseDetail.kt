package com.example.pandaapplication.model

data class ResponseDetail(
    val leagueName: String,
    val serieName: String,
    val begin_at: String,
    val opponents: List<OpponentX>,
)

