package fr.stardustenterprises.yanl

import fr.stardustenterprises.plat4k.EnumArchitecture
import fr.stardustenterprises.plat4k.EnumOperatingSystem
import fr.stardustenterprises.plat4k.Platform
import fr.stardustenterprises.yanl.api.Context

open class PlatformContext : Context {
    val platform = Platform.current

    init {
        if (
            platform.operatingSystem == EnumOperatingSystem.UNKNOWN ||
            platform.architecture == EnumArchitecture.UNKNOWN
        ) {
            throw RuntimeException(
                "Could not find platform OS and/or Arch!"
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
