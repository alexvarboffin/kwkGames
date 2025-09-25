pluginManagement {
    repositories {
        mavenCentral()  // Primary repository for dependencies
        google()        // Required for Android-specific dependencies
        gradlePluginPortal()  // Access to Gradle plugins

//        google {
//            mavenContent {
//                includeGroupAndSubgroups("androidx")
//                includeGroupAndSubgroups("com.android")
//                includeGroupAndSubgroups("com.google")
//            }
//        }
        maven("https://maven.google.com")
        maven("https://dl.bintray.com/videolan/Android")
    }
}

dependencyResolutionManagement {
    repositories {
        //google()
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        //mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://repo1.maven.org/maven2/")
        maven("https://androidx.dev/storage/compose-compiler/repository/")

        maven("https://maven.google.com")
        maven("https://dl.bintray.com/videolan/Android")
    }
}

rootProject.name = "kwkGames"
//include(":VAI")
//include(":Esporte")
//include(":VAIQwen")
//include(":TacticField")
////01 twa
//include(":cricket")//02. app
//include(":cricket_2_mostbet")//cricket clone
include(":Footbol")


include(":sdk")
include(":annotations")
