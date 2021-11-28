package fr.stardustenterprises.yanl.api.platform

interface IContext {

    fun mapLibraryPath(string: String, name: String): String

    fun mapLibraryName(
        libraryName: String,
        usePlatformPrefix: Boolean,
        usePlatformExtension: Boolean
    ): String

}
