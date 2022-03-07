package com.example.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) :AndroidViewModel(application) {

    val allNotes : LiveData<List<Note>>
    private val repository : NoteRespository

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NoteRespository(dao)
        allNotes = repository.allNotes
    }

    // as the delete function in repository is a suspend funtion so we cannot directly call it so we can wither call it via suspend function or have to make it via coroutine
    // so we calling via coroutine
    fun deleteNote(note :Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun insertNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

}


