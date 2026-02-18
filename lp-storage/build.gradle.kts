plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("plugin.jpa")
	id("io.spring.dependency-management")
}

description = "Storage module - Database entities, repositories, and JPA configuration"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

dependencies {
	// Depend on common module
	implementation(project(":lp-common"))

	// Spring Data JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// Database drivers
	runtimeOnly("com.h2database:h2") // For testing
	runtimeOnly("org.xerial:sqlite-jdbc:3.45.0.0") // For local profile
	runtimeOnly("com.mysql:mysql-connector-j:8.3.0") // For prod profile

	// Hibernate SQLite dialect
	implementation("org.hibernate.orm:hibernate-community-dialects")

	// Test dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// JPA entity classes need to be open (not final)
allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

// This is a library module, not a Spring Boot application
tasks.named<Jar>("jar") {
	enabled = true
}
