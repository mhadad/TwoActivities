package com.mf.toyotamfarag.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mf.toyotamfarag.R
import com.mf.toyotamfarag.ui.data.model.NoteModel
import com.mf.toyotamfarag.ui.main.view.AddNoteActivity
import com.mf.toyotamfarag.ui.main.viewmodel.NotesViewModel
import com.mf.toyotamfarag.ui.theme.ToyotaMFaragTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(R.layout.main_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val composeView = findViewById<ComposeView>(R.id.composeView)
        enableEdgeToEdge()
        composeView.setContent {
            ToyotaMFaragTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    showNotesList(innerPadding)
                }
            }
        }
    }


@Composable
 fun showNotesList(innerPaddingValues: PaddingValues, notesViewModel: NotesViewModel = hiltViewModel<NotesViewModel>()) {
    val notesList  : List<NoteModel> by notesViewModel.notesList.collectAsStateWithLifecycle()
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(innerPaddingValues)) {
        LazyColumn {
            itemsIndexed(notesList) { _, note ->
                Box {
                    Card(
                        modifier = Modifier
                            .background(Color.Black)
                            .padding(15.dp)
                            .fillMaxHeight(0.9f)
                            .fillParentMaxWidth(0.9f)
                    ) {
                        Text("${note.id}: \n${note.note}", modifier = Modifier.padding(5.dp), fontWeight = FontWeight.Bold, color = Color.White, fontSize = TextUnit(25f, TextUnitType.Sp))
                    }
                    HorizontalDivider(
                        thickness = 3.dp, color = Color.Cyan, modifier = Modifier
                            .padding(3.dp)
                            .fillParentMaxWidth(0.8f)
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }
        FloatingActionButton(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(25.dp),onClick = {
            val intent = Intent(applicationContext, AddNoteActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            applicationContext.startActivity(intent)
        }) {
            Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add a note")
        }
    }
}
}