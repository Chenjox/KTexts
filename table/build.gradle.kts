plugins {
    kotlin("multiplatform") version "1.5.10"
    id("maven-publish")
}

val grouping = "chenjox.util.texts"
val artifac = "table"
val vers = "0.1.1-PreAlpha"

group = grouping
version = vers

repositories {
    mavenCentral()
    mavenLocal()
}

kotlin {
    jvm()
    js()
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting{
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}