package by.devnmisko.testbs.ui.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.usecase.images.GetImagesUseCase
import by.devnmisko.domain.usecase.images.RemoveImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotosViewModel @Inject constructor(
    getImagesUseCase: GetImagesUseCase,
    private val removeImageUseCase: RemoveImageUseCase
): ViewModel() {

    private val _removeState = MutableStateFlow<Output<Void>?>(null)
    internal val removeState: StateFlow<Output<Void>?> = _removeState

    val pagingImages =
        getImagesUseCase().stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty()).cachedIn(viewModelScope)

    fun removeImage(id: Int) = viewModelScope.launch {
        removeImageUseCase(id).onEach { output ->
            _removeState.value = output
        }.onCompletion {
            _removeState.value = null
        }.collect()
    }

}