package fr.stardustenterprises.yanl.api

/**
 * Defines a YANL native loader.
 *
 * @author xtrm
 */
interface Loader {
    /**
     * Actually loads the library into the classpath.
     *
     * @param libraryName The name of the library.
     * @param isOptional Whether the current library is optional.
     */
    fun loadLibrary(
        libraryName: String,
        isOptional: Boolean = false
    )
}
