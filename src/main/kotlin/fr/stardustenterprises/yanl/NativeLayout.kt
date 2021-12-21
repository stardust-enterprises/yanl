package fr.stardustenterprises.yanl

import fr.stardustenterprises.yanl.api.*
import java.net.URI

data class NativeLayout(
    private val pathFormat: String,
    private val usePlatformPrefix: Boolean = true,
    private val usePlatformSuffix: Boolean = true
) : Layout {
    companion object {
        @JvmStatic
        val FLAT_LAYOUT = NativeLayout(NAME_FORMAT)

        @JvmStatic
        val HIERARCHICAL_LAYOUT = NativeLayout("$OS_FORMAT/$ARCH_FORMAT/$NAME_FORMAT")
    }

    override fun locateNative(
        rootPath: String,
        libraryName: String,
        context: Context
    ): URI? {
        val name = context.mapLibraryName(libraryName, this.usePlatformPrefix, this.usePlatformSuffix)
        val formattedPath = context.format(this.pathFormat, name)

        var nativePath = ""

        if (!rootPath.startsWith('/'))
            nativePath += "/"
        nativePath += rootPath

        if (!rootPath.endsWith('/'))
            nativePath += "/"
        nativePath += formattedPath

        return NativeLayout::class.java.getResource(nativePath)?.toURI()
    }
}
