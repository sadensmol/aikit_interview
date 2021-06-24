package me.sadensmol.controller

import me.sadensmol.model.Photo
import me.sadensmol.service.PhotosService
import org.springframework.web.bind.annotation.*


/**
 *
 * /photos
 * /posts
 *
 * photos : id
 * posts : id
 *
 */
@RestController
class PostsController(private val photosService: PhotosService) {

    @GetMapping("/photos")
    suspend fun getAllPhotos(): List<Photo> {
        return photosService.getPhotos()
    }

    @GetMapping("/photos/{id}")
    suspend fun getPhotoById(@PathVariable id:Int): Photo? {
        return photosService.getPhotoById(id)
    }

    @GetMapping("/photos/{id}/title")
    suspend fun getTitleById(@PathVariable id:Int): String {
        return photosService.getPhotoTitleById(id)
    }

    @PostMapping("/photo")
    suspend fun createPhoto(@RequestBody photo:Photo): Photo {
        return photosService.createPhoto(photo)
    }

    @PutMapping("/photo")
    suspend fun updatePhoto(@RequestBody  photo:Photo): Photo {
        return photosService.updatePhoto(photo)
    }

    @DeleteMapping("/photos/{id}")
    suspend fun deleteById(@PathVariable id:Int): Int {
        return photosService.deletePhotoById(id)
    }
}