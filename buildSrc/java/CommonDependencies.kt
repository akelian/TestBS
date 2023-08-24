object CommonDependencies {

    object AndroidX {
        const val core_ktx_version = "1.8.0"
        const val appcompat_version = "1.6.1"
        const val annotation_version = "1.6.0"

        const val core = "androidx.core:core-ktx:$core_ktx_version"
        const val appcompat = "androidx.appcompat:appcompat:$appcompat_version"
        const val annotation = "androidx.annotation:annotation:$annotation_version"
    }

    object Threads {
        const val coroutines_version = "1.3.9"

        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"

    }

    object DI {
        const val dagger_version = "2.47"

        const val dagger = "com.google.dagger:dagger:$dagger_version"
        const val dagger_android = "com.google.dagger:dagger-android:$dagger_version"
        const val dagger_compiler = "com.google.dagger:dagger-compiler:$dagger_version"
        const val dagger_apt = "com.google.dagger:dagger-android-processor:$dagger_version"
    }

    object Logging {
        const val timber_version = "5.0.1"
        const val timber = "com.jakewharton.timber:timber:$timber_version"
    }

}