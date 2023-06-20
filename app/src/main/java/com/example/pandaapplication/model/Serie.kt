package com.example.pandaapplication.model

data class Serie(
    val begin_at: String,
    val end_at: String,
    val full_name: String,
    val id: Int,
    val league_id: Int,
    val modified_at: String,
    val name: String?,
    val season: String,
    val slug: String,
    val winner_id: Int,
    val winner_type: String,
    val year: Int
)