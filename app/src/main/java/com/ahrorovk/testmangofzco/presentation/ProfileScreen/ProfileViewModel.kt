package com.ahrorovk.testmangofzco.presentation.ProfileScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.testmangofzco.core.Resource
import com.ahrorovk.testmangofzco.core.checkUpdatesInfo
import com.ahrorovk.testmangofzco.core.onInfoStateChange
import com.ahrorovk.testmangofzco.core.states.CurrentUserState
import com.ahrorovk.testmangofzco.core.states.RefreshTokenState
import com.ahrorovk.testmangofzco.core.states.UpdateUserInfoState
import com.ahrorovk.testmangofzco.data.local.DataStoreManager
import com.ahrorovk.testmangofzco.domain.model.requestBody.Avatar
import com.ahrorovk.testmangofzco.domain.model.requestBody.RefreshTokenBody
import com.ahrorovk.testmangofzco.domain.model.requestBody.UpdateUserInfoBody
import com.ahrorovk.testmangofzco.domain.model.requestResponse.RefreshTokenResponse
import com.ahrorovk.testmangofzco.domain.model.requestResponse.UpdateUserInfoResponse
import com.ahrorovk.testmangofzco.domain.model.requestResponse.UserInfoResponse
import com.ahrorovk.testmangofzco.domain.use_cases.GetCurrentUserUseCase
import com.ahrorovk.testmangofzco.domain.use_cases.RefreshTokenUseCase
import com.ahrorovk.testmangofzco.domain.use_cases.UpdateUserInfoUseCase
import com.ahrorovk.testmangofzco.presentation.RegistrationScreen.RegistrationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val dataStoreManager: DataStoreManager,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase
):ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        ProfileState()
    )

    init {
        dataStoreManager.getAccessToken.onEach { value ->
            _state.update {
                it.copy(accessToken = value)
            }
        }.launchIn(viewModelScope)

        dataStoreManager.getAccessTokenLife.onEach { value ->
            _state.update {
                it.copy(accessTokenLife = value)
            }
        }.launchIn(viewModelScope)


        dataStoreManager.getRefreshToken.onEach { value ->
            _state.update {
                it.copy(refreshToken = value)
            }
        }.launchIn(viewModelScope)

        dataStoreManager.getName.onEach { value ->
            _state.update {
                it.copy(name = value)
            }
            Log.e("NAME", value)
        }.launchIn(viewModelScope)
        dataStoreManager.getUsername.onEach { value ->
            _state.update {
                it.copy(username = value)
            }
        }.launchIn(viewModelScope)
        dataStoreManager.getCity.onEach { value ->
            _state.update {
                it.copy(city = value)
            }
        }.launchIn(viewModelScope)
        dataStoreManager.getBirthday.onEach { value ->
            _state.update {
                it.copy(birthday = value)
            }
        }.launchIn(viewModelScope)
        dataStoreManager.getInstagram.onEach { value ->
            _state.update {
                it.copy(instagram = value)
            }
        }.launchIn(viewModelScope)
        dataStoreManager.getStatus.onEach { value ->
            _state.update {
                it.copy(status = value)
            }
        }.launchIn(viewModelScope)
        dataStoreManager.getVK.onEach { value ->
            _state.update {
                it.copy(vk = value)
            }
        }.launchIn(viewModelScope)
        dataStoreManager.getRegisterPhone.onEach { value ->
            _state.update {
                it.copy(phone = value)
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.GetCurrentUser -> {
                getCurrentUser()
            }
            is ProfileEvent.OnItemChange -> {
                _state.update {
                    it.copy(itemState = event.change)
                }
            }
            is ProfileEvent.GoToChange -> {
                _state.update {
                    it.copy(typeOfItem = event.screen)
                }
            }
            is ProfileEvent.OnInfoStatesChange -> {
                onInfoStateChange(_state.value,event,dataStoreManager,viewModelScope)
            }
            is ProfileEvent.OnAccessTokenChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreManager.updateAccessToken(event.token)
                }
            }
            is ProfileEvent.OnAccessTokenLifeChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreManager.updateAccessTokenLife(event.lifeData)
                }
            }
            is ProfileEvent.OnUpdateUserInfoChange -> {
                _state.update {
                    it.copy(updateUserInfoState = event.state)
                }
            }
            is ProfileEvent.OnCurrentUserStateChange -> {
                _state.update {
                    it.copy(currentUserState = event.state)
                }
            }
            is ProfileEvent.OnRefreshTokenChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreManager.updateRefreshToken(event.token)
                }
            }
            is ProfileEvent.OnLogOutFromAccount -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreManager.updateAccessToken("")
                    dataStoreManager.updateRefreshToken("")
                    dataStoreManager.updateAccessTokenLife(0)
                }
            }
            is ProfileEvent.ChangeItem -> {
                updateUserInfo()
            }
            is ProfileEvent.OnRefreshStateChange -> {
                _state.update {
                    it.copy(refreshState = event.state)
                }
            }
            is ProfileEvent.OnRefreshTokenResponseChange -> {
                _state.update {
                    it.copy(refreshTokenState = event.state)
                }
            }
            is ProfileEvent.RefreshToken -> {
                refreshToken()
            }
            else -> {}
        }
    }

    private fun getCurrentUser() {
        getCurrentUserUseCase.invoke(_state.value.accessToken)
            .onEach { result: Resource<UserInfoResponse> ->
                when (result) {
                    is Resource.Success -> {
                        val response: UserInfoResponse? = result.data
                        onEvent(ProfileEvent.OnCurrentUserStateChange(CurrentUserState(response = response)))
                        response?.let {
                            it.profile_data?.let { itm ->
                                onEvent(
                                    ProfileEvent.OnInfoStatesChange(
                                        itm.avatar,
                                        itm.birthday,
                                        itm.city,
                                        itm.instagram,
                                        itm.name,
                                        itm.status,
                                        itm.username,
                                        itm.vk,
                                    )
                                )
                            }
                        }
                        Log.e("TAG", "GetCurrentUserResponse->\n ${_state.value.currentUserState}")
                    }
                    is Resource.Error -> {
                        Log.e("TAG", "GetCurrentUserResponseError->\n ${result.message}")
                        onEvent(
                            ProfileEvent.OnCurrentUserStateChange(
                                CurrentUserState(
                                    error = "${result.message}"
                                )
                            )
                        )
                    }
                    is Resource.Loading -> {
                        onEvent(ProfileEvent.OnCurrentUserStateChange(CurrentUserState(isLoading = true)))
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun updateUserInfo() {
        updateUserInfoUseCase.invoke(
            _state.value.accessToken, UpdateUserInfoBody(
                avatar = Avatar(
                    "",
                    if (_state.value.typeOfItem == "avatar") _state.value.itemState else _state.value.avatar
                ),
                birthday = checkUpdatesInfo(
                    _state.value.typeOfItem,
                    "birthday",
                    _state.value.itemState,
                    _state.value.birthday
                ),
                city = checkUpdatesInfo(
                    _state.value.typeOfItem,
                    "city",
                    _state.value.itemState,
                    _state.value.city
                ),
                instagram = checkUpdatesInfo(
                    _state.value.typeOfItem,
                    "instagram",
                    _state.value.itemState,
                    _state.value.instagram
                ),
                name = checkUpdatesInfo(
                    _state.value.typeOfItem,
                    "name",
                    _state.value.itemState,
                    _state.value.name
                ),
                status = checkUpdatesInfo(
                    _state.value.typeOfItem,
                    "status",
                    _state.value.itemState,
                    _state.value.status
                ),
                username = _state.value.username,
                vk = checkUpdatesInfo(
                    _state.value.typeOfItem,
                    "vk",
                    _state.value.itemState,
                    _state.value.vk
                ),
            )
        )
            .onEach { result: Resource<UpdateUserInfoResponse> ->
                when (result) {
                    is Resource.Success -> {
                        val response: UpdateUserInfoResponse? = result.data
                        onEvent(ProfileEvent.OnUpdateUserInfoChange(UpdateUserInfoState(response = response)))
                        Log.e("TAG", "GetCurrentUserResponse->\n ${_state.value.currentUserState}")
                    }
                    is Resource.Error -> {
                        Log.e("TAG", "GetCurrentUserResponseError->\n ${result.message}")
                        onEvent(
                            ProfileEvent.OnUpdateUserInfoChange(
                                UpdateUserInfoState(
                                    error = "${result.message}"
                                )
                            )
                        )
                    }
                    is Resource.Loading -> {
                        onEvent(ProfileEvent.OnUpdateUserInfoChange(UpdateUserInfoState(isLoading = true)))
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun refreshToken() {
        refreshTokenUseCase.invoke(RefreshTokenBody(_state.value.refreshToken))
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val response: RefreshTokenResponse? = result.data
                        onEvent(
                            ProfileEvent.OnRefreshTokenResponseChange(
                                RefreshTokenState(
                                    response = response
                                )
                            )
                        )
                        response?.let { resp ->
                            onEvent(ProfileEvent.OnAccessTokenLifeChange(System.currentTimeMillis()))
                            onEvent(ProfileEvent.OnRefreshTokenChange(resp.refresh_token))
                            onEvent(ProfileEvent.OnAccessTokenChange(resp.access_token))
                        }
                        Log.e(
                            "TAG",
                            "UserRegistrationResponse->\n $response"
                        )
                    }
                    is Resource.Error -> {
                        onEvent(
                            ProfileEvent.OnRefreshTokenResponseChange(
                                RefreshTokenState(
                                    error = "${result.message}"
                                )
                            )
                        )
                        Log.e("TAG", "UserRegistrationResponseError->\n ${result.message}")
                    }
                    is Resource.Loading -> {
                        onEvent(
                            ProfileEvent.OnRefreshTokenResponseChange(
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