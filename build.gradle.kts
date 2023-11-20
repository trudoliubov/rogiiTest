import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val junit = "5.7.1"
val springBoot = "2.5.0"
val ktorVersion = "2.2.2"
val ktorAssert = "4.4.3"
val kotestAssert = "5.3.2"
val allure = "2.12.1"
val jacksonDatatypeJsr310 = "2.12.5"
var javaxValidationApiVersion = "2.0.1.Final"
val jsoupVersion = "1.15.2"



plugins {
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.6.20"
    id("io.qameta.allure") version "2.10.0"
}

group = "com.rogii"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

allure {
    version.set("2.12.1")
    adapter {
        aspectjWeaver.set(false)
        frameworks {
            junit5
        }
    }
}

java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    implementation(kotlin("stdlib"))
    implementation(platform("org.junit:junit-bom:$junit"))
    implementation("org.junit.jupiter:junit-jupiter")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonDatatypeJsr310")
    implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBoot"))
    implementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.mockito")
    }
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.7.1")
    implementation("io.kotest:kotest-assertions-core:$kotestAssert")
    implementation("io.kotest:kotest-assertions-ktor:$ktorAssert")
    implementation("javax.validation", "validation-api", javaxValidationApiVersion)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0-rc1")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-java:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("org.jsoup:jsoup:$jsoupVersion")
    implementation("io.qameta.allure", "allure-junit5", allure)
    implementation("io.qameta.allure", "allure-java-commons", allure)
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = java.sourceCompatibility.majorVersion
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy("allureReport")
}


