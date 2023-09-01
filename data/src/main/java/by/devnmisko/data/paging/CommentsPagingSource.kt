package by.devnmisko.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import by.devnmisko.data.source.remote.datasource.CommentsRemoteDataSource
import by.devnmisko.data.source.remote.model.toDomainModel
import by.devnmisko.domain.model.CommentDomainResponseModel
import by.devnmisko.domain.model.Output
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class CommentsPagingSource @AssistedInject constructor(
    private val commentsRemoteDataSource: CommentsRemoteDataSource,
    @Assisted("imageId") private val imageId: Int
) : PagingSource<Int, CommentDomainResponseModel>() {
    override fun getRefreshKey(state: PagingState<Int, CommentDomainResponseModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommentDomainResponseModel> {

        val page: Int = params.key ?: 0
        val pageSize: Int = params.loadSize.coerceAtLeast(6)

        val response = commentsRemoteDataSource.getComments(imageId, page)
        return when (response.status) {
            Output.Status.SUCCESS -> {
                val comments = checkNotNull(response.data?.data?.map { it.toDomainModel() })
                val nextKey = if (comments.size < pageSize) null else page + 1
                val prevKey = if (page == 0) null else page - 1
                LoadResult.Page(comments, prevKey, nextKey)
            }

            Output.Status.ERROR -> {
                LoadResult.Error(Throwable(response.message))
            }

            else -> {
                LoadResult.Invalid()
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("imageId") imageId: Int) : CommentsPagingSource
    }
}