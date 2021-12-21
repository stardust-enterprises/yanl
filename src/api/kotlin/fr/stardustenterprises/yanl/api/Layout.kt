package fr.stardustenterprises.yanl.api

import java.net.URI

interface Layout {
    fun locateNative(
        rootPath: String,
        libraryName: String,
        context: Context
    ): URI?
}