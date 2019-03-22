import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val assertJVersion: String by project
val jupiterVersion: String by project
val slf4jApiVersion: String by project
val tinylogImplVersion: String by project

plugins {
    kotlin("jvm") version "1.3.21"
}

group = "com.github.nwillc"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.slf4j:slf4j-api:$slf4jApiVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")

    testRuntime("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
    testRuntime("org.tinylog:slf4j-binding:$tinylogImplVersion")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
    withType<Test> {
        useJUnitPlatform()
        testLogging.showStandardStreams = true
    }
}