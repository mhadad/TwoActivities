package com.mf.toyotamfarag.ui.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")
public class NoteModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String note;

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {

        this.note = note;
    }


    public NoteModel(int id, String note){
        this.id = id;
        this.note = note;
    }
}
