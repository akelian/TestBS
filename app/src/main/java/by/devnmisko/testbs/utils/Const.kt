package by.devnmisko.testbs.utils

object Const {
    object Pattern {
        const val USERNAME_PATTERN = "[a-z0-9_\\-.@]+"
        const val DATE_TIME_PATTERN = "dd.MM.yyyy"
    }

    object Range {
        val USERNAME_RANGE = 4..32
        val PASSWORD_RANGE = 4..500
    }
}