package fr.stardustenterprises.yanl

import fr.stardustenterprises.yanl.api.*

/**
 * The YANL native loader implementation, following the [Loader] interface.
 *
 * @author xtrm
 * @see Loader
 */
class NativeLoader private constructor(
    private val root: String,
    private val layout: Layout,
    private val extractor: Extractor,
    private val context: Context,
) : Loader {
    override fun loadLibrary(libraryName: String, isOptional: Boolean) {
        var uri = layout.locateLibrary(root, libraryName, context)
        if (context.is64Bits) {
            val secondary = layout.locateLibrary(root, libraryName + "64", context)
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

        val path = extractor.extractLibrary(libraryName, uri)
        System.load(path.toAbsolutePath().toString())
    }

    /**
     * Defines the [NativeLoader] builder.
     */
    data class Builder(
        /**
         * The `root` value, following the [NativeLoader.root] definition.
         */
        var root: String = "/META-INF/natives",

        /**
         * The `layout` value, following the [NativeLoader.layout] definition.
         */
        var layout: Layout = NativeLayout.HIERARCHICAL_LAYOUT,

        /**
         * The `extractor` value, following the [NativeLoader.extractor]
         * definition.
         */
        var extractor: Extractor = TempExtractor(),

        /**
         * The `context` value, following the [NativeLoader.context]
         * definition.
         */
        var context: Context = PlatformContext(),
    ) {
        /**
         * Sets the [root] value to the given one.
         *
         * @param root The new [root] value.
         *
         * @return This builder.
         */
        fun root(root: String): Builder =
            apply { this.root = root }

        /**
         * Sets the [layout] value to the given one.
         *
         * @param layout The new [layout] value.
         *
         * @return This builder.
         */
        fun layout(layout: Layout): Builder =
            apply { this.layout = layout }

        /**
         * Sets the [layout] value to the given one, but create it using the
         * constructor.
         *
         * @param pattern The pattern to use following the [NativeLayout]
         *                constructor.
         * @param usePrefix Whether to use the platform prefix, following the
         *                  [NativeLayout] constructor.
         * @param useSuffix Whether to use the platform suffix, following the
         *                  [NativeLayout] constructor.
         *
         * @return This builder.
         *
         * @see NativeLayout
         */
        fun layout(
            pattern: String,
            usePrefix: Boolean = true,
            useSuffix: Boolean = true,
        ): Builder =
            layout(NativeLayout(pattern, usePrefix, useSuffix))

        /**
         * Sets the [extractor] value to the given one.
         *
         * @param extractor The new [extractor] value.
         *
         * @return This builder.
         */
        fun extractor(extractor: Extractor): Builder =
            apply { this.extractor = extractor }

        /**
         * Sets the [context] value to the given one.
         *
         * @param context The new [context] value.
         *
         * @return This builder.
         */
        fun context(context: Context): Builder =
            apply { this.context = context }

        /**
         * Builds the [NativeLoader].
         *
         * @return The built [NativeLoader].
         */
        fun build(): NativeLoader =
            NativeLoader(root, layout, extractor, context)
    }
}
