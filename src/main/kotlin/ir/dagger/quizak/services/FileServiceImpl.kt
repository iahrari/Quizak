package ir.dagger.quizak.services

import ir.dagger.quizak.db.entity.Media
import ir.dagger.quizak.db.entity.MediaType
import ir.dagger.quizak.db.repostiory.MediaRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.security.MessageDigest
import java.util.*
import javax.transaction.Transactional
import kotlin.jvm.Throws

@Service
class FileServiceImpl(
    private val mediaRepository: MediaRepository,
) : FileService {
    private val rootLocation: Path = Paths.get(uploadDir)

    @Transactional
    override fun save(file: MultipartFile, type: MediaType): Optional<Media> {

        //TODO: Exception handling
        if (file.isEmpty) throw IOException("File is empty")
        val message = MessageDigest.getInstance("SHA-256")
        val array = message.digest(file.bytes)
        val digest = array.fold("") { str, it -> str + "%02x".format(it) }

        val folder1 = digest.substring(0, 2)
        val folder2 = digest.substring(2, 4)
        val fileName = digest.substring(4)

        val m = mediaRepository.findByFolder1AndFolder2AndFileName(
            folder1, folder2, fileName
        ).or {
            var media = Media(
                folder1 = folder1,
                folder2 = folder2,
                fileName = fileName,
                type = MediaType.Picture,
                extension = Optional.ofNullable(type.name).filter { it.contains(".") }
                    .map { it.substring(it.lastIndexOf(".")) }.orElse("")
            )
            media = mediaRepository.save(media)
            var destinationFile = rootLocation
                .resolve("${media.folder1}/${media.folder2}")
                .normalize().toAbsolutePath()

            Files.createDirectories(destinationFile)
            destinationFile = destinationFile.resolve(media.fileName)
                .normalize().toAbsolutePath()

            Files.copy(file.inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING)
            Optional.of(media)
        }

        return m
    }

    override fun getMediaOutputStream(media: Media): InputStream {
        val destinationFile = rootLocation
            .resolve("${media.folder1}/${media.folder2}/${media.fileName}")
            .normalize().toAbsolutePath()

        return Files.newInputStream(destinationFile)
    }

    override fun findMediaById(mediaId: String): Media =
        mediaRepository.findById(mediaId).orElseThrow { HttpClientErrorException(HttpStatus.NOT_FOUND) }

    @Bean
    @Throws(IOException::class)
    override fun initialize() = CommandLineRunner {
        Files.createDirectories(rootLocation)
    }

    companion object {
        val uploadDir = "${File("").absolutePath}/uploadDir"
    }
}