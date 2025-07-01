package ar.edu.unlam.scaffoldingandroid3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.scaffoldingandroid3.data.local.dao.MonumentoDao
import ar.edu.unlam.scaffoldingandroid3.data.local.dao.PhotoDao
import ar.edu.unlam.scaffoldingandroid3.data.local.entity.MonumentoEntity
import ar.edu.unlam.scaffoldingandroid3.data.local.entity.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}

@Database(entities = [MonumentoEntity::class], version = 1)
abstract class MonumentoDatabase : RoomDatabase() {
    abstract fun monumentoDao(): MonumentoDao
}
