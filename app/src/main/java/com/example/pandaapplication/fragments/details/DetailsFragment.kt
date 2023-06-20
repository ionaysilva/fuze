package com.example.pandaapplication.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pandaapplication.R
import com.example.pandaapplication.core.glide.GlideApp
import com.example.pandaapplication.core.utils.Constants
import com.example.pandaapplication.core.utils.EventState
import com.example.pandaapplication.databinding.FragmentDetailsBinding
import com.example.pandaapplication.extensions.hideView
import com.example.pandaapplication.extensions.loadTextDate
import com.example.pandaapplication.extensions.showView
import com.example.pandaapplication.model.ResponseDetail
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {


    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModel()
    private lateinit var adapterA: PlayerAdapter
    private lateinit var adapterB: PlayerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()
        initAdapters()
        initListeners()
        initObservers()
        loadData()
    }

    private fun initListeners() {
        binding.back.setOnClickListener { goToMatchFragment() }
    }

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    goToMatchFragment()
                }
            })
    }

    private fun goToMatchFragment(){
        this@DetailsFragment.findNavController().navigate(R.id.matchFragment)
    }

    private fun initAdapters() {
        adapterA = PlayerAdapter(Constants.TEAM_A)
        adapterB = PlayerAdapter(Constants.TEAM_B)
        binding.recyclerviewA.adapter = adapterA
        binding.recyclerviewB.adapter = adapterB
    }

    private fun loadData() {
        val idValue = arguments?.getInt("id") ?: 0
        viewModel.loadMatch(idValue)

    }

    private fun initObservers() {
        viewModel.matchDetail.observe(viewLifecycleOwner) {
            when (it) {
                is EventState.Loading -> {
                    binding.progress.showView()
                }

                is EventState.Error -> {
                    binding.progress.hideView()
                }

                is EventState.Success -> {
                    populateFragment(it.data)
                    binding.progress.hideView()
                    binding.content.showView()

                }
            }
        }
    }



    private fun populateFragment(data: ResponseDetail) {
        binding.teams.time.text = data.begin_at.loadTextDate()
        binding.title.text = data.leagueName + " + " + data.serieName

        binding.teams.firstTitle.text = data.opponents[0].name
        GlideApp.with(binding.root.context)
            .load(data.opponents[0].image_url)
            .error(R.drawable.team_logo)
            .into(binding.teams.firstImage)
        adapterA.setList(data.opponents[0].players)


        binding.teams.secondTitle.text = data.opponents[1].name
        GlideApp.with(binding.root.context)
            .load(data.opponents[1].image_url)
            .error(R.drawable.team_logo)
            .into(binding.teams.secondImage)
        adapterB.setList(data.opponents[1].players)

    }


}