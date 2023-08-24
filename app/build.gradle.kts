plugins {
    id("com.android.application")
    kotlin("android")
    id ("kotlin-kapt")
}

android {
    namespace = "by.devnmisko.testbs"
    compileSdk = 33

    defaultConfig {
        applicationId = "by.devnmisko.testbs"
        minSdk = 24
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation(CommonDependencies.AndroidX.core)
    implementation(CommonDependencies.AndroidX.appcompat)
    implementation(CommonDependencies.Threads.coroutines)

    implementation(AppDependencies.UI.material)
    implementation(AppDependencies.UI.recycler_view)
    implementation(AppDependencies.UI.activity_ktx)
    implementation(AppDependencies.UI.nav_fragment)
    implementation(AppDependencies.UI.nav_ui)
    implementation(AppDependencies.UI.fragment)
    implementation(AppDependencies.UI.lifecycle)
    implementation(AppDependencies.UI.constraint_layout)

    implementation(CommonDependencies.DI.dagger)
    implementation(CommonDependencies.DI.dagger_android)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt(CommonDependencies.DI.dagger_compiler)
    kapt(CommonDependencies.DI.dagger_apt)

    implementation(CommonDependencies.Logging.timber)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(project(":data"))
    implementation(project(":domain"))
}