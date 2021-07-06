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
    js(IR).browser()
    js(IR).nodejs()
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    jvm{
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js().browser {
        testRuns["test"].executionTask.configure {
            useMocha()
        }
    }
    js().nodejs {
        testRuns["test"].executionTask.configure {
            useMocha()
        }
    }

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
        val jvmTest by getting{
            dependencies {
                implementation(kotlin("test-junit5"))
            }
        }
        val jsTest by getting{
            dependencies {
                implementation(kotlin("test-js"))
                //implementation(npm( "mocha", "9.0.2" ))
            }

        }
    }

    explicitApi = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Strict
}