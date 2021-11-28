package fr.stardustenterprises.yanl.platform

import fr.stardustenterprises.yanl.api.platform.IContext

object PlatformFetcher {
    val architectures = mutableListOf(
        "aarch64" to listOf("arm64", "aarch64"),
        "i686" to listOf("x86", "i386", "i486", "i586", "i686"),
        "x86_64" to listOf("x86_64", "x64", "amd64"),
        "arm" to listOf("arm", "armeabi", "armv7b", "armv7l")
        //FIXME: assure correct arm/arm64 aliases
        //TODO: maybe ppc, mips, riscv?
    )

    fun provideContext(): IContext {
        TODO()
    }
}