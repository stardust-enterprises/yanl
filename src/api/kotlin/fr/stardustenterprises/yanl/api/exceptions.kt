package fr.stardustenterprises.yanl.api

class NativeNotFoundException(
    native: String,
    message: String = "Native \"$native\" wasn't found!",
    cause: Throwable? = null
) : Exception(message, cause)
