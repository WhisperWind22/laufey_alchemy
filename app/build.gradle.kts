// build.gradle.kts (уровень модуля app)
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

android {

    namespace = "com.example.alchemy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.alchemy"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }


}

dependencies {
    implementation(libs.androidx.core.ktx.v1101)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.material.v190)
    implementation(libs.androidx.navigation.fragment.ktx.v260)
    implementation(libs.androidx.navigation.ui.ktx.v260)
    implementation(libs.coil.compose)
    implementation(libs.gson)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)


    // Room components
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.ui.viewbinding)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.androidx.material3.android)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Testing components
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
