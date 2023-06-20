package com.example.pandaapplication.model

data class OpponentX(
    val acronym: String,
    val current_videogame: CurrentVideogame,
    val id: Int,
    val image_url: String,
    val location: String,
    val modified_at: String,
    val name: String,
    val players: List<Player>,
    val slug: String

)