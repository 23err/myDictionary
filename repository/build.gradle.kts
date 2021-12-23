plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}


android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
    implementation(Dependencies.ROOM_RUNTIME_DEP)
    kapt(Dependencies.ROOM_COMPILER_DEP)
    implementation(Dependencies.ROOM_KTX_DEP)
    implementation(Dependencies.OKHTTP_DEP)
    implementation(Dependencies.RETROFIT_DEP)
    implementation(Dependencies.RETROFIT_CONVERTER_GSON_DEP)
    implementation(Dependencies.RETROFIT_ADAPTER_RXJAVA3_DEP)
    implementation(Dependencies.RETROFIT_COROUTINES_ADAPTER_DEP)
    implementation(Dependencies.CONSCRYPT_ANDROID_DEP)
    implementation(Dependencies.COROUTINES_CORE_DEP)
    implementation(Dependencies.COROUTINES_ANDROID_DEP)
}