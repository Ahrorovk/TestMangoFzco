package com.ahrorovk.testmangofzco.presentation.RegistrationScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.testmangofzco.core.Resource
import com.ahrorovk.testmangofzco.core.states.RefreshTokenState
import com.ahrorovk.testmangofzco.core.states.UserRegistrationState
import com.ahrorovk.testmangofzco.data.local.DataStoreManager
import com.ahrorovk.testmangofzco.domain.model.requestBody.RefreshTokenBody
import com.ahrorovk.testmangofzco.domain.model.requestBody.UserBody
import com.ahrorovk.testmangofzco.domain.model.requestResponse.RefreshTokenResponse
import com.ahrorovk.testmangofzco.domain.model.requestResponse.UserRegistrationResponse
import com.ahrorovk.testmangofzco.domain.use_cases.RefreshTokenUseCase
import com.ahrorovk.testmangofzco.domain.use_cases.UserRegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject
@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRegistrationUseCase: UserRegistrationUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        RegistrationState()
    )

    init {
        dataStoreManager.getAccessToken.onEach { value ->
            _state.update {
                it.copy(
                    accessToken = value
                )
            }
        }.launchIn(viewModelScope)
        dataStoreManager.getAccessTokenLife.onEach { value ->
            _state.update {
                it.copy(
                    accessTokenLife = value
                )
            }
        }.launchIn(viewModelScope)
        dataStoreManager.getRefreshToken.onEach { value ->
            _state.update {
                it.copy(
                    refresh_token = value
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnUserRegistrationChange -> {
                _state.update {
                    it.copy(userRegState = event.user)
                }
            }
            is RegistrationEvent.OnUserNameChange -> {
                _state.update {
                    it.copy(userName = event.userName)
                }
            }
            is RegistrationEvent.OnNameChange -> {
                _state.update {
                    it.copy(name = event.name)
                }
            }
            is RegistrationEvent.OnPhoneChange -> {
                _state.update {
                    it.copy(phone = event.phone)
                }
            }
            is RegistrationEvent.RefreshToken -> {
                refreshToken()
            }
            is RegistrationEvent.OnSelectedNumberRegionOfPhoneChange -> {
                _state.update {
                    it.copy(selectedNumberRegionOfPhone = event.selectedNumberRegionOfPhone)
                }
            }
            is RegistrationEvent.OnSelectedImageRegionOfPhoneChange -> {
                _state.update {
                    it.copy(selectedImageRegionOfPhone = event.selectedImageRegionOfPhone)
                }
            }
            is RegistrationEvent.SendUserRegistration -> {
                registerUser()
            }
            is RegistrationEvent.OnErrorChange -> {
                _state.update {
                    it.copy(error = event.error)
                }
            }
            is RegistrationEvent.OnRefreshTokenChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreManager.updateRefreshToken(event.token)
                }
            }
            is RegistrationEvent.OnAccessTokenLifeChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreManager.updateAccessTokenLife(event.lifeData)
                }
            }
            is RegistrationEvent.OnAccessTokenChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreManager.updateAccessToken(event.token)
                }
            }
            is RegistrationEvent.OnRefreshTokenResponseChange -> {
                _state.update {
                    it.copy(refreshTokenState = event.token)
                }
            }
            else -> {}
        }
    }

    private fun registerUser() {
        userRegistrationUseCase.invoke(
            UserBody(
                "+${_state.value.selectedNumberRegionOfPhone}${_state.value.phone}",
                _state.value.name,
                _state.value.userName
            )
        )
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val response: UserRegistrationResponse? = result.data
                        onEvent(
                            RegistrationEvent.OnUserRegistrationChange(
                                UserRegistrationState(
                                    response = response
                                )
                            )
                        )
                        onEvent(RegistrationEvent.OnAccessTokenLifeChange(System.currentTimeMillis()))
                        response?.let { resp->
                            onEvent(RegistrationEvent.OnAccessTokenChange(resp.access_token))
                            onEvent(RegistrationEvent.OnRefreshTokenChange(resp.refresh_token) )
                        }
                        Log.e(
                            "TAG",
                            "RefreshTokenResponse->\n $response"
                        )
                    }
                    is Resource.Error -> {
                        Log.e("TAG", "RefreshTokenResponseError->\n ${result.message}")
                            onEvent(RegistrationEvent.OnErrorChange("${result.message}"))
                            onEvent(
                                RegistrationEvent.OnUserRegistrationChange(
                                    UserRegistrationState(
                                        error = "${result.message}"
                                    )
                                )
                            )
                        }
                    is Resource.Loading -> {
                        onEvent(
                            RegistrationEvent.OnUserRegistrationChange(
                                UserRegistrationState(
                                    isLoading = true
                                )
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
    private fun refreshToken() {
        refreshTokenUseCase.invoke(RefreshTokenBody(_state.value.refresh_token))
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val response: RefreshTokenResponse? = result.data
                        onEvent(
                            RegistrationEvent.OnRefreshTokenResponseChange(
                                RefreshTokenState(
                                    response = response
                                )
                            )
                        )
                        response?.let {resp->
                            onEvent(RegistrationEvent.OnAccessTokenLifeChange(System.currentTimeMillis()))
                            onEvent(RegistrationEvent.OnRefreshTokenChange(resp.refresh_token))
                            onEvent(RegistrationEvent.OnAccessTokenChange(resp.access_token))
                        }
                        Log.e(
                            "TAG",
                            "UserRegistrationResponse->\n $response"
                        )
                    }
                    is Resource.Error -> {
                        onEvent(
                            RegistrationEvent.OnRefreshTokenResponseChange(
                                RefreshTokenState(
                                    error = "${result.message}"
                                )
                            )
                        )
                        Log.e("TAG", "UserRegistrationResponseError->\n ${result.message}")
                    }
                    is Resource.Loading -> {
                        onEvent(
                            RegistrationEvent.OnRefreshTokenResponseChange(
                                RefreshTokenState(
                                    isLoading = true
                                )
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}