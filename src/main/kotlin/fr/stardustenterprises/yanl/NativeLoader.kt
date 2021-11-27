package fr.stardustenterprises.yanl

import fr.stardustenterprises.yanl.api.IExtractor
import fr.stardustenterprises.yanl.api.ILayout

class NativeLoader(
    private val layout: ILayout,
    private val extractor: IExtractor
) {

    companion object {
        @JvmStatic
        fun getDefault() = NativeLoader(
            NativeLayout.HIERARCHICAL_LAYOUT,
            NativeExtractor
        )
    }

    fun loadLibrary(libraryName: String) {

    }
}