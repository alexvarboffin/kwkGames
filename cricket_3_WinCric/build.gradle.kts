import java.text.SimpleDateFormat
import java.util.Date
import org.gradle.api.tasks.Copy


fun versionCodeDate(): Int {
    return SimpleDateFormat("yyMMdd").format(Date()).toInt()
}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}
val code = versionCodeDate()

android {
    //namespace = "com.mostbet.mostcric.tencric"
    //namespace = "com.cricmost.cricmst"
    //com.cricjojo.cricmost
    namespace = "com.cricmost.cricmost"

    compileSdk = 36

    defaultConfig {
        applicationId = "com.cricmost.cricmost"
        minSdk = 24
        targetSdk = 36
        versionCode = code
        versionName = "1.1.$code.full"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        setProperty("archivesBaseName", "com.cricmost.cricmost")
    }

    signingConfigs {

        create("x") {
            keyAlias = "release"
            keyPassword = "release"
            storeFile = file("keystore/keystore.jks")
            storePassword = "release"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("x")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("x")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }

    kotlin{
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }
    }

    buildFeatures {
        compose = true
    }
}

tasks.register("buildReleaseArtifacts", Copy::class) {
    dependsOn("assembleRelease", "bundleRelease")
    from(layout.buildDirectory.dir("outputs/apk/release")) {
        include("*.apk")
    }
    from(layout.buildDirectory.dir("outputs/bundle/release")) {
        include("*.aab")
    }
    into(rootProject.file("c:/build"))
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
    implementation(libs.androidx.browser)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}