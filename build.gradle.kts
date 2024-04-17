plugins {
    java
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.bookingbee"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation("org.projectlombok:lombok:1.18.28")
    implementation ("org.springframework.boot:spring-boot-starter-security")
    compileOnly ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")
    implementation ("com.google.cloud:spring-cloud-gcp-starter-data-firestore:5.0.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.google.firebase:firebase-admin:8.0.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
