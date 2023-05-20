package com.ahrorovk.testmangofzco.presentation.AuthorizationScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.testmangofzco.core.Resource
import com.ahrorovk.testmangofzco.core.states.SendAuthCodeState
import com.ahrorovk.testmangofzco.data.local.DataStoreManager
import com.ahrorovk.testmangofzco.domain.model.requestBody.SendAuthCodeBody
import com.ahrorovk.testmangofzco.domain.model.requestResponse.SendAuthCodeResponse
import com.ahrorovk.testmangofzco.domain.use_cases.SendAuthCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val sendAuthCodeUseCase:SendAuthCodeUseCase,
    private val dataStoreManager: DataStoreManager
):ViewModel() {
    private val _state = MutableStateFlow(AuthorizationState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        AuthorizationState()
    )
    fun onEvent(event: AuthorizationEvent){
        when(event){
            is AuthorizationEvent.OnPhoneChange-> {
                _state.update {
                    it.copy(phone = event.phone)
                }
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreManager.updateRegisterPhone("+${_state.value.selectedNumberRegionOfPhone}${event.phone}")
                }
            }
            is AuthorizationEvent.OnSendAuthCodeChange->{
                _state.update {
                    it.copy(authCodeSendState = event.authCodeState)
                }
            }
            is AuthorizationEvent.OnSelectedImageRegionOfPhoneChange->{
                _state.update {
                    it.copy(selectedImageRegionOfPhone = event.image)
                }
            }
            is AuthorizationEvent.OnSelectedNumberRegionOfPhoneChange->{
                _state.update {
                    it.copy(selectedNumberRegionOfPhone = event.number)
                }
            }
            is AuthorizationEvent.SendAuthCode->{
                sendAuthCode()
                Log.e("SENDD", _state.value.phone)
            }
            else ->{}
        }
    }
    private fun sendAuthCode() {
        sendAuthCodeUseCase.invoke(SendAuthCodeBody("+${_state.value.selectedNumberRegionOfPhone}${_state.value.phone}")).onEach { result: Resource<SendAuthCodeResponse> ->2
            when (result) {
                is Resource.Success -> {
                    val response: SendAuthCodeResponse? = result.data
                    onEvent(AuthorizationEvent.OnSendAuthCodeChange(SendAuthCodeState(response = response)))
                    Log.e("TAG", "SendAuthCodeResponse->\n ${_state.value.authCodeSendState}")
                }
                is Resource.Error -> {
                    Log.e("TAG", "SendAuthCodeResponseError->\n ${result.message}")
                    onEvent(
                        AuthorizationEvent.OnSendAuthCodeChange(
                            SendAuthCodeState(
                                error = "${result.message}"
                            )
                        )
                    )
                }
                is Resource.Loading -> {
                    onEvent(AuthorizationEvent.OnSendAuthCodeChange(SendAuthCodeState(isLoading = true)))
                }
            }
        }.launchIn(viewModelScope)
    }
}