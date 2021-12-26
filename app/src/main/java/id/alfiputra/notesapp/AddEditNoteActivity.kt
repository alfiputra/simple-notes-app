package id.alfiputra.notesapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var edtNoteTitle: EditText
    private lateinit var edtNoteDescription: EditText
    private lateinit var btnAddUpdate: Button
    private lateinit var viewModel: NoteViewModel
    private var noteId = -1


    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        edtNoteTitle = findViewById(R.id.edt_noteTitle)
        edtNoteDescription = findViewById(R.id.edt_noteDescription)
        btnAddUpdate = findViewById(R.id.btn_addUpdate)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")){

            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteId = intent.getIntExtra("noteId", -1)
            btnAddUpdate.text = "Update Note"
            edtNoteTitle.setText(noteTitle)
            edtNoteDescription.setText(noteDesc)
        }else{
            btnAddUpdate.text = "Save Note"
        }

        btnAddUpdate.setOnClickListener {
            val noteTitle = edtNoteTitle.text.toString()
            val noteDesc = edtNoteDescription.text.toString()

            if (noteType.equals("Edit")){
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()){
                    val dateFormat = SimpleDateFormat("dd MMM, yyyy - HH:MM")
                    val currentDate: String = dateFormat.format(Date())
                    val updateNote = Note(noteTitle, noteDesc, currentDate)
                    updateNote.id = noteId
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }
            }else{
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()){
                    val dateFormat = SimpleDateFormat("dd MMM, yyyy - HH:MM")
                    val currentDate: String = dateFormat.format(Date())
                    viewModel.addNote(Note(noteTitle, noteDesc, currentDate))
                    Toast.makeText(this, "Note Added..", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}