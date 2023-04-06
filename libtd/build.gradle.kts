@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "org.drinkless.td.libcore"
    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets.getByName("main").jniLibs {
        srcDir("src/main/libs")
    }

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    lint {
        disable.add("InvalidPackage")
    }
}

dependencies {
    implementation(libs.androidx.annotation)
}
