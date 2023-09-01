package by.devnmisko.data.repository.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

abstract class BaseRepository : CoroutineScope by MainScope()