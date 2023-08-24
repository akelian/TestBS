plugins {
    id("com.android.library")
    kotlin("android")
    id ("kotlin-kapt")
}

android {
    namespace = "by.devnmisko.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        jvmTarget = "17"
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

    implementation(DataDependencies.Data.room_ktx)
    api(DataDependencies.Data.room_runtime)
    annotationProcessor(DataDependencies.Data.room_compiler)
    kapt(DataDependencies.Data.room_compiler)

    implementation(CommonDependencies.DI.dagger)
    implementation(CommonDependencies.DI.dagger_android)
    kapt(CommonDependencies.DI.dagger_compiler)
    kapt(CommonDependencies.DI.dagger_apt)

    implementation(CommonDependencies.Logging.timber)

    implementation(project(":domain"))
}