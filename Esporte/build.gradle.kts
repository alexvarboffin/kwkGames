plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //alias(libs.plugins.hilt)
    //alias(libs.plugins.ksp)

    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}
val majorVersion = 1
val minorVersion = 0
val patchVersion = 12
//val versionSuffix = "beta.1"
val versionSuffix = ""//"" для стабильной версии

val _versionName = if (versionSuffix.isNotBlank()) {
    "$majorVersion.$minorVersion.$patchVersion-$versionSuffix"
} else {
    "$majorVersion.$minorVersion.$patchVersion"
}

val _versionCode = majorVersion * 10000 + minorVersion * 100 + patchVersion
//=====================================================================


//com.esportefootbal.esportedasortee

android {
    namespace = "com.aputot.apuestatotal.apuestape"
    compileSdk = 36

    defaultConfig {
        namespace = "com.aputot.apuestatotal.apuestape"
        minSdk = 24
        targetSdk = 36
        versionCode = _versionCode
        versionName = _versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        setProperty("archivesBaseName", "com.aputot.apuestatotal.apuestape")
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
    implementation(project(":sdk"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Kotlinx Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")


    implementation("androidx.media3:media3-exoplayer:1.8.0")
    implementation("androidx.media3:media3-exoplayer-dash:1.8.0")
    implementation("androidx.media3:media3-ui:1.8.0")

    // Hilt
//    implementation(libs.hilt.android)
//    implementation(libs.androidx.hilt.navigation.compose)
//    ksp(libs.hilt.compiler)

    implementation("androidx.navigation:navigation-compose:2.9.3")
    implementation("androidx.browser:browser:1.8.0")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2")

    implementation("androidx.room:room-runtime:2.7.2")
    implementation("androidx.room:room-ktx:2.7.2")
    kapt("androidx.room:room-compiler:2.7.2")

    // Koin
    implementation(platform("io.insert-koin:koin-bom:3.5.6"))
    implementation("io.insert-koin:koin-android")
    implementation("io.insert-koin:koin-androidx-compose")

    // Kotlinx Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("nl.dionsegijn:konfetti-compose:2.0.2")
    implementation("br.com.devsrsouza.compose.icons:font-awesome:1.1.0")
}