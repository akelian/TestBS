package by.devnmisko.testbs.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.devnmisko.domain.usecase.images.GetImagesFromDBUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class MapViewModel @Inject constructor(
    getImagesFromDBUseCase: GetImagesFromDBUseCase
): ViewModel() {
    internal val allImages = getImagesFromDBUseCase().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}