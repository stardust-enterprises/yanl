package fr.stardustenterprises.yanl.api

/**
 * The text to replace with the OS name when formatting.
 */
const val OS_FORMAT = "{os}"

/**
 * The text to replace with the architecture identifier when formatting.
 */
const val ARCH_FORMAT = "{arch}"

/**
 * The text to replace with the name of the library when formatting.
 */
const val NAME_FORMAT = "{name}"

/**
 * Defines a YANL platform context.
 *
 * @author xtrm
 */
interface Context {
    /**
     * The operating system's name in the current context.
     */
    val osName: String

    /**
     * The architecture's identifier in the current context.
     */
    val archIdentifiers: Array<String>

    /**
     * Whether the current context is 64-bits capable.
     */
    val is64Bits: Boolean

    /**
     * Maps a given library name using the current context.
     *
     * @param name The name of the library.
     * @param usePlatformPrefix Whether to use the platform prefix.
     * @param usePlatformSuffix Whether to use the platform suffix.
     *
     * @return The mapped library name.
     */
    fun mapLibraryName(
        name: String,
        usePlatformPrefix: Boolean,
        usePlatformSuffix: Boolean,
    ): String

    /**
     * Replaces OS, Arch and Name values from the given `pathFormat` string
     * using the constants as identifiers and the fields and name parameter as
     * values.
     *
     * @param pathFormat The String to format.
     * @param name The name to add to the format.
     *
     * @return The formatted String.
     */
    fun format(
        pathFormat: String,
        name: String,
    ): Array<String> =
        archIdentifiers.map { archIdentifier ->
            pathFormat
                .replace(OS_FORMAT, osName, ignoreCase = true)
                .replace(ARCH_FORMAT, archIdentifier, ignoreCase = true)
                .lowercase()
                .replace(NAME_FORMAT, name, ignoreCase = true)
        }.toTypedArray()
}
