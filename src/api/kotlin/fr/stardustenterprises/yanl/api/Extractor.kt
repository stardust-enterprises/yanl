package fr.stardustenterprises.yanl.api

import java.net.URI
import java.nio.file.Path

/**
 * Defines a YANL native extractor.
 *
 * @author xtrm
 */
interface Extractor {
    @Deprecated(
        "As of 0.5.0, all 'native' functions and functions params have been renamed to 'library', and deprecated stuff is soon to be removed.",
        ReplaceWith("extractLibrary(libraryFilename, resourceUri)")
    )
    fun extractNative(
        libraryFile: String,
        resourceUri: URI
    ): Path = extractLibrary(libraryFile, resourceUri)

    /**
     * Extracts the given library to the given URI.
     *
     * @param libraryFile The name of the file to extract the native to.
     * @param resourceUri The URI pointing to where the native resides.
     *
     * @return The path where the library was extracted to.
     */
    fun extractLibrary(
        libraryFile: String,
        resourceUri: URI
    ): Path
}
