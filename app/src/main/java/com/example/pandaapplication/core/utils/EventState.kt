package com.example.pandaapplication.core.utils

sealed class EventState<out R> {
    data class Success<out T>(val data: T) : EventState<T>()
    data class Error(val exception: String) : EventState<Nothing>()
    object Loading : EventState<Nothing>()
}
