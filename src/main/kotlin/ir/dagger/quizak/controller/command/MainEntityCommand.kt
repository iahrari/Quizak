package ir.dagger.quizak.controller.command

import org.springframework.web.multipart.MultipartFile

open class MainEntityCommand(): BaseEntityCommand() {
    var name: String? = null
    var description: String? = null
    var imageFile: MultipartFile? = null
    var mediaId: String? = null

    constructor(name: String?, description: String?) : this() {
        this.name = name
        this.description = description
    }
}