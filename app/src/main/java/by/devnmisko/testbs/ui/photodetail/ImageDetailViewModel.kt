package by.devnmisko.testbs.ui.photodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import by.devnmisko.data.paging.CommentsPagingSource
import by.devnmisko.domain.model.CommentDomainRequestModel
import by.devnmisko.domain.model.CommentDomainResponseModel
import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.usecase.comments.GetCommentsUseCase
import by.devnmisko.domain.usecase.comments.PostCommentUseCase
import by.devnmisko.domain.usecase.comments.RemoveCommentUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ImageDetailViewModel @AssistedInject constructor(
    getCommentsUseCase: GetCommentsUseCase,
    private val postCommentUseCase: PostCommentUseCase,
    private val removeCommentUseCase: RemoveCommentUseCase,
    @Assisted("imageId") private val imageId: Int
) : ViewModel() {

    val allComments = getCommentsUseCase(imageId).stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty()).cachedIn(viewModelScope)

    private val _postCommentState = MutableStateFlow<Output<CommentDomainResponseModel>?>(null)
    val postCommentState : StateFlow<Output<CommentDomainResponseModel>?> = _postCommentState

    private val _removeState = MutableStateFlow<Output<Void>?>(null)
    val removeState : StateFlow<Output<Void>?> = _removeState

    fun postComment(commentBody: String) = viewModelScope.launch {
        postCommentUseCase(imageId, CommentDomainRequestModel(commentBody)).onEach {
            _postCommentState.value = it
        }.onCompletion {
            _postCommentState.value = null
        }.collect()
    }

    fun removeComment(commentId: Int) = viewModelScope.launch {
        removeCommentUseCase(imageId, commentId).onEach { output ->
            _removeState.value = output
        }.onCompletion {
            _removeState.value = null
        }.collect()
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("imageId") imageId: Int) : ImageDetailViewModel
    }

}