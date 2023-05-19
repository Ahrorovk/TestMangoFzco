package com.ahrorovk.testmangofzco.presentation.AuthCodeCheckScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.testmangofzco.core.Resource
import com.ahrorovk.testmangofzco.core.states.CheckAuthCodeState
import com.ahrorovk.testmangofzco.data.local.DataStoreManager
import com.ahrorovk.testmangofzco.domain.model.requestBody.CheckAuthCodeBody
import com.ahrorovk.testmangofzco.domain.use_cases.CheckAuthCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class AuthCodeCheckViewModel @Inject constructor(
    private val checkAuthCodeUseCase: CheckAuthCodeUseCase,
    private val dataStoreManager: DataStoreManager
) :ViewModel() {
    private val _state = MutableStateFlow(AuthCodeCheckState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        AuthCodeCheckState()
    )
    init {
        dataStoreManager.getRegisterPhone.onEach { value ->
            _state.update {
                it.copy( phone = value
                )
            }
            Log.e("Phone", _state.value.phone)
        }.launchIn(viewModelScope)
        dataStoreManager.getAuthCode.onEach { value ->
            _state.update {
                it.copy( code = value
                )
            }
        }.launchIn(viewModelScope)
    }
    fun onEvent(event: AuthCodeCheckEvent){
        when(event){
            is AuthCodeCheckEvent.OnCodeChange->{
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreManager.updateAuthCode(event.code)
                }
            }
            is AuthCodeCheckEvent.OnStateCheckAuthCodeChange->{
                _state.update {
                    it.copy(checkAuthCodeState = event.state)
                }
            }
            is AuthCodeCheckEvent.OnAccessTokenChange->{
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreManager.updateAccessToken(event.token)
                }
            }
            is AuthCodeCheckEvent.OnRefreshTokenChange->{
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreManager.updateRefreshToken(event.token)
                }
            }
            is AuthCodeCheckEvent.OnAccessTokenLifeChange->{
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreManager.updateAccessTokenLife(event.lifeData)
                }
            }
            is AuthCodeCheckEvent.CheckAuthCode->{
                checkAuthCode()
            }
            else ->{}
        }
    }
    private fun checkAuthCode(){
        checkAuthCodeUseCase.invoke(CheckAuthCodeBody(_state.value.phone,_state.value.code)).onEach { result->
            when(result){
                is Resource.Success->{
                    val response = result.data
                    onEvent(AuthCodeCheckEvent.OnStateCheckAuthCodeChange(CheckAuthCodeState(response = response)))
                    response?.let {
                        it.access_token?.let { access ->
                            onEvent(AuthCodeCheckEvent.OnAccessTokenChange(access))
                        }
                        it.refresh_token?.let { refresh->
                            onEvent(AuthCodeCheckEvent.OnRefreshTokenChange(refresh))
                            onEvent(AuthCodeCheckEvent.OnAccessTokenLifeChange(System.currentTimeMillis()))
                        }
                    }
                    Log.e("TAG","CheckAuthCodeResponse->\n ${_state.value.checkAuthCodeState}")
                }
                is Resource.Error->{
                    onEvent(AuthCodeCheckEvent.OnStateCheckAuthCodeChange(CheckAuthCodeState(error = "${result.message}")))
                    Log.e("TAG","CheckAuthCodeError->\n ${result.message}")
                }
                is Resource.Loading->{
                    onEvent(AuthCodeCheckEvent.OnStateCheckAuthCodeChange(CheckAuthCodeState(true)))
                }
            }
        }.launchIn(viewModelScope)
    }
}