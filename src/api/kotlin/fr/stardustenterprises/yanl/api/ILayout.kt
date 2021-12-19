package fr.stardustenterprises.yanl.api

import java.net.URI

interface ILayout {
    fun locateNative(
        rootPath: String,
        libraryName: String
    ): URI?
}