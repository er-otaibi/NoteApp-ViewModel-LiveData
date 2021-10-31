package com.example.noteapp_room

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val myViewModel by lazy{ ViewModelProvider(this).get(MyViewModel::class.java)}
    lateinit var rvMain: RecyclerView
    lateinit var rvAdapter: NoteAdapter

    lateinit var etnote: EditText
    lateinit var addBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain = findViewById(R.id.rvMain)
        etnote = findViewById(R.id.etnote)
        addBtn = findViewById(R.id.addBtn)


        myViewModel.getNotes().observe(this, {
                notes -> rvAdapter = NoteAdapter(this, notes)
                         rvMain.adapter = rvAdapter
                         rvMain.layoutManager = LinearLayoutManager(this)
        })

        addBtn.setOnClickListener {
            val note = etnote.text.toString()
            myViewModel.addNote(note)
            etnote.setText("")
        }

    }


    fun editAlert( idNote: Int, note:String){
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
        val input = EditText(this)
        var newNote = ""
        input.setText("$note")
        dialogBuilder.setMessage("")
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ ->
                newNote = input.text.toString()
                myViewModel.editNote(idNote,newNote)
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()

        alert.setTitle("Edit Alert")
        alert.setView(input)
        alert.show()


    }

    fun deleteAlert( idNote: Int , note: String){
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)


        dialogBuilder.setMessage("Confirm delete ?")
            .setPositiveButton("Delete", DialogInterface.OnClickListener {
                    _, _ ->
                myViewModel.deleteNote(idNote, note)
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()

        alert.setTitle("Delete Alert")
        alert.show()


    }
}