package me.xtrm.yanl.test

import fr.stardustenterprises.plat4k.EnumArchitecture
import fr.stardustenterprises.plat4k.EnumOperatingSystem
import fr.stardustenterprises.yanl.api.Context

class FakeLinuxContext(val arch: EnumArchitecture): Context {
    override fun getOperatingSystem() = EnumOperatingSystem.LINUX.osName

    override fun getArchitecture() = arch.identifier

    override fun is64Bit() = true

    override fun mapLibraryName(name: String, usePlatformPrefix: Boolean, usePlatformSuffix: Boolean) =
        "lib$name.so"
}

class FakeWindowsContext(val arch: EnumArchitecture): Context {
    override fun getOperatingSystem() = EnumOperatingSystem.WINDOWS.osName

    override fun getArchitecture() = arch.identifier

    override fun is64Bit() = true

    override fun mapLibraryName(name: String, usePlatformPrefix: Boolean, usePlatformSuffix: Boolean) =
        "$name.dll"
}