rootProject.name = "yanl"

pluginManagement.resolutionStrategy.eachPlugin {
    val id = requested.id.id
    if (id.contains("kotlin") ||
        id == "org.jetbrains.dokka") {
        useVersion(
            extra["kotlin.version"] as String?
                ?: throw kotlin.Exception("No Kotlin version given!")
        )
    }
}
