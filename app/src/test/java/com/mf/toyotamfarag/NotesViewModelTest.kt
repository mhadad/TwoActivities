package com.mf.toyotamfarag

import com.mf.toyotamfarag.ui.data.db.dao.NotesDAO
import com.mf.toyotamfarag.ui.data.model.NoteModel
import com.mf.toyotamfarag.ui.main.viewmodel.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NotesViewModelTest {
    @Mock
    lateinit var notesDAO: NotesDAO

    lateinit var notesViewModel: NotesViewModel


    @Before
    fun initDependencies(){
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(StandardTestDispatcher())
        this.notesViewModel = NotesViewModel(notesDAO)
    }
    @Test
    fun addNoteTest() = runBlocking {
        notesViewModel.addNote(NoteModel(0, "<5"))
        Assert.assertEquals(notesViewModel.addNoteError.value?.first?.isNotEmpty() ?: true, true)
        notesViewModel.addNote(NoteModel(0, "Valid data"))
        Assert.assertEquals(notesViewModel.addNoteError.value?.first?.isEmpty() ?: true, true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}