import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val coroutinesVersion = "0.22.5"
val kotlinVersion = "1.2.51"
val reactorVersion = "Californium-BUILD-SNAPSHOT"
val springVersion = "5.1.0.BUILD-SNAPSHOT"
val springBootVersion = "2.0.2.RELEASE"
val springFuVersion = "0.0.1.BUILD-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven("https://repo.spring.io/libs-release")
    maven("https://repo.spring.io/libs-milestone")
    maven("https://repo.spring.io/libs-snapshot")
    maven("https://repo.spring.io/snapshot")
    maven("https://repo.spring.io/milestone")
}


plugins {
    kotlin("jvm").version("1.2.50")
    application
    id("io.spring.dependency-management").version("1.0.5.RELEASE")
    id("com.github.johnrengelman.shadow").version("2.0.4")
}

application {
    mainClassName = "eu.wojciechzurek.example.ApplicationKt"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }

}

dependencies {
    implementation("org.springframework.fu:spring-fu:$springFuVersion")

    implementation("org.springframework.fu.module:spring-fu-logging:$springFuVersion")
    implementation("org.springframework.fu.module:spring-fu-logging-logback:$springFuVersion")
    implementation("org.springframework.fu.module:spring-fu-mongodb:$springFuVersion")
    implementation("org.springframework.fu.module:spring-fu-webflux-jackson:$springFuVersion")
    implementation("org.springframework.fu.module:spring-fu-webflux-mustache:$springFuVersion")
    implementation("org.springframework.fu.module:spring-fu-webflux-netty:$springFuVersion")

    testImplementation("org.springframework.fu.module:spring-fu-test:$springFuVersion")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion") {
            bomProperty("spring.version", springVersion)
            bomProperty("reactor-bom.version", reactorVersion)
        }
    }

    dependencies {
        dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
        dependency("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")
    }
}

configurations.all { exclude(module = "slf4j-simple") }