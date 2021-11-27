package fr.stardustenterprises.yanl

class NativeLoader(
    private val layout: NativeLayout
) {

    companion object {
        @JvmStatic
        fun getDefault() = NativeLoader(
            NativeLayout.HIERARCHICAL_LAYOUT,
            //NativeExtractor
        )
    }

    fun loadLibrary(libraryName: String) {

    }

}