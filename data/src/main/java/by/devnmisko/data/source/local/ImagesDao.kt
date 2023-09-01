package by.devnmisko.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import by.devnmisko.data.source.local.model.ImageApiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(items: List<ImageApiEntity>)

    @Query("DELETE from ImageApiEntity")
    suspend fun deleteAll()

    @Query("SELECT * from ImageApiEntity")
    fun allImagesEntries(): Flow<List<ImageApiEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(entry: ImageApiEntity)

    @Query("SELECT * from ImageApiEntity WHERE id = :id")
    fun imageEntryById(id: Int): Flow<ImageApiEntity?>

    @Query("SELECT * FROM ImageApiEntity ")
    fun pagingSource(): PagingSource<Int, ImageApiEntity>

    @Transaction
    suspend fun replaceDataset(newEntries: List<ImageApiEntity>) {
        deleteAll()
        addAll(newEntries)
    }
}