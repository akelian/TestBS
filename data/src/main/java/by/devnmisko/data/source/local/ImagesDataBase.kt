package by.devnmisko.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.devnmisko.data.AppContracts.IMAGES_DATABASE
import by.devnmisko.data.source.local.model.ImageApiEntity
import by.devnmisko.data.source.local.model.RemoteKey

@Database(entities = [ImageApiEntity::class, RemoteKey::class], version = 8, exportSchema = true)
abstract class ImagesDataBase : RoomDatabase(){

    abstract fun imagesDao() : ImagesDao
    abstract fun remoteKeyDao() : RemoteKeyDao

    companion object {
        fun create(applicationContext: Context): ImagesDataBase {
            return Room.databaseBuilder(
                applicationContext,
                ImagesDataBase::class.java,
                IMAGES_DATABASE
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}