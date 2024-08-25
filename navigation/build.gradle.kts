plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "tech.kekulta.navigation"
    compileSdk = 34

    defaultConfig {
        minSdk = 31

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
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
}