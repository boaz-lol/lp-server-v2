plugins {
	kotlin("jvm")
}

description = "Common module - Shared utilities, DTOs, enums, and exceptions"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

dependencies {
	// Jakarta Validation API (for validation annotations in DTOs)
	implementation("jakarta.validation:jakarta.validation-api:3.0.2")

	// No Spring dependencies - this is a pure Kotlin module
	// No JPA dependencies - this module should not know about persistence
}

// This is a library module, not a Spring Boot application
tasks.named<Jar>("jar") {
	enabled = true
}
