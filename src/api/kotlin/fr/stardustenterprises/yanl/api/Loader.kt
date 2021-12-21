package fr.stardustenterprises.yanl.api

interface Loader {
    fun loadLibrary(
        libraryName: String,
        isOptional: Boolean = false
    )
}
