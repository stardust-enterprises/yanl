package fr.stardustenterprises.yanl

import fr.stardustenterprises.yanl.api.Context
import fr.stardustenterprises.yanl.api.Extractor
import fr.stardustenterprises.yanl.api.Layout
import fr.stardustenterprises.yanl.api.NativeNotFoundException

class NativeLoader private constructor(
    private val root: String,
    private val layout: Layout,
    private val extractor: Extractor,
    private val context: Context
) {
    
    fun loadLibrary(libraryName: String, isOptional: Boolean = false) {
        var uri = layout.locateNative(root, libraryName, context)
        if (context.is64Bit()) {
            val secondary = layout.locateNative(root, libraryName + "64", context)
            if (secondary != null) {
                uri = secondary
            }
        }

        if (uri == null) {
            if (!isOptional) {
                throw NativeNotFoundException(libraryName)
            }
            return
        }

        val path = extractor.extractNative(libraryName, uri)
        System.loadLibrary(path.toAbsolutePath().toString())
    }

    data class Builder(
        var root: String = "/META-INF/natives",
        var layout: Layout = NativeLayout.HIERARCHICAL_LAYOUT,
        var extractor: Extractor = TempExtractor(),
        var context: Context = PlatformContext()
    ) {
        fun root(root: String) = apply { this.root = root }

        fun layout(layout: Layout) = apply { this.layout = layout }

        fun layout(
            pattern: String,
            usePrefix: Boolean = true,
            useSuffix: Boolean = true
        ) = layout(NativeLayout(pattern, usePrefix, useSuffix))

        fun extractor(extractor: Extractor) = apply { this.extractor = extractor }

        fun context(context: Context) = apply { this.context = context }

        fun build() = NativeLoader(root, layout, extractor, context)
    }
}