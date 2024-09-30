package com.mf.toyotamfarag.ui.main.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mf.toyotamfarag.R
import com.mf.toyotamfarag.ui.data.model.NoteModel
import com.mf.toyotamfarag.ui.main.viewmodel.NotesViewModel
import com.mf.toyotamfarag.ui.theme.ToyotaMFaragTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteActivity : ComponentActivity(R.layout.add_note_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val composeView = findViewById<ComposeView>(R.id.composeView)
        enableEdgeToEdge()
        composeView.setContent {
            ToyotaMFaragTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    showScreen(innerPadding)
                }
            }
        }
    }

    @Composable
    fun showScreen(innerPaddingValues: PaddingValues, notesViewModel: NotesViewModel = hiltViewModel<NotesViewModel>()){
        var descriptionVal by rememberSaveable {
            mutableStateOf("")
        }
        val addNotesError by notesViewModel.addNoteError.observeAsState()
        val scrollState = rememberScrollState()
        Box(modifier = Modifier.fillMaxSize(1.0f)){
        Column (modifier = Modifier.padding(innerPaddingValues).align(Alignment.Center).verticalScroll(scrollState)) {
            addNotesError?.let { if (it.first.isNotEmpty()) Text(text = "${it.first} and the data entered were ${it.second}") }
            OutlinedTextField(
                minLines = 15, value = descriptionVal,
                onValueChange = { newText ->
                    descriptionVal = newText
                    notesViewModel.addNoteError.postValue(Pair("", ""))
                },
                modifier = Modifier.fillMaxSize(0.8f).height(300.dp),
            )
            TextButton(onClick = {
                notesViewModel.addNote(noteObj = NoteModel(0, descriptionVal))
                if (addNotesError?.first?.isEmpty() ?: true && descriptionVal.trim()
                        .length in IntRange(5, 250)) {
                    finish()
                }
            }) {
                Row {
                    Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "Submit note")
                    Text(text = "Save")
                }
            }
        }
        }
    }
}