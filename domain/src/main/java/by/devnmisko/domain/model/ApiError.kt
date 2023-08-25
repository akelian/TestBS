package by.devnmisko.domain.model

data class ApiError(
    val status: Int = 0,
    val error: String? = null,
    var errorMessage: String? = null,
    val valid: List<Valid> = emptyList()
)

data class Valid(
    val field: String?=null,
    val message: String? = null
)

fun ApiError.buildErrorMessage() {
    if (status == 0){
        return
    }
    this.errorMessage = "Code: $status "
    if (valid.isEmpty()){
         this.errorMessage += "$error"
    } else {
        for (reason in valid){
            this.errorMessage += "\n" + reason.field + " " + reason.message
        }
    }
}