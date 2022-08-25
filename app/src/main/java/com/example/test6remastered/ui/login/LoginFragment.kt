package com.example.test6remastered.ui.login

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.test6remastered.constants.DataStorePreferenceKeys
import com.example.test6remastered.constants.ResultFragmentConstants
import com.example.test6remastered.databinding.LoginFragmentBinding
import com.example.test6remastered.model.LoginResponse
import com.example.test6remastered.model.UserInfoSend
import com.example.test6remastered.ui.base.BaseFragment
import com.example.test6remastered.utils.ResponseHandler
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<LoginFragmentBinding>(LoginFragmentBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun start() {
        sessionCheck()
        setUpResultFragment()
        listeners()
    }

    private fun setUpResultFragment() {
        setFragmentResultListener(ResultFragmentConstants.AUTH_KEY) { _, bundle ->
            binding.etEmail.setText(bundle.getString(ResultFragmentConstants.EMAIL, "email"))
            binding.etPassword.setText(
                bundle.getString(
                    ResultFragmentConstants.PASSWORD,
                    "password"
                )
            )
        }
    }

    private fun listeners() {
        binding.btnRegistration.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
        }
        binding.btnLogIn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getUserLogin(
                    UserInfoSend(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                ).collect {
                    when (it) {
                        is ResponseHandler.Success<*> -> {
                            if (binding.cbRememberMe.isChecked) {
                                viewModel.save(
                                    DataStorePreferenceKeys.SESSION.toString(),
                                    true, (it.result as LoginResponse).token ?: "empty token"
                                )
                            } else {
                                findNavController().navigate(
                                    LoginFragmentDirections.actionLoginFragmentToHomeFragment(
                                        binding.etEmail.text.toString()
                                    )
                                )
                            }
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

    private fun sessionCheck() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPreferences().collect {
                    if (it.contains(booleanPreferencesKey(DataStorePreferenceKeys.SESSION.toString()))) {
                        findNavController().navigate(
                            LoginFragmentDirections.actionLoginFragmentToHomeFragment(
                                binding.etEmail.text.toString()
                            )
                        )
                    }
                }
            }
        }
    }

}