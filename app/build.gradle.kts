import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
}



android {
    namespace = "com.sosauce.cutecalc"
    compileSdk = 36

    defaultConfig {

        applicationId = "com.sosauce.cutecalc"
        minSdk = 23
        targetSdk = 36
        versionCode = 40001
        versionName = "3.6.4"
        ndk {
            //noinspection ChromeOsAbiSupport
            abiFilters += arrayOf("arm64-v8a", "armeabi-v7a")
        }

    }

    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val outputFileName = "CC_${variant.versionName}.apk"
                output.outputFileName = outputFileName
            }
    }

    signingConfigs {
        create("release") {
            storeFile = file("release_key.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = true
            signingConfig = signingConfigs.getByName("release")
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

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    buildFeatures {
        compose = true
        aidl = false
        renderScript = false
        shaders = false
        buildConfig = false
        resValues = false
        viewBinding = false
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.keval)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.compose.material3)
    ksp(libs.androidx.room.compiler)
}
