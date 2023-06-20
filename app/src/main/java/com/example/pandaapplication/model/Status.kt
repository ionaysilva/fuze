package com.example.pandaapplication.model

enum class Status(val statusName: String) {
    RUNNING("running"),
    CANCELED("canceled"),
    FINISHED("finished"),
    NOT_STARTED("not_started"),
    POSTPONED("postponed");

}