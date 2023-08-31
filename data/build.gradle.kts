plugins {
    id("com.android.library")
    kotlin("android")
    id ("kotlin-kapt")
}

android {
    namespace = "by.devnmisko.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {

    implementation(CommonDependencies.Threads.coroutines)
    implementation(CommonDependencies.AndroidX.annotation)

    api(DataDependencies.Networking.retrofit)
    api(DataDependencies.Networking.retrofit_coroutine_adapter)
    api(DataDependencies.Networking.gson)
    api(DataDependencies.Networking.converter_gson)
    api(platform(DataDependencies.Networking.okhttp_bom))
    api(DataDependencies.Networking.okhttp)
    api(DataDependencies.Networking.okhttp_logging_interceptor)

    api(DataDependencies.Data.room_runtime)
    api(DataDependencies.Data.room_ktx)
    api(DataDependencies.Data.room_paging)
    kapt(DataDependencies.Data.room_compiler)

    implementation(CommonDependencies.DI.dagger)
    implementation(CommonDependencies.DI.dagger_android)
    kapt(CommonDependencies.DI.dagger_compiler)
    kapt(CommonDependencies.DI.dagger_apt)

    implementation(CommonDependencies.Logging.timber)

    implementation(project(":domain"))
}