package me.xtrm.yanl.test

import fr.stardustenterprises.plat4k.EnumArchitecture
import fr.stardustenterprises.plat4k.EnumOperatingSystem
import fr.stardustenterprises.yanl.api.Context

class FakeLinuxContext(arch: EnumArchitecture): Context {
    override val osName = EnumOperatingSystem.LINUX.osName
    override val archIdentifier = arch.identifier
    override val is64Bits = true

    override fun mapLibraryName(
        name: String,
        usePlatformPrefix: Boolean,
        usePlatformSuffix: Boolean
    ) = "lib$name.so"
}

class FakeWindowsContext(arch: EnumArchitecture): Context {
    override val osName = EnumOperatingSystem.WINDOWS.osName
    override val archIdentifier = arch.identifier
    override val is64Bits = true

    override fun mapLibraryName(
        name: String,
        usePlatformPrefix: Boolean,
        usePlatformSuffix: Boolean
    ) = "$name.dll"
}
