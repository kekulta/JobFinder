plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ktorfit)
    id("com.google.devtools.ksp")
}

android {
    namespace = "tech.kekulta.jobfinder"
    compileSdk = 34

    defaultConfig {
        applicationId = "tech.kekulta.jobfinder"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":uikit"))
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.core.ktx)
    implementation(libs.viewbinding.delegate)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.coroutines.android)
    implementation(libs.androidx.activity)
    implementation(libs.navcomponent.fragments)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.logcat)
    implementation(libs.glide)
    implementation(libs.bundles.ktorfit)
    ksp(libs.ktorfit.ksp)
}