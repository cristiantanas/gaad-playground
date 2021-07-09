// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Classpath.gradle)
        classpath(Dependencies.Classpath.kotlin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    jacoco
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = Dependencies.Plugin.jacoco)

    jacoco {
        toolVersion = Version.JACOCO
        reportsDir = file("${project.buildDir}/reports/jacoco")
    }

    tasks.register<JacocoReport>("codeCoverageReport") {
        dependsOn("testDebugUnitTest")

        group = "Code Coverage Reports"
        description = "Generates Jacoco coverage reports for the debugTests"

        reports {
            html.isEnabled = true
            xml.isEnabled = true
        }

        val classes = listOf(
            "${buildDir}/intermediates/javac/debug/classes",
            "${buildDir}/tmp/kotlin-classes/debug"
        )

        val mainSrc = listOf(
            "${project.projectDir}/src/main/java"
        )

        val fileFilter = mutableSetOf(
            "**/R.class",
            "**/R$*.class",
            "**/Manifest*.*",
            "android/**/*.*",
            "**/*Activity*.*",
            "**/*Fragment*.*",
            "**/*Adapter*.*",
            "**/*Binding*.*",
            "**/*CustomLayout*.*",
            "**/*CustomView*.*",
            "**/*ViewHolder*.*"
        )

        val distinctCompiledSources = classes
            .flatMap { project.fileTree(it) { exclude(fileFilter) } }
            .distinctBy { it.name }

        sourceDirectories.setFrom(project.files(mainSrc))
        classDirectories.setFrom(distinctCompiledSources)

        val buildDirectoryName = buildDir.name
        executionData.setFrom(
            project.fileTree(project.projectDir) {
                include(
                    "jacoco*.exec",
                    "$buildDirectoryName/jacoco/*.exec",
                    "$buildDirectoryName/outputs/code_coverage/debugAndroidTest/connected/*coverage.exec"
                )
            }
        )

        enabled = true
    }
}

tasks.register<Delete>("clean") {
    delete(buildDir)
}

tasks.register("codeCoverage") {
    dependsOn(":app:codeCoverageReport")

    // Add here the dependency to the codeCoverageReport task for each new module
    doLast {
        println("Code coverage reports generated for all project modules.")
    }
}