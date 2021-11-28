package fr.stardustenterprises.yanl

import fr.stardustenterprises.yanl.api.ILayout
import fr.stardustenterprises.yanl.api.platform.IContext
import java.net.URI

data class NativeLayout(
    private val pathFormat: String,
    private val usePlatformPrefix: Boolean,
    private val usePlatformExtension: Boolean
): ILayout {
    companion object {
        @JvmStatic
        val FLAT_LAYOUT = NativeLayout(
            "{name}",
            usePlatformPrefix = true,
            usePlatformExtension = true
        )

        @JvmStatic
        val HIERARCHICAL_LAYOUT = NativeLayout(
            "{os}/{arch}/{name}",
            usePlatformPrefix = true,
            usePlatformExtension = true
        )
    }

    override fun locateNative(
        rootPath: String,
        libraryName: String,
        context: IContext
    ): URI? {
        val name = context.mapLibraryName(libraryName, this.usePlatformPrefix, this.usePlatformExtension)
        val formattedPath = context.mapLibraryPath(this.pathFormat, name)

        val nativePath = (if (rootPath.startsWith('/')) "" else "/") +
                rootPath +
                (if (rootPath.endsWith('/')) "" else "/") +
                formattedPath

        class WAClass
        return WAClass::class.java.classLoader.getResource(nativePath)?.toURI()
    }
}