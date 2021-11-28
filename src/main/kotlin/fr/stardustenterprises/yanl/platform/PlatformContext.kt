package fr.stardustenterprises.yanl.platform

import fr.stardustenterprises.yanl.api.platform.IContext

const val OS = "{os}"
const val ARCH = "{arch}"
const val NAME = "{name}"

data class PlatformContext(
    val operatingSystem: String,
    val architecture: String,
    val binaryPrefix: String,
    val binaryExtension: String
) : IContext {
    override fun mapLibraryPath(string: String, name: String): String =
        string.replace(OS, operatingSystem, true)
            .replace(ARCH, architecture, true)
            .replace(NAME, name, true)

    override fun mapLibraryName(
        libraryName: String,
        usePlatformPrefix: Boolean,
        usePlatformExtension: Boolean
    ): String =
        (if (usePlatformPrefix) binaryPrefix else "") + libraryName + (if (usePlatformExtension) binaryExtension else "")
}
