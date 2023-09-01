package by.devnmisko.data.source.remote.model

import by.devnmisko.domain.model.CommentDomainRequestModel

data class CommentApiRequestModel(
    val text: String
) {
    companion object {
        fun toDataModel(comment: CommentDomainRequestModel): CommentApiRequestModel {
            return CommentApiRequestModel(
                text = comment.text
            )
        }
    }
}

