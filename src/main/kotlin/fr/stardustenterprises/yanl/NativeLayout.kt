package fr.stardustenterprises.yanl

import fr.stardustenterprises.yanl.api.*
import java.net.URI

/**
 * The YANL native layout implementation, following the [Layout] interface.
 *
 * @author xtrm
 * @see Layout
 */
data class NativeLayout(
    /**
     * The format to use for locating the native.
     */
    private val pathFormat: String,

    /**
     * Whether to use the platform prefix.
     */
    private val usePlatformPrefix: Boolean = true,

    /**
     * Whether to use the platform suffix.
     */
    private val usePlatformSuffix: Boolean = true
) : Layout {
    companion object {
        /**
         * A flat native layout, consisting in only the name format.
         */
        @JvmStatic
        val FLAT_LAYOUT = NativeLayout(NAME_FORMAT)

        /**
         * A hierarchical native layout, consisting in the OS, arch and name
         * formats.
         */
        @JvmStatic
        val HIERARCHICAL_LAYOUT =
            NativeLayout("$OS_FORMAT/$ARCH_FORMAT/$NAME_FORMAT")
    }

    override fun locateLibrary(
        rootPath: String,
        libraryName: String,
        context: Context
    ): URI? {
        val mappedLibraryName = context.mapLibraryName(
            libraryName,
            this.usePlatformPrefix,
            this.usePlatformSuffix
        )

        val formattedPath = context.format(this.pathFormat, mappedLibraryName)

        var libraryPath = ""

        if (!rootPath.startsWith('/'))
            libraryPath += "/"
        libraryPath += rootPath

        if (!rootPath.endsWith('/'))
            libraryPath += "/"
        libraryPath += formattedPath

        return javaClass.getResource(libraryPath)?.toURI()
    }
}
