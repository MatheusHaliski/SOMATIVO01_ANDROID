pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.6.0" apply false
        id("org.jetbrains.kotlin.android") version "1.9.0" apply false
        id("dagger.hilt.android.plugin") version "2.48"
    }

}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "myinitactiv1"
include(":app")
