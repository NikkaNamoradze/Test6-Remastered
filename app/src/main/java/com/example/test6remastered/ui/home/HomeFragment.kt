package com.example.test6remastered.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.test6remastered.R
import com.example.test6remastered.constants.DataStorePreferenceKeys
import com.example.test6remastered.databinding.HomeFragmentBinding
import com.example.test6remastered.ui.base.BaseFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val args: HomeFragmentArgs by navArgs()

    private val viewModel: HomeViewModel by viewModels()

    override fun start() {
        setUpEmail()
        observer()
        listeners()
    }

    private fun setUpEmail(){
        binding.tvSession.text = args.email
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPreferences().collect {
                    if (it.contains(booleanPreferencesKey(DataStorePreferenceKeys.SESSION.toString()))) {
                        binding.tvSession.text =
                            it[stringPreferencesKey(DataStorePreferenceKeys.TOKEN)]
                    }
                }
            }
        }
    }

    private fun listeners(){
        binding.btnLogout.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.clear()
            }.invokeOnCompletion {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }
    }

}