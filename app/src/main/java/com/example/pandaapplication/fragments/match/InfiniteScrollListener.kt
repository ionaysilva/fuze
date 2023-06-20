package com.example.pandaapplication.fragments.match

import androidx.recyclerview.widget.RecyclerView

abstract class InfiniteScrollListener : RecyclerView.OnScrollListener() {
    abstract fun onLoadMore()


}