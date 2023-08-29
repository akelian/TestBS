package by.devnmisko.testbs.ui.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.devnmisko.domain.model.ImageDomainRequestModel
import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.usecase.images.GetImagesUseCase
import by.devnmisko.domain.usecase.images.PostImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotosViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
): ViewModel() {

    private val _loadingState = MutableStateFlow(false)
    internal val loadingState: StateFlow<Boolean> = _loadingState

    private val _imagesList = MutableStateFlow<Output<List<ImageDomainResponseModel>>?>(null)
    internal val imagesList: StateFlow<Output<List<ImageDomainResponseModel>>?> = _imagesList


    fun fetchImages(page: Int) = viewModelScope.launch {
        getImagesUseCase(page).onEach {
            _loadingState.value = it.status == Output.Status.LOADING
            _imagesList.value = it
        }.collect()
    }

}