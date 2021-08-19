package ir.dagger.quizak.controller.command

import org.springframework.web.multipart.MultipartFile

class MediaData {
    var file: MultipartFile? = null
    var mediaId: String? = null
}
