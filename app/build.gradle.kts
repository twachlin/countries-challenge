import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.android.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.maps.secrets)
}

android {
    namespace = "com.example.countries_challenge"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.countries_challenge"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val secretsFile = file("${rootDir}/secrets.properties")
        val properties = Properties()
        properties.load(secretsFile.inputStream())
        val mapsApiKey: String = properties.getProperty("MAPS_API_KEY") ?: ""
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Maps
    implementation(libs.google.maps.compose)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Debugging
    debugImplementation(libs.androidx.compose.ui.tooling)
}
