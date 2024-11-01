// settings.gradle.kts
pluginManagement {
    repositories {
        google() // Репозиторий Google
        mavenCentral() // Maven Central
        gradlePluginPortal() // Gradle Plugin Portal, если нужно
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google() // Репозиторий Google
        mavenCentral() // Maven Central
    }
}

rootProject.name = "Alchemy"
include(":app")
