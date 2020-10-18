import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
}
group = "com.github.iobeer"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    api("com.pi4j:pi4j-core:1.2")
    api("com.pi4j:pi4j-device:1.2")
    api("com.pi4j:pi4j-gpio-extension:1.2")
    api("com.pi4j:pi4j-service:1.1")
    api("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.0.1")
    api("org.slf4j", "slf4j-api", "1.7.30")
    api("org.slf4j:slf4j-nop:1.7.30")
    api("org.mariadb.jdbc:mariadb-java-client:2.7.0")
    api("org.jetbrains.exposed", "exposed-core", "0.24.1")
    api("org.jetbrains.exposed", "exposed-dao", "0.24.1")
    api("org.jetbrains.exposed", "exposed-jdbc", "0.24.1")
    api("org.jetbrains.exposed", "exposed-java-time", "0.24.1")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
application {
    mainClassName = "MainKt"
}

tasks.register<Jar>("uberJar") {
    archiveClassifier.set("uber")

    manifest.attributes.set("Main-Class", "com.github.iobeer.brewbot.MainKt")
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter {it.name.endsWith("jar")}.map {zipTree(it)}
    })
}