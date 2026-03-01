plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
	id("org.springframework.boot")
	id("io.spring.dependency-management")
}

description = "Batch Server - Spring Batch jobs for data collection and processing"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

dependencies {
	// Depend on storage and common modules
	implementation(project(":lp-common"))
	implementation(project(":lp-storage"))

	// Spring Data JPA (needed for repository access)
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// Spring Batch
	implementation("org.springframework.boot:spring-boot-starter-batch")

	// WebFlux (WebClient for calling Python model and Riot API)
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// Quartz Scheduler
	implementation("org.springframework.boot:spring-boot-starter-quartz")

	// Actuator (health checks)
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// Test dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.batch:spring-batch-test")
}

// This is a Spring Boot application
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	enabled = true
	archiveFileName.set("lp-batch.jar")
}

tasks.named<Jar>("jar") {
	enabled = false
}
