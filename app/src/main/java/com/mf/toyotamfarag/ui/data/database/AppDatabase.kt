package com.mf.toyotamfarag.ui.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mf.toyotamfarag.ui.data.db.dao.NotesDAO
import com.mf.toyotamfarag.ui.data.model.NoteModel

@Database(entities = [NoteModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun notesDao(): NotesDAO
}