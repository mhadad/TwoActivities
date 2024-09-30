package com.mf.toyotamfarag.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mf.toyotamfarag.ui.data.db.dao.NotesDAO
import com.mf.toyotamfarag.ui.data.model.NoteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor( private val notesDao: NotesDAO): ViewModel()  {
    val addNoteError : MutableLiveData<Pair<String, String>> =  MutableLiveData<Pair<String, String>>()

    var notesList: StateFlow<List<NoteModel>> = notesDao.getAllNotes().stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = emptyList())

    // add data to db function
    fun addNote(noteObj: NoteModel){
        viewModelScope.launch {
            if(isValidNoteDescription(noteObj = noteObj)) {
                notesDao.insertOne(noteObj)
            }
        }
    }

    // is valid note, Valid notes are between 5-250 chars
    private fun isValidNoteDescription(noteObj: NoteModel): Boolean{
        if(noteObj.note.trim().length !in IntRange(5, 250)){
            viewModelScope.launch {
                addNoteError.postValue(
                    Pair(
                        "Description length is ${noteObj.note.trim().length}",
                        noteObj.note
                    )
                )
            }
            return false
        }
        else
            return true
    }
}