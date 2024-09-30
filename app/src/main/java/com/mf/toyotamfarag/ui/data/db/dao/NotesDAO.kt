package com.mf.toyotamfarag.ui.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mf.toyotamfarag.ui.data.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(noteObj: NoteModel);

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getAllNotes(): Flow<List<NoteModel>>;


}