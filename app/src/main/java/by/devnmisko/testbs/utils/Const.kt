package by.devnmisko.testbs.utils

object Const {
    const val INVALID_ID = -1
    object Pattern {
        const val USERNAME_PATTERN = "[a-z0-9_\\-.@]+"
        const val DATE_PATTERN = "dd.MM.yyyy"
        const val DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm"
    }

    object Range {
        val USERNAME_RANGE = 4..32
        val PASSWORD_RANGE = 4..500
        val COMMENT_RANGE = 1..500
    }

}