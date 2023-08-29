package by.devnmisko.testbs.ui.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.devnmisko.domain.model.ImageDomainRequestModel
import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.usecase.images.PostImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CameraViewModel @Inject constructor(
    private val postImageUseCase : PostImageUseCase
) : ViewModel() {

    private val _loadingState = MutableStateFlow(false)
    internal val loadingState: StateFlow<Boolean> = _loadingState

    private val _postImageResponse = MutableStateFlow<Output<ImageDomainResponseModel>?>(null)
    internal val postImageResponse: StateFlow<Output<ImageDomainResponseModel>?> = _postImageResponse

    fun postImage(image: ImageDomainRequestModel) = viewModelScope.launch {
        postImageUseCase.invoke(image).onEach {
            _loadingState.value = it.status == Output.Status.LOADING
            _postImageResponse.value = it
        }.collect()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.e("View Model cleared")
    }
}