package com.example.pandaapplication.fragments.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pandaapplication.R
import com.example.pandaapplication.core.utils.EventState
import com.example.pandaapplication.databinding.FragmentBlankBinding
import com.example.pandaapplication.extensions.hideView
import com.example.pandaapplication.extensions.showView
import com.example.pandaapplication.model.MatchesItem
import org.koin.androidx.viewmodel.ext.android.viewModel


class MatchFragment : Fragment() {

    private lateinit var binding: FragmentBlankBinding
    private lateinit var adapter: MatchAdapter
    private val viewModel by viewModel<MatchFragmentViewModel>()
    var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blank, container, false)
        initAdapter()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.adapter = adapter
        onBackPressed()
        initObservers()
        loadData()
    }

    private fun loadData() {
        if(viewModel.canLoadMatches()){
            isLoading = true
            viewModel.loadMatches()
        }
    }

    private fun initObservers() {
        viewModel.matches.observe(viewLifecycleOwner) {
            when (it) {
                is EventState.Loading -> {
                    if(viewModel.getPage() >= 1){
                        adapter.addLoadingFooter()
                    }
                }

                is EventState.Error -> {
                    adapter.removeLoadingFooter()
                    isLoading = false
                    binding.progress.hideView()
                }

                is EventState.Success -> {
                    binding.progress.hideView()
                    binding.recyclerview.showView()
                    isLoading = false
                    if(viewModel.getPage() == 1){
                        adapter.setMatchList(it.data)
                    } else {
                        adapter.removeLoadingFooter()
                        adapter.addAll(it.data)
                    }

                }
            }
        }


        viewModel.navigateToDetail.observe(viewLifecycleOwner) {
            this.findNavController().navigate(
                R.id.details_fragment, bundleOf(
                    "id" to it
                )
            )
        }

        createScrollListener()
    }

    private fun initAdapter() {
        adapter = MatchAdapter { gotoDetails(it) }
    }

    private fun gotoDetails(item: MatchesItem?) {
        viewModel.goToDetails(item)
    }


    private fun createScrollListener() {
      binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
          override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
              super.onScrollStateChanged(recyclerView, newState)
              val layoutManager = recyclerView.layoutManager as LinearLayoutManager
              val adapter = recyclerView.adapter
              if (!isLoading && adapter != null && layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                loadData()
              }
          }

      })
    }

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                  activity?.finish()
                }
            })
    }

}