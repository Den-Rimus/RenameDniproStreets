
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

repositories {
    google()
    jcenter()
}

// App version
val versionMajor = 1
val versionMinor = 1
val versionPatch = 0
val versionCodee = 6 // incremental

android {
    compileSdkVersion(28)

    defaultConfig {
        applicationId = "ua.dp.rename.dniprostreets"
        minSdkVersion(15)
        targetSdkVersion(28)
        versionCode = versionCodee
        versionName = "$versionMajor.$versionMinor.$versionPatch ($versionCodee)"
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lintOptions {
        disable("InvalidPackage")
    }

    packagingOptions {
        exclude("META-INF/services/javax.annotation.processing.Processor")
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")

    val lifecycleVersion = "2.0.0"
    implementation("androidx.lifecycle:lifecycle-extensions:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")

    implementation("android.arch.navigation:navigation-fragment:1.0.0")

    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.0.0")

    implementation("com.google.code.gson:gson:2.8.5")

    val retrofitVersion = "2.5.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")

    implementation("io.reactivex.rxjava2:rxjava:2.2.9")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("com.jakewharton.rxbinding3:rxbinding-appcompat:3.0.0-alpha2")

    implementation("de.greenrobot:eventbus:2.4.0")

    val mosbyVersion = "3.1.1"
    implementation("com.hannesdorfmann.mosby3:mvp:$mosbyVersion")
    implementation("com.hannesdorfmann.mosby3:viewstate:$mosbyVersion")
    implementation("com.hannesdorfmann.mosby3:mvp-nullobject-presenter:$mosbyVersion")

    implementation("com.snappydb:snappydb-lib:0.5.2")
    implementation("com.esotericsoftware.kryo:kryo:2.24.0")

    val daggerVersion = "2.17"
    implementation("com.google.dagger:dagger:$daggerVersion")
    implementation("com.google.dagger:dagger-android:$daggerVersion")
    implementation("com.google.dagger:dagger-android-support:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    kapt("com.google.dagger:dagger-android-processor:$daggerVersion")

    // Dagger-related. Needed for @Generated annotation (missing in Java <= 1.6; therefore, Android)
    implementation("javax.annotation:jsr250-api:1.0")

    implementation("com.jakewharton.timber:timber:4.1.0")
}
