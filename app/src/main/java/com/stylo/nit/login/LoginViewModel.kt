package com.stylo.nit.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stylo.nit.RetrofitApi.CredentialRepository
import com.stylo.nit.RetrofitApi.LoginRequest
import com.stylo.nit.RetrofitApi.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: CredentialRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun login(username: String, password: String) {
        // Create input JSON
        val inputJson = JSONObject().apply {
            put("username", username)
            put("password", password)
        }

        // Log input for debugging
        println("Input JSON: $inputJson")

        // Show loading state
        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            val credential = LoginRequest(username, password)

            // Use either the real API or simulation based on your needs
            // val result = repository.validateCredentials(credential)
            // Real API
            val result = repository.validateCredentials(credential)
        //    val result = repository.simulateApiCall(credential)  // Simulated API

            result.fold(
                onSuccess = { response ->


                    // Create output JSON
                    val outputJson = JSONObject().apply {
                        put("keypass", response.keypass)
                    }

                    // Log output for debugging
                    println("Output JSON: $outputJson")
                    Log.e("Output JSON","Output JSON: $outputJson")

                    _loginState.value = LoginState.Success(response, outputJson.toString())
                },
                onFailure = { error ->
                    _loginState.value = LoginState.Error(error.message ?: "Unknown error occurred")
                }
            )
        }
    }

    sealed class LoginState {
        object Loading : LoginState()
        data class Success(val response: LoginResponse, val jsonOutput: String) : LoginState()
        data class Error(val message: String) : LoginState()
    }
}