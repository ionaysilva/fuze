package com.example.pandaapplication.model

data class Matches (
    val matches: List<MatchesItem>,
    val page: Int = 1,
    val totalPages: Int = 1
 )