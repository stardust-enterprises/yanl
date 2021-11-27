package fr.stardustenterprises.yanl.api.platform

const val OS = "{os}"
const val ARCH = "{arch}"
const val NAME = "{name}"

data class PlatformContext(
    val operatingSystem: String,
    val architecture: String,
    val binaryPrefix: String,
    val binaryExtension: String
) {
    fun populatePath(string: String, name: String): String =
        string.replace(OS, operatingSystem, true)
            .replace(ARCH, architecture, true)
            .replace(NAME, name, true)

    fun populateName(
        libraryName: String,
        usePlatformPrefix: Boolean,
        usePlatformExtension: Boolean
    ): String =
        (if(usePlatformPrefix) binaryPrefix else "") + libraryName + (if(usePlatformExtension) binaryExtension else "")
}
