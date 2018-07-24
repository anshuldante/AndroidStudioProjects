package com.anshulagrawal.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class NotesEditor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);

        EditText editText = findViewById(R.id.editText);

        final int noteId = getIntent().getIntExtra("noteId", -1);

        if (noteId != -1)
            editText.setText(MainActivity.NOTES_LIST.get(noteId));

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                MainActivity.NOTES_LIST.set(noteId, String.valueOf(charSequence));
                MainActivity.adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
