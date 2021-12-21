package fr.stardustenterprises.yanl

import fr.stardustenterprises.yanl.api.Extractor
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path

class TempExtractor : Extractor {
    private val tempHolder = Files.createTempDirectory("yanl_natives")

    override fun extractNative(
        nativeFilename: String,
        resourceUri: URI
    ): Path {
        val path = tempHolder.resolve(nativeFilename)

        if(!Files.exists(path)) {
            val url = resourceUri.toURL()
            val inputStream = url.openStream()

            Files.copy(inputStream, path)
        }

        return path
    }
}