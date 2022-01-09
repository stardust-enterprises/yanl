package fr.stardustenterprises.yanl

import fr.stardustenterprises.plat4k.EnumArchitecture
import fr.stardustenterprises.plat4k.EnumOperatingSystem
import fr.stardustenterprises.plat4k.Platform
import fr.stardustenterprises.yanl.api.Context
import fr.stardustenterprises.yanl.api.UnsupportedPlatformException

/**
 * The YANL platform context implementation, following the [Context] interface.
 *
 * @author xtrm
 * @see Context
 */
open class PlatformContext : Context {
    /**
     * The [plat4k.Platform](fr.stardustenterprises.plat4k.Platform) associated
     * to this context.
     *
     * @see Platform
     */
    val platform = Platform.current

    init {
        val (operatingSystem, architecture) = platform

        if (
            operatingSystem == EnumOperatingSystem.UNKNOWN ||
            architecture == EnumArchitecture.UNKNOWN
        ) {
            throw UnsupportedPlatformException(
                "OS: ${operatingSystem.osName}, arch: ${architecture.identifier}"
            )
        }
    }

    override val osName = platform.operatingSystem.osName
    override val archIdentifier = platform.architecture.identifier
    override val is64Bits = platform.architecture.bits == 64.toShort()

    override fun mapLibraryName(
        name: String,
        usePlatformPrefix: Boolean,
        usePlatformSuffix: Boolean
    ): String {
        var libName = name
        val os = platform.operatingSystem

        if (usePlatformPrefix) {
            libName = os.nativePrefix + libName
        }

        if (usePlatformSuffix) {
            libName += os.nativeSuffix
        }

        return libName
    }
}
