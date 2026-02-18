rootProject.name = "lp"

// Include all Kotlin/Spring Boot modules
// Note: lp-frontend (Next.js), lp-python-model (Python), and lp-database (SQL scripts)
// are not Gradle modules and should not be included here
include(
    "lp-common",
    "lp-storage",
    "lp-api",
    "lp-batch",
    "lp-admin"
)
