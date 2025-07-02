package ar.edu.unlam.scaffoldingandroid3.data.repository

import ar.edu.unlam.scaffoldingandroid3.data.local.dao.PhotoDao
import ar.edu.unlam.scaffoldingandroid3.data.local.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoRepository
    @Inject
    constructor(
        private val photoDao: PhotoDao,
    ) {
        suspend fun insertPhoto(photo: PhotoEntity) = photoDao.insert(photo)

        fun getAllPhotos(): Flow<List<PhotoEntity>> = photoDao.getAllPhotos()
    }
