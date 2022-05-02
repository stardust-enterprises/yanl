package me.xtrm.yanl.test

import fr.stardustenterprises.plat4k.EnumArchitecture
import fr.stardustenterprises.plat4k.EnumOperatingSystem
import fr.stardustenterprises.yanl.api.Context

/**
 * A fake Linux context made in order to unit-test the project.
 */
class FakeLinuxContext(arch: EnumArchitecture): Context {
    override val osName = EnumOperatingSystem.LINUX.osName
    override val archIdentifiers = arch.aliases
    override val is64Bits = true

    override fun mapLibraryName(
        name: String,
        usePlatformPrefix: Boolean,
        usePlatformSuffix: Boolean
    ) = "lib$name.so"  // On Linux, the libraries are formatted like this.
}

/**
 * A fake Windows context made in order to unit-test the project.
 */
class FakeWindowsContext(arch: EnumArchitecture): Context {
    override val osName = EnumOperatingSystem.WINDOWS.osName
    override val archIdentifiers = arch.aliases
    override val is64Bits = true

    override fun mapLibraryName(
        name: String,
        usePlatformPrefix: Boolean,
        usePlatformSuffix: Boolean
    ) = "$name.dll"  // On Windows, the libraries are formatted like that.
}
