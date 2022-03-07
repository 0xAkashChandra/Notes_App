package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(),INotesRVAdapter {
    lateinit var viewModel: NoteViewModel
    lateinit var mAdapter : NotesRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = NotesRVAdapter(this,this)

        recyclerView.adapter = mAdapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {list->
            list?.let{
                mAdapter.update(it)
            }
        })



    }

    override fun onItemClicked(note: Note) {
            viewModel.deleteNote(note)
            Toast.makeText(this,"${note.text} deleted",Toast.LENGTH_LONG).show()
    }

    fun submitData(view: View) {
        val editText = findViewById<EditText>(R.id.input)
        val noteText = editText.text.toString()
        if(noteText.isNotEmpty()){
            viewModel.insertNote(Note(noteText))
            Toast.makeText(this,"$noteText inserted",Toast.LENGTH_LONG).show()
        }
    }


}