import io.gitlab.arturbosch.detekt.CONFIGURATION_DETEKT_PLUGINS

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Classpath.gradle)
        classpath(Dependencies.Classpath.kotlin)
        classpath(Dependencies.Classpath.detekt)

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

    apply(plugin = Dependencies.Plugin.detekt)

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        toolVersion = Version.DETEKT
        config = rootProject.files("detekt-config.yml")

        autoCorrect = false
        buildUponDefaultConfig = true
        ignoreFailures = true

        reports {
            html.enabled = true
            xml.enabled = true
            txt.enabled = false
            sarif {
                enabled = false
            }
        }
    }

    val detektPlugins = configurations.getByName(CONFIGURATION_DETEKT_PLUGINS)
    dependencies {
        detektPlugins(Dependencies.detektFormatting)
    }

    tasks.register<io.gitlab.arturbosch.detekt.Detekt>("detektFormat") {
        description = "Applies detekt-formatting to the whole project"

        autoCorrect = true
        buildUponDefaultConfig = true
        ignoreFailures = true

        setSource(files(projectDir))
        include("**/*.kt")
        exclude("**/*.kts")
        exclude("**/resources/**")
        exclude("**/build/**")

        reports {
            html.enabled = true
            xml.enabled = true
            txt.enabled = false
            sarif {
                enabled = false
            }
        }
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
            "$buildDir/intermediates/javac/debug/classes",
            "$buildDir/tmp/kotlin-classes/debug"
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
