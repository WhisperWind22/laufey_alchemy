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
        classpath("com.android.tools.build:gradle:8.1.0") // Убедитесь, что версия правильная
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21")
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

