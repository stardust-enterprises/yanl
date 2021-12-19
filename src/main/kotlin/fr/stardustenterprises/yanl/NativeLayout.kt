package fr.stardustenterprises.yanl

import fr.stardustenterprises.plat4k.EnumOperatingSystem
import fr.stardustenterprises.plat4k.Platform
import fr.stardustenterprises.yanl.api.ILayout
import java.net.URI

data class NativeLayout(
    private val pathFormat: String,
    private val usePlatformPrefix: Boolean,
    private val usePlatformExtension: Boolean
) : ILayout {
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
        libraryName: String
    ): URI? {
        val platform = Platform.current
        val os = platform.operatingSystem

        val name = os.mapLibraryName(libraryName, this.usePlatformPrefix, this.usePlatformExtension)
        val formattedPath = platform.mapLibraryPath(this.pathFormat, name)

        val nativePath = (if (rootPath.startsWith('/')) "" else "/") +
                rootPath +
                (if (rootPath.endsWith('/')) "" else "/") +
                formattedPath

        class WAClass
        return WAClass::class.java.classLoader.getResource(nativePath)?.toURI()
    }
}

fun EnumOperatingSystem.mapLibraryName(
    libraryName: String,
    usePlatformPrefix: Boolean,
    usePlatformExtension: Boolean
): String =
    (if (usePlatformPrefix) nativePrefix else "") + libraryName + (if (usePlatformExtension) nativeSuffix else "")

const val OS = "{os}"
const val ARCH = "{arch}"
const val NAME = "{name}"

fun Platform.mapLibraryPath(string: String, name: String): String =
    string.replace(OS, operatingSystem.osName, true)
        .replace(ARCH, architecture.identifier, true)
        .replace(NAME, name, true)