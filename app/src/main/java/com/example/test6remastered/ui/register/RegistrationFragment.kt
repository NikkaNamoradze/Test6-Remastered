package com.example.test6remastered.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.test6remastered.R
import com.example.test6remastered.constants.ResultFragmentConstants
import com.example.test6remastered.databinding.RegistrationFragmentBinding
import com.example.test6remastered.model.UserInfoSend
import com.example.test6remastered.ui.base.BaseFragment
import com.example.test6remastered.utils.ResponseHandler
import kotlinx.coroutines.launch

class RegistrationFragment :
    BaseFragment<RegistrationFragmentBinding>(RegistrationFragmentBinding::inflate) {

    private val viewModel: RegistrationViewModel by viewModels()

    override fun start() {
        listeners()
    }

    private fun setUpResultFragment() {
        setFragmentResult(
            requestKey = ResultFragmentConstants.AUTH_KEY,
            result = bundleOf(
                ResultFragmentConstants.EMAIL to binding.etEmail.text.toString(),
                ResultFragmentConstants.PASSWORD to binding.etPassword.text.toString()
            )
        )
    }

    private fun listeners() {
        binding.btnRegistration.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getUserRegistrations(
                    UserInfoSend(
                        binding.etEmail.text.toString(),
                        binding.etConfirmPassword.text.toString()
                    )
                ).collect {
                    when (it) {
                        is ResponseHandler.Success<*> -> {
                            setUpResultFragment()
                            findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment())
                        }
                        is ResponseHandler.Failure -> {
                            Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                        }
                        is ResponseHandler.Loader -> {
                            binding.progressBar.isVisible = it.isLoading
                        }
                    }
                }
            }
        }
    }



}