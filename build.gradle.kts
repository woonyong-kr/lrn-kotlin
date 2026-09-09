plugins {
    kotlin("jvm") version "2.4.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "learning.kotlininaction.chapter02.colors.MainKt"
}

tasks.register<JavaExec>("runLabelFlow") {
    group = "application"
    description = "Run Kotlin label examples and print their control-flow order"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass = "learning.kotlininaction.chapter02.labels.LabelFlowKt"
}

tasks.register<JavaExec>("runRangeProgression") {
    group = "application"
    description = "Run Kotlin range and progression boundary examples"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass = "learning.kotlininaction.chapter02.ranges.RangeProgressionKt"
}

tasks.register<JavaExec>("runChapter02Review") {
    group = "application"
    description = "Run the complete Kotlin in Action Chapter 2 review path"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass = "learning.kotlininaction.chapter02.review.Chapter02ReviewKt"
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("runEqualityAndHashing") {
    group = "application"
    description = "Compare equality, hashing, collisions, and mutable keys in JVM collections"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass = "learning.kotlininaction.chapter04.equality.EqualityAndHashingKt"
}

tasks.register<JavaExec>("runCaptureAndCalls") {
    group = "application"
    description = "Compare captures, SAM identity, inline calls, and receiver functions"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass = "learning.kotlininaction.chapter05.lambdas.CaptureAndCallsKt"
}
