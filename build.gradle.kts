
buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.4.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.0.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks {
    registering(Delete::class) {
        delete(buildDir)
    }
}
