plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("de.undercouch.download") version "5.6.0"
    kotlin("plugin.serialization") version "1.8.10"
}

android {
    namespace = "com.example.tensorflow"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tensorflow"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        debug {

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    androidResources {
        noCompress("tflite")
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src/main/assets")
            }
        }
    }
}

// import DownloadModels task
//val ASSET_DIR: String by project("$projectDir/src/main/assets")
//val TEST_ASSET_DIR: String by project("$projectDir/src/androidTest/assets")

/*apply {
    from("download_model.gradle")
}*/

dependencies {
    val navVersion = "2.7.2"
    val ktor_version = "2.3.4"
    //Tensor Flow
    implementation("org.tensorflow:tensorflow-lite-task-vision-play-services:0.4.2")
    implementation("com.google.android.gms:play-services-tflite-gpu:16.1.0")
    implementation("org.tensorflow:tensorflow-lite-task-text:0.4.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.4")

    // Gson library
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.google.guava:guava:28.1-android")

    //Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    //Hilt end
    //Nav dependencies

    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$navVersion")

    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:$navVersion")

    val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
    implementation(composeBom)
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")

    //End Nav

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.09.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material-icons-extended-android:1.6.8")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0-alpha02")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.09.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //API
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}