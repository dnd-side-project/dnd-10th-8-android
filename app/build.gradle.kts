import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("io.sentry.android.gradle")
    id("com.google.devtools.ksp")
    kotlin("android")
    kotlin("kapt")
    alias(libs.plugins.gms)
}

android {
    namespace = "ac.dnd.mour.android"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "ac.dnd.mour.android"
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
        versionCode = libs.versions.app.versioncode.get().toInt()
        versionName = libs.versions.app.versionname.get()

        manifestPlaceholders["sentryDsnToken"] = getLocalProperty("SENTRY_DSN_TOKEN")
        manifestPlaceholders["kakaoAppKey"] = getLocalProperty("KAKAO_APP_KEY")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            android.buildFeatures.buildConfig = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "KAKAO_APP_KEY", getLocalProperty("KAKAO_APP_KEY"))
            resValue("string", "kakao_oauth_key", getLocalProperty("KAKAO_OAUTH_KEY"))
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            android.buildFeatures.buildConfig = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "KAKAO_APP_KEY", getLocalProperty("KAKAO_APP_KEY"))
            resValue("string", "kakao_oauth_key", getLocalProperty("KAKAO_OAUTH_KEY"))
        }
    }

    /**
     * Android 14 JDK 17 지원
     * url : https://developer.android.com/about/versions/14/behavior-changes-14?hl=ko#core-libraries
     */
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
}


sentry {
    // Disables or enables debug log output, e.g. for for sentry-cli.
    // Default is disabled.
    debug = false

    // The slug of the Sentry organization to use for uploading proguard mappings/source contexts.
    org = "dnd108-72"

    // The slug of the Sentry project to use for uploading proguard mappings/source contexts.
    projectName = "android"

    // The authentication token to use for uploading proguard mappings/source contexts.
    // WARNING: Do not expose this token in your build.gradle files, but rather set an environment
    // variable and read it into this property.
    authToken = getLocalProperty("SENTRY_AUTH_TOKEN")

    // The url of your Sentry instance. If you're using SAAS (not self hosting) you do not have to
    // set this. If you are self hosting you can set your URL here
    url = "https://sentry.io"

    // Disables or enables the handling of Proguard mapping for Sentry.
    // If enabled the plugin will generate a UUID and will take care of
    // uploading the mapping to Sentry. If disabled, all the logic
    // related to proguard mapping will be excluded.
    // Default is enabled.
    includeProguardMapping = true

    // Whether the plugin should attempt to auto-upload the mapping file to Sentry or not.
    // If disabled the plugin will run a dry-run and just generate a UUID.
    // The mapping file has to be uploaded manually via sentry-cli in this case.
    // Default is enabled.
    autoUploadProguardMapping = true

    // Experimental flag to turn on support for GuardSquare's tools integration (Dexguard and External Proguard).
    // If enabled, the plugin will try to consume and upload the mapping file produced by Dexguard and External Proguard.
    // Default is disabled.
    dexguardEnabled = false
}

dependencies {
    implementation(project(":common"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    implementation(libs.bundles.kotlin)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.bundles.androidx.data)
    implementation(libs.bundles.network)

    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.logging)
    debugImplementation(libs.leakcanary)

    implementation(libs.bundles.kakao)
}

fun getLocalProperty(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey) ?: System.getenv(propertyKey)
}
