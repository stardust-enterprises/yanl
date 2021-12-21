package fr.stardustenterprises.yanl.api

const val OS_FORMAT = "{os}"
const val ARCH_FORMAT = "{arch}"
const val NAME_FORMAT = "{name}"

interface Context {
    val osName: String
    val archIdentifier: String
    val is64Bits: Boolean

    fun mapLibraryName(
        name: String,
        usePlatformPrefix: Boolean,
        usePlatformSuffix: Boolean
    ): String

    fun format(
        pathFormat: String,
        name: String
    ): String =
        pathFormat
            .replace(OS_FORMAT, osName, ignoreCase = true)
            .replace(ARCH_FORMAT, archIdentifier, ignoreCase = true)
            .lowercase()
            .replace(NAME_FORMAT, name, ignoreCase = true)
}
