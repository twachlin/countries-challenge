import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.android.ksp) apply false
    alias(libs.plugins.hilt) apply false
}

buildscript {
    dependencies {
        classpath(libs.google.maps.secrets)
    }
}