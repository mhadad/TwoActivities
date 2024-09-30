package com.mf.toyotamfarag.ui.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mf.toyotamfarag.ui.data.db.dao.NotesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun getDB(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "Toyota_CC").addMigrations(
            MIGRATION_100_101).build()
    }

    @Provides
    fun getNotesDao(db: AppDatabase): NotesDAO {
        return db.notesDao()
    }
}

val MIGRATION_100_101 = object : Migration(100, 1) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE Notes")
        database.execSQL("CREATE TABLE Notes (id INTEGER PRIMARY KEY NOT NULL, note TEXT)")
    }
}