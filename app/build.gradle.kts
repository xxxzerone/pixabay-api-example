import com.android.build.api.dsl.ApplicationExtension
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

/**
 * android {} 블록 내부에서 Kotlin DSL로 접근할 때
 * 현재 사용하는 BaseAppModuleExtension 기반 확장은 deprecated 되었음.
 * 대신 **ApplicationExtension**을 사용하라는 뜻.
 * AGP 9.0부터는 android.newDsl=true가 기본
 * 새로운 DSL에서 BaseAppModuleExtension은 내부용
 * public extension으로는 더 이상 사용되지 않음
 * AGP 10.0에서는 완전히 제거될 예정
 *
 * 해결 방법: 새로운 DSL 사용 (권장)
 * Gradle에서 android {} 블록을 extensions.getByType<ApplicationExtension>() 형태로 접근
 *
 * https://developer.android.com/build/migrate-to-built-in-kotlin?utm_source=chatgpt.com
 */
val android = extensions.getByType<ApplicationExtension>()

val localProperties = Properties().apply {
    val propertiesFile = rootProject.file("local.properties")
    if (propertiesFile.exists()) {
        propertiesFile.inputStream().use { load(it) }
    }
}

android.apply {
    namespace = "com.example.pixbayphoto"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.pixbayphoto"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
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
    buildFeatures {
        compose = true
        resValues = true
    }

    val pixabayApiKey = localProperties.getProperty("PIXABAY_API_KEY") ?: ""

    flavorDimensions += "version"
    productFlavors {
        create("dev") {
            dimension = "version"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }
        create("prod") {
            dimension = "version"
            resValue("string", "pixabay_api_key", pixabayApiKey)
        }
    }
}

dependencies {
    // Viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Navigation Component
    implementation(libs.androidx.navigation.compose)

    // Kotlinx
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.kotlinx.coroutines.test)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    testImplementation(libs.ktor.client.mock)

    // Coil Compose
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Mockk
    testImplementation(libs.mockk)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}