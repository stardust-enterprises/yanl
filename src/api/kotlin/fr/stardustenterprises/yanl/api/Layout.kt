package fr.stardustenterprises.yanl.api

import java.net.URI

/**
 * Defines a YANL native Layout.
 *
 * @author xtrm
 */
interface Layout {
    /**
     * Locates the given library using the given context.
     *
     * @param rootPath The path to walk through while locating the library.
     * @param libraryName The name of the native to locate.
     * @param context The current platform context.
     */
    @Deprecated(
        "As of 0.5.0, all 'native' functions and functions params have been renamed to 'library', and deprecated stuff is soon to be removed.",
        ReplaceWith("locateLibrary(rootPath, libraryName, context)")
    )
    fun locateNative(
        rootPath: String,
        libraryName: String,
        context: Context
    ) = locateLibrary(rootPath, libraryName, context)

    /**
     * Locates the given library using the given context.
     *
     * @param rootPath The path to walk through while locating the library.
     * @param libraryName The name of the native to locate.
     * @param context The current platform context.
     */
    fun locateLibrary(
        rootPath: String,
        libraryName: String,
        context: Context
    ): URI?
}
