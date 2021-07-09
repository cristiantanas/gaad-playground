plugins {
    id("com.android.application")
    kotlin("android")
}

val appVersion = AppVersion.of("version.properties")

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "com.ctanas.android.alerts"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = appVersion.versionCode
        versionName = appVersion.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.androidCoreKtx)
    implementation(Dependencies.androidxCompat)
    implementation(Dependencies.androidMaterial)
    implementation(Dependencies.androidxConstraintLayout)
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidxExtJUnit)
    androidTestImplementation(Dependencies.androidxEspresso)
}

android.applicationVariants.all {
    outputs.forEach { output ->
        if (output is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {
            output.outputFileName =
                ProjectConfig.applicationName.replace("\\s".toRegex(), "") +
                        "-v$versionName" +
                        "($versionCode)" +
                        ".${output.outputFile.extension}"
        }
    }
}
