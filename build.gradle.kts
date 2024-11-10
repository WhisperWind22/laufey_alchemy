// build.gradle.kts (уровень проекта)
plugins {
    `kotlin-dsl`
}

buildscript {
    repositories {
        google() // Добавьте Google репозиторий
        mavenCentral() // Добавьте Maven Central репозиторий
    }
    dependencies {
        classpath(libs.gradle) // Убедитесь, что версия правильная
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.gson)
    }
}

allprojects {
    repositories {
        google() // Добавьте Google репозиторий
        mavenCentral() // Добавьте Maven Central репозиторий
    }
}

//tasks.register("clean", Delete::class) {
//    delete(project.layout.buildDirectory.get().asFile)
//}

