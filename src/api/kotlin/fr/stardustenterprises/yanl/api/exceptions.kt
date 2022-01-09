package fr.stardustenterprises.yanl.api

/**
 * Defines an exception used to warn the user about a missing native.
 *
 * @author xtrm
 */
class NativeNotFoundException(
    /**
     * The path to the target native.
     */
    native: String,

    /**
     * The message to show as an exception.
     */
    message: String = "Native \"$native\" wasn't found!",

    /**
     * Another throwable defining the cause of that Exception. Generally null.
     */
    cause: Throwable? = null
) : Exception(message, cause)

/**
 * Defines an exception used to warn the user about a platform being
 * unsupported.
 *
 * @author lambdagg
 */
class UnsupportedPlatformException(
    /**
     * The message to show as an exception.
     */
    extra: String = "No extra information provided.",

    /**
     * Another throwable defining the cause of that Exception. Generally null.
     */
    cause: Throwable? = null
) : Exception("Unsupported platform. ($extra)", cause)
