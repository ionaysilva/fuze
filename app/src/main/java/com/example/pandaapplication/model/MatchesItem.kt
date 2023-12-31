package com.example.pandaapplication.model

data class MatchesItem(
    val begin_at: String,
    val detailed_stats: Boolean,
    val draw: Boolean,
    val end_at: Any,
    val forfeit: Boolean,
    val game_advantage: Any,
    val id: Int,
    val league: League,
    val league_id: Int,
    val live: Live,
    val match_type: String,
    val modified_at: String,
    val name: String,
    val number_of_games: Int,
    val opponents: List<Opponent>,
    val original_scheduled_at: String,
    val rescheduled: Boolean,
    val results: List<Result>,
    val scheduled_at: String,
    val serie: Serie,
    val serie_id: Int,
    val slug: String,
    val status: String,
    val streams_list: List<Streams>,
    val tournament: Tournament,
    val tournament_id: Int,
    val videogame: Videogame,
    val videogame_version: Any,
    val winner: Any,
    val winner_id: Any,
    val winner_type: String
)