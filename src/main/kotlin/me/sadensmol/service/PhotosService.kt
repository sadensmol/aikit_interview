package me.sadensmol.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import me.sadensmol.model.Photo
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service


@Service
class PhotosService(@Value("classpath:photos.json") private val photosResource: Resource,
private val objectMapper: ObjectMapper) {

    private fun getPhotosAsString() = photosResource.file.readText()

    private var photos:List<Photo> = mutableListOf()

    private fun parsePhotos():List<Photo> {
        photos = objectMapper.readValue<List<Photo>>(getPhotosAsString())
        return photos
    }

    fun getPhotos() : List<Photo> {
        if (photos.isEmpty()) return parsePhotos()
        else return photos
    }

    fun getPhotoById(id: Int): Photo? {
        return getPhotos().firstOrNull { it.id ==id }
    }

    fun getPhotoTitleById(id: Int): String {
        return getPhotoById(id)?.title?:throw IllegalArgumentException("Photo isn't found!")
    }

    fun createPhoto(photo:Photo): Photo {
        if (getPhotoById(photo.id) !=null) throw  IllegalArgumentException("Photo is already exists")
        photos = photos + photo
        return photo
    }

    fun updatePhoto(photo:Photo): Photo {
        getPhotoById(photo.id)?:throw IllegalArgumentException("There is no such photo")
        deletePhotoById(photo.id)
        createPhoto(photo)
        return photo
    }

    fun deletePhotoById(id: Int): Int {
        val l = getPhotos().size
        photos = getPhotos().filter{it.id != id}
        return l - getPhotos().size
    }


}