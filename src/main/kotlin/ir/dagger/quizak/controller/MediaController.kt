package ir.dagger.quizak.controller

import ir.dagger.quizak.services.FileService
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/media")
class MediaController(
    private val fileService: FileService
) {
    @GetMapping("/{mediaId}")
    fun getMedia(@PathVariable mediaId: String, response: HttpServletResponse){
        val media = fileService.findMediaById(mediaId)
        val file = fileService.getMediaOutputStream(media)
        response.contentType = media.type.mimeType
        IOUtils.copy(file, response.outputStream)

    }
}