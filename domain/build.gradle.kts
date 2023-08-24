plugins {
    id("java-library")
    id("kotlin")
    id ("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(CommonDependencies.Threads.coroutines)

    implementation(CommonDependencies.DI.dagger)
    implementation(CommonDependencies.DI.dagger_android)
    kapt(CommonDependencies.DI.dagger_compiler)
    kapt(CommonDependencies.DI.dagger_apt)
}