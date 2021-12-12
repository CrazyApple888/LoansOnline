package me.isachenko.loansonline.data.model

data class ErrorMessageStore(
    val e401: String,
    val e403: String,
    val e404: String
) {
    fun getMessageForCode(code: Int): String =
        when (code) {
            401 -> e401
            403 -> e403
            404 -> e404
            else -> ""
        }
}
