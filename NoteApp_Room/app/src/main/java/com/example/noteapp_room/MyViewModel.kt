package com.example.noteapp_room

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application): AndroidViewModel(application) {

    private val noteDao = NotesDatabase.getInstance(application).NotesDao()
    private val notes: LiveData<List<Notes>>

    init {
        val noteDao = NotesDatabase.getInstance(application).NotesDao()
        notes = noteDao.getAllMyNotes()
    }

    fun getNotes(): LiveData<List<Notes>>{
        return notes
    }

    fun addNote(noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.insertNote(Notes(0,noteText))
        }
    }

    fun editNote(noteID: Int, noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.update(noteText,noteID)
        }
    }

    fun deleteNote(noteID: Int, noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.deleteOBJ(Notes(noteID,noteText))
        }
    }
}