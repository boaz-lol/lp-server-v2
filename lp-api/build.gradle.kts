plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
	id("org.springframework.boot")
	id("io.spring.dependency-management")
}

description = "API Server - REST API backend for frontend"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

dependencies {
	// Depend on storage and common modules
	implementation(project(":lp-common"))
	implementation(project(":lp-storage"))

	// Spring Web MVC
	implementation("org.springframework.boot:spring-boot-starter-webmvc")

	// Spring Security (for authentication)
	implementation("org.springframework.boot:spring-boot-starter-security")

	// Validation
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// WebFlux (WebClient for Riot API calls)
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// Actuator (health checks for Docker)
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// Test dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

// This is a Spring Boot application
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	enabled = true
	archiveFileName.set("lp-api.jar")
}

tasks.named<Jar>("jar") {
	enabled = false
}
