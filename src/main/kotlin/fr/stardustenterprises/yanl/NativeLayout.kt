package fr.stardustenterprises.yanl

import fr.stardustenterprises.yanl.api.Context
import fr.stardustenterprises.yanl.api.Layout
import java.net.URI

data class NativeLayout(
    private val pathFormat: String,
    private val usePlatformPrefix: Boolean = true,
    private val usePlatformSuffix: Boolean = true
) : Layout {
    companion object {
        @JvmStatic
        val FLAT_LAYOUT = NativeLayout("{name}")

        @JvmStatic
        val HIERARCHICAL_LAYOUT = NativeLayout("{os}/{arch}/{name}")
    }

    override fun locateNative(
        rootPath: String,
        libraryName: String,
        context: Context
    ): URI? {
        val name = context.mapLibraryName(libraryName, this.usePlatformPrefix, this.usePlatformSuffix)
        val formattedPath = context.format(this.pathFormat, name)

        val nativePath =
            (if (rootPath.startsWith('/')) "" else "/") + rootPath + (if (rootPath.endsWith('/')) "" else "/") + formattedPath

        return NativeLayout::class.java.getResource(nativePath)?.toURI()
    }
}