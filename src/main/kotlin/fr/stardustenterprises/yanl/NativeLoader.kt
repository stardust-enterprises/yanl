package fr.stardustenterprises.yanl

import com.sun.istack.internal.logging.Logger
import fr.stardustenterprises.yanl.api.IExtractor
import fr.stardustenterprises.yanl.api.ILayout
import fr.stardustenterprises.yanl.api.platform.IContext
import fr.stardustenterprises.yanl.platform.PlatformFetcher

class NativeLoader(
    private val root: String,
    private val layout: ILayout,
    private val extractor: IExtractor
) {
    private val platform = Platform

    companion object {
        private val logger = Logger.getLogger(this::class.java)

        @JvmStatic
        fun getDefault() = NativeLoader(
            "/META-INF/natives/",
            NativeLayout.HIERARCHICAL_LAYOUT,
            NativeExtractor(),
            PlatformFetcher::provideContext
        )
    }

    fun loadLibrary(libraryName: String) {
        val uri = layout.locateNative(root, libraryName, context)
        extractor.extractNative(libraryName, uri!!)
    }
}