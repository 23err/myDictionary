plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}


android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.example.mydictionary"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

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
    implementation(Dependencies.CORE_KTX_DEP)
    implementation(Dependencies.APPCOMPAT_DEP)
    implementation(Dependencies.MATERIAL_DEP)
    implementation(Dependencies.CONSTRAINT_LAYOUT_DEP)
    implementation(Dependencies.JUNIT_DEP)
    implementation(Dependencies.EXT_JUNIT_DEP)
    implementation(Dependencies.ESPRESSO_CORE_DEP)
    implementation(Dependencies.CICERONE_DEP)
    implementation(Dependencies.MOXY_COMPILER_DEP)
    implementation(Dependencies.MOXY_KTX_DEP)
    implementation(Dependencies.MOXY_ANDROIDX_DEP)
    implementation(Dependencies.GLIDE_DEP)
//    implementation(Dependencies.GLIDE_COMPILER_DEP)
    implementation(Dependencies.KOIN_CORE_DEP)
    implementation(Dependencies.KOIN_ANDROID_COMPAT_DEP)
    implementation(Dependencies.KOIN_ANDROID_DEP)
    implementation(Dependencies.ROOM_RUNTIME_DEP)
    annotationProcessor(Dependencies.ROOM_COMPILER_DEP)
    implementation(Dependencies.ROOM_KTX_DEP)
    implementation(Dependencies.OKHTTP_DEP)
    implementation(Dependencies.RETROFIT_DEP)
    implementation(Dependencies.RETROFIT_CONVERTER_GSON_DEP)
    implementation(Dependencies.RETROFIT_ADAPTER_RXJAVA3_DEP)
    implementation(Dependencies.RETROFIT_COROUTINES_ADAPTER_DEP)
    implementation(Dependencies.CONSCRYPT_ANDROID_DEP)
    implementation(Dependencies.COROUTINES_CORE_DEP)
    implementation(Dependencies.COROUTINES_ANDROID_DEP)

    implementation(project(":repository"))
}