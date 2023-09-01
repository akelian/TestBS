package by.devnmisko.testbs.ui.photos

import by.devnmisko.domain.model.ImageDomainResponseModel

interface OnImageClickListener {
    fun openImageDetail(image: ImageDomainResponseModel?)
}