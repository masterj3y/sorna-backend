package io.github.masterj3y.sorna.features.ad.ad_picture

import io.github.masterj3y.sorna.filestorage.FileStorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ad-picture")
class AdPictureController
@Autowired
constructor(private val fileStorageService: FileStorageService) {

    @GetMapping("/{pic}")
    private fun downloadAdPicture(@PathVariable("pic") pic: String): ResponseEntity<Resource> {
        val resource = fileStorageService.load(FileStorageService.AD_PICTURE, pic)
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.filename + "\"").body(resource)
    }
}