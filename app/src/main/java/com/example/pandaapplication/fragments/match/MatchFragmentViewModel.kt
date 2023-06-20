package com.example.pandaapplication.fragments.match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pandaapplication.core.utils.EventState
import com.example.pandaapplication.model.MatchesItem
import com.example.pandaapplication.repository.PandaRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.roundToInt


class MatchFragmentViewModel(private val pandaRepository: PandaRepository) : ViewModel() {

    private var page = 0
    private var totalPage = 1

    private val _matchesList = MutableLiveData<EventState<List<MatchesItem>>>()
    val matches: LiveData<EventState<List<MatchesItem>>> = _matchesList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _navigateToDetail = MutableLiveData<Int>()
    val navigateToDetail: LiveData<Int> = _navigateToDetail


    fun loadMatches() {
        viewModelScope.launch {
            _matchesList.postValue(EventState.Loading)
            try {
                val response = pandaRepository.getMatches(getDate(), page + 1)
                if (response.isSuccessful) {
                    response.body()?.let {
                        processResponse(response)
                        _matchesList.postValue(EventState.Success(it))
                    }
                }

            } catch (ex: Exception) {
                _matchesList.postValue(EventState.Error(ex.message ?: "No message"))
            }
        }
    }

    private fun processResponse(response: Response<List<MatchesItem>>){
        page = (response.headers().get("X-Page") ?: "1").toInt()
        val total = response.headers().get("X-Total") ?: "1"
        val pageSize = response.headers().get("X-Per-Page") ?: "0"
        totalPage = (total.toDouble() / pageSize.toDouble()).roundToInt()

    }

    private fun getDate(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(time)
    }

    fun getPage() = page
    fun canLoadMatches(): Boolean = (totalPage > page)


    fun goToDetails(item: MatchesItem?) {
       if (item != null){
            _navigateToDetail.postValue(item.id)
       }
    }


}