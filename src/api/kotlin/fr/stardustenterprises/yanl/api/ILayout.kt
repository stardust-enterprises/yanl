package fr.stardustenterprises.yanl.api

import fr.stardustenterprises.yanl.api.platform.PlatformContext
import java.net.URI

interface ILayout {
    fun locateNative(
        rootPath: String,
        libraryName: String,
        classLoader: ClassLoader,
        platformContext: PlatformContext
    ): URI
}