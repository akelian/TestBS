object DataDependencies {

    object Networking {
        private  const val retrofit_version = "2.9.0"
        private const val retrofit_coroutine_adapter_version = "0.9.2"
        private const val gson_version = "2.10.1"
        private const val okhttp_version = "4.10.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"
        const val retrofit_coroutine_adapter =
            "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$retrofit_coroutine_adapter_version"
        const val gson = "com.google.code.gson:gson:$gson_version"
        const val converter_gson = "com.squareup.retrofit2:converter-gson:$retrofit_version"
        const val okhttp_bom = "com.squareup.okhttp3:okhttp-bom:$okhttp_version"
        const val okhttp = "com.squareup.okhttp3:okhttp"
        const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor"
    }

    object Data {
        private const val room_version = "2.5.2"

        const val room_ktx = "androidx.room:room-ktx:$room_version"
        const val room_runtime = "androidx.room:room-runtime:$room_version"
        const val room_compiler = "androidx.room:room-compiler:$room_version"
    }
}