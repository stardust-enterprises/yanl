package fr.stardustenterprises.yanl

import fr.stardustenterprises.yanl.api.Extractor
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path

class TempExtractor : Extractor {
    private val tempDir = Files.createTempDirectory("yanl_natives")

    override fun extractNative(
        nativeFilename: String,
        resourceUri: URI
    ): Path {
        val path = tempDir.resolve(nativeFilename)

        if (!Files.exists(path)) {
            Files.copy(resourceUri.toURL().openStream(), path)
        }

        return path
    }
}
