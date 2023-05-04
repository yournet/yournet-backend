package com.yournet.yournet.controller


import com.yournet.yournet.service.StorageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class StorageController(
    private val storageService: StorageService
) {
    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") files: List<MultipartFile>): ResponseEntity<*>{
        return ResponseEntity.ok(storageService.uploadFile(files))
    }
}