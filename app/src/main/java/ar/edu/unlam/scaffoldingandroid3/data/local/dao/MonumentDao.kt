package ar.edu.unlam.scaffoldingandroid3.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.unlam.scaffoldingandroid3.data.local.entity.MonumentoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MonumentoDao {
    @Query("SELECT * FROM monumentos")
    fun getAll(): Flow<List<MonumentoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(monumento: MonumentoEntity)
}
