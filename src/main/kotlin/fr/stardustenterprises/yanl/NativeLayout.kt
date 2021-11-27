package fr.stardustenterprises.yanl

import fr.stardustenterprises.yanl.api.ILayout
import fr.stardustenterprises.yanl.api.platform.PlatformContext
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
            usePlatformPrefix = false,
            usePlatformExtension = true
        )
    }

    override fun locateNative(
        rootPath: String,
        libraryName: String,
        classLoader: ClassLoader,
        platformContext: PlatformContext
    ): URI {
        val name = platformContext.populateName(libraryName, this.usePlatformPrefix, this.usePlatformExtension)
        val formattedPath = platformContext.populatePath(this.pathFormat, name)

        val nativePath = (if (rootPath.startsWith('/')) "" else "/") +
                rootPath +
                (if (rootPath.endsWith('/')) "" else "/") +
                formattedPath

        return classLoader.getResource(nativePath)?.toURI() ?: throw IllegalArgumentException()
    }
}