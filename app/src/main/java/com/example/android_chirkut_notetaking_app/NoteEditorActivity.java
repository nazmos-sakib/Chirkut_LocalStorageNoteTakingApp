package com.example.android_chirkut_notetaking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

//edit existing note or typing new notes
public class NoteEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        //it has only one view which is EditText
        EditText editNote = findViewById(R.id.editNote);

        Intent intent = getIntent();

        int noteId = intent.getIntExtra("noteId",-1);

        // if -1 create new note else edit existing note
         if (noteId != -1){
             editNote.setText(MainActivity.notes.get(noteId));
         } else {
             MainActivity.notes.add("");
             noteId = MainActivity.notes.size() -1;
         }

        int finalNoteId = noteId;

         //detect changes
        editNote.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 //updating the note variable array
                MainActivity.notes.set(finalNoteId,String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                //adding newly changes to the shared preferences
                 SharedPreferences sharedPreferences = getSharedPreferences("com.example.android_chirkut_notetaking_app", Context.MODE_PRIVATE);
                 HashSet<String> set = new HashSet<>(MainActivity.notes);
                 sharedPreferences.edit().putStringSet("notes",set).apply();
             }

             @Override
             public void afterTextChanged(Editable editable) {

             }
         }); //end of addTextChangedListener
    }
}