package fr.stardustenterprises.yanl.api

import java.net.URI
import java.nio.file.Path

interface Extractor {
    fun extractNative(
        nativeFilename: String,
        resourceUri: URI
    ): Path
}