plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
	id("org.springframework.boot")
	id("io.spring.dependency-management")
}

description = "Admin Server - Admin backoffice for system management"

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

	// Spring Security (stronger security for admin)
	implementation("org.springframework.boot:spring-boot-starter-security")

	// Validation
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// Actuator
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// Spring Boot Admin Server (optional - for monitoring dashboard)
	// Uncomment if you want to use Spring Boot Admin UI
	// implementation("de.codecentric:spring-boot-admin-starter-server:3.2.1")

	// Test dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

// This is a Spring Boot application
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	enabled = true
	archiveFileName.set("lp-admin.jar")
}

tasks.named<Jar>("jar") {
	enabled = false
}
