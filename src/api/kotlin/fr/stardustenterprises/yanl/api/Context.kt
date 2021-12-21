package fr.stardustenterprises.yanl.api

const val OS = "{os}"
const val ARCH = "{arch}"
const val NAME = "{name}"

interface Context {

    fun getOperatingSystem(): String

    fun getArchitecture(): String

    fun is64Bit(): Boolean

    fun mapLibraryName(
        name: String,
        usePlatformPrefix: Boolean,
        usePlatformSuffix: Boolean
    ): String

    fun format(pathFormat: String, name: String): String =
        pathFormat.replace(OS, getOperatingSystem(), true)
            .replace(ARCH, getArchitecture(), true)
            .lowercase()
            .replace(NAME, name, true)

}