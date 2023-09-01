package by.devnmisko.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.devnmisko.data.source.local.model.RemoteKey

@Dao
interface RemoteKeyDao {

  @Query("SELECT * FROM remote_keys WHERE id =:id")
  suspend fun getRemoteKeys(id: Int): RemoteKey

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun addAllRemoteKeys(remoteKeys: List<RemoteKey>)

  @Query("DELETE FROM remote_keys")
   fun deleteAllRemoteKeys()

}