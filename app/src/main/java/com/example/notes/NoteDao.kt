package com.example.notes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)  // ignore means if we try to insert duplicate then ignore it
    suspend fun insert(note : Note)   // suspend makes insert task as background threat so it will not run on main threat

    @Delete
    suspend fun delete(note : Note)

    @Query("Select  * from notes_table order by id ASC")
    fun getAllNotes() : LiveData<List<Note>>
}