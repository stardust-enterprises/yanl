package fr.stardustenterprises.yanl

import fr.stardustenterprises.yanl.api.Extractor
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path

/**
 * The YANL native extractor implementation, following the [Extractor]
 * interface.
 *
 * @author xtrm
 * @see Extractor
 */
class TempExtractor : Extractor {
    /**
     * The temporary directory where the YANL natives will reside.
     */
    private val tempDir = Files.createTempDirectory("yanl_natives")

    override fun extractLibrary(
        libraryFile: String,
        resourceUri: URI
    ): Path {
        val resPath = resourceUri.toString()
        val index = resPath.lastIndexOf('.')
        val ext = if(index == -1) "" else resPath.substring(index)
        val path = tempDir.resolve(libraryFile + ext)

        if (!Files.exists(path)) {
            Files.copy(resourceUri.toURL().openStream(), path)
        }

        return path
    }
}
