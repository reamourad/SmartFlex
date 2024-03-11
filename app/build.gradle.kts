plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.smartflex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.smartflex"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packaging {
        packaging {
            resources {
                excludes += "META-INF/*"
                excludes += "mozilla/public-suffix-list.txt"
            }
        }
    }
}


dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.ai.client.generativeai:generativeai:0.2.1")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation (platform("com.google.cloud:libraries-bom:26.34.0"))
    implementation ("com.google.cloud:google-cloud-vertexai")
    implementation ("io.grpc:grpc-okhttp:1.62.0")

    // Include MPAndroidChart library
    implementation ("com.github.PhilJay:MPAndroidChart:v3.0.3")
}
