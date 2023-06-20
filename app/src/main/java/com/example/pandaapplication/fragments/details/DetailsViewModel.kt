package com.example.pandaapplication.fragments.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pandaapplication.core.utils.EventState
import com.example.pandaapplication.model.MatchDetailResponse
import com.example.pandaapplication.model.OpponentsResponse
import com.example.pandaapplication.model.ResponseDetail
import com.example.pandaapplication.repository.PandaRepository
import kotlinx.coroutines.launch

class DetailsViewModel(private var pandaRepository: PandaRepository) : ViewModel() {


    private val _matchDetail = MutableLiveData<EventState<ResponseDetail>>()
    val matchDetail: LiveData<EventState<ResponseDetail>> = _matchDetail

    fun loadMatch(matchId: Int) {
        viewModelScope.launch {
            _matchDetail.postValue(EventState.Loading)
            try {

                pandaRepository.getMatchDetail(matchId)
                val response = pandaRepository.getMatchDetail(matchId)
                val responseOpponents = pandaRepository.getMatchOpponents(matchId)

                if (response.isSuccessful && responseOpponents.isSuccessful) {
                    response.body()?.let { match ->
                        responseOpponents.body()?.let { opponents ->
                            val processResponses = processResponses(match, opponents)
                            _matchDetail.postValue(EventState.Success(processResponses))
                        }
                    }
                }

            } catch (ex: Exception) {
                _matchDetail.postValue(EventState.Error(ex.message ?: "No message"))
            }
        }
    }

    private fun processResponses(match: MatchDetailResponse, opponents: OpponentsResponse) =
        ResponseDetail(match.league.name?:"", match.serie.name?:"", match.begin_at, opponents.opponents)

}