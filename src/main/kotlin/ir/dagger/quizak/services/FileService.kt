package ir.dagger.quizak.services

import ir.dagger.quizak.db.entity.Media
import ir.dagger.quizak.db.entity.MediaType
import org.springframework.boot.CommandLineRunner
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.util.*

interface FileService {
    fun save(file: MultipartFile, type: MediaType): Optional<Media>
    fun initialize(): CommandLineRunner
    fun getMediaOutputStream(media: Media): InputStream
    fun findMediaById(mediaId: String): Media
}