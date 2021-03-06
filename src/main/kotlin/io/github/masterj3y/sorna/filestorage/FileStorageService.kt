package io.github.masterj3y.sorna.filestorage

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*


@Service
class FileStorageService {

    fun init() {
        try {

            roots.values.forEach {
                if (Files.exists(it).not())
                    Files.createDirectory(it)
            }

        } catch (e: IOException) {
            throw RuntimeException("could not initialise directory for uploads!")
        }
    }

    fun save(path: Path, file: MultipartFile) {
        try {

            Files.copy(file.inputStream, path)
        } catch (e: Exception) {
            throw RuntimeException("could not store the file, error: ${e.message}")
        }
    }

    fun load(path: String, filename: String): Resource {
        return try {
            val file: Path = requireNotNull(roots[path]).resolve(filename)
            val resource: Resource = UrlResource(file.toUri())
            if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw RuntimeException("Could not read the file!")
            }
        } catch (e: MalformedURLException) {
            throw RuntimeException("Error: " + e.message)
        }
    }

    fun deleteFile(vararg path: Path) = path.forEach {
        FileSystemUtils.deleteRecursively(it)
    }

    companion object {

        const val USER_PROFILE = "user-profile"
        const val AD_PICTURE = "ad-picture"

        private val roots: HashMap<String, Path> = hashMapOf(
                USER_PROFILE to Paths.get(USER_PROFILE),
                AD_PICTURE to Paths.get(AD_PICTURE)
        )

        fun generateFileName(directory: String): Path {
            val dir: Path = requireNotNull(roots[directory])
            val fileName = Random().nextInt(999999999).toString()
            return dir.resolve(fileName)
        }
    }
}