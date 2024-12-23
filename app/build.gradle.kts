plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "frgp.utn.edu.ar.quepasa"
    compileSdk = 35

    defaultConfig {
        applicationId = "frgp.utn.edu.ar.quepasa"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/DEPENDENCIES"
            excludes += "/META-INF/LICENSE"
            excludes += "/META-INF/LICENSE.txt"
            excludes += "/META-INF/NOTICE"
            excludes += "/META-INF/NOTICE.txt"

            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

// ksp {
    // correctErrorTypes = true
    // generateStubs = true
// }

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(fileTree(mapOf(
        "dir" to "libs/",
        "include" to listOf("*.aar", "*.jar"),
        "exclude" to listOf("*.exe")
    )))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    /** Retrofit dependencies **/
    implementation(libs.retrofit)
    implementation(libs.converter)
    /** Dagger & Hilt dependencies **/
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    /** Material Icons **/
    // https://mvnrepository.com/artifact/androidx.compose.material/material-icons-extended
    runtimeOnly(libs.androidx.material.icons.extended)
    implementation (libs.logging.interceptor)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.coil.network.ktor2)
    implementation(libs.coil.network.ktor3)
    implementation(libs.libphonenumber)
    implementation (libs.compose.shimmer)
    // https://mvnrepository.com/artifact/org.ocpsoft.prettytime/prettytime
    implementation(libs.prettytime)
    implementation (libs.google.api.client)
    implementation (libs.google.oauth.client.jetty)
    implementation (libs.google.api.services.calendar)


}