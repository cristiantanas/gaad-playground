object Dependencies {
    const val androidCoreKtx = "androidx.core:core-ktx:${Version.CORE_KTX}"
    const val androidMaterial = "com.google.android.material:material:${Version.MATERIAL}"
    const val androidxCompat = "androidx.appcompat:appcompat:${Version.APP_COMPAT}"
    const val androidxConstraintLayout = "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT}"
    const val androidxEspresso = "androidx.test.espresso:espresso-core:${Version.ESPRESSO}"
    const val androidxExtJUnit = "androidx.test.ext:junit:${Version.EXT_JUNIT}"
    const val junit = "junit:junit:${Version.JUNIT}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Version.KOTLIN}"

    object Classpath {
        const val gradle = "com.android.tools.build:gradle:${Version.AGP}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.KOTLIN}"
    }

    object Plugin {
        const val jacoco = "org.gradle.jacoco"
    }
}

object Version {
    const val AGP = "4.2.2"
    const val APP_COMPAT = "1.3.0"
    const val CONSTRAINT_LAYOUT = "2.0.4"
    const val CORE_KTX = "1.6.0"
    const val ESPRESSO = "3.4.0"
    const val EXT_JUNIT = "1.1.3"
    const val JACOCO = "0.8.5"
    const val JUNIT = "4.13.2"
    const val KOTLIN = "1.5.20"
    const val MATERIAL = "1.4.0"
}