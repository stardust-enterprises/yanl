package fr.stardustenterprises.yanl.api

import fr.stardustenterprises.yanl.api.platform.IContext
import java.net.URI

interface ILayout {
    fun locateNative(
        rootPath: String,
        libraryName: String,
        context: IContext
    ): URI?
}