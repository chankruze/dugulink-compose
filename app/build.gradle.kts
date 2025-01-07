import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
}

val versionPropsFile = file("version.properties")
val versionProps = Properties().apply {
    load(FileInputStream(versionPropsFile))
}

android {
    namespace = "com.geekofia.dugulink"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.geekofia.dugulink"
        minSdk = 25
        targetSdk = 34
        versionCode = versionProps["versionCode"].toString().toInt()
        versionName = convertVersionCodeToVersionName(versionCode!!)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    applicationVariants.all {
        outputs.all {
            val appName = rootProject.name
            val versionCode = versionCode
            val variantName = name
            val fileExtension = if (outputFile.name.endsWith(".apk")) "apk" else "aab"

            val newFileName = "${appName}-${variantName}-${versionCode}.${fileExtension}"
            (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName =
                newFileName
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // Navigation
    implementation(libs.androidx.navigation.compose)
    // Firebase BoM
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    // Debug dependencies
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Function to convert versionCode into versionName (x.y.z pattern)
fun convertVersionCodeToVersionName(versionCode: Int): String {
    val patch = versionCode % 10
    val minor = (versionCode / 10) % 10
    val major = versionCode / 100

    return "$major.$minor.$patch"
}

// Task to increment versionCode
tasks.register("incrementVersionCode") {
    doLast {
        val currentVersionCode = versionProps["versionCode"].toString().toInt()
        versionProps["versionCode"] = (currentVersionCode + 1).toString()
        versionProps["versionName"] =
            convertVersionCodeToVersionName(versionProps["versionCode"].toString().toInt())
        versionProps.store(FileOutputStream(versionPropsFile), null)
        println("Version code incremented to ${versionProps["versionCode"]}")
        println("Version name updated to ${versionProps["versionName"]}")
    }
}

// Make incrementVersionCode dependent on build tasks
tasks.whenTaskAdded {
    if (name == "assembleDebug" || name == "assembleRelease") {
        finalizedBy("incrementVersionCode")
    }
}
