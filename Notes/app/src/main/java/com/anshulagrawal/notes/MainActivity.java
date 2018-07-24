package com.anshulagrawal.notes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView notesListView;
    public static final List<String> NOTES_LIST = new ArrayList<>();
    public static ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (NOTES_LIST.size() == 0)
            NOTES_LIST.add("Start writing...");

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, NOTES_LIST);

        notesListView = findViewById(R.id.listView);
        notesListView.setAdapter(adapter);

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), NotesEditor.class);
                intent.putExtra("noteId", i);
                startActivity(intent);
            }
        });
        notesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                new AlertDialog.Builder(getApplicationContext())
                        .setIcon(R.drawable.ic_launcher_background)
                        .setTitle("Are you sure you want to delete this note>")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NOTES_LIST.remove(i);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.addNote:
                NOTES_LIST.add("Start writing...");
                startNoteEditor(NOTES_LIST.size() - 1);
                return true;
            default:
                return false;
        }
    }

    private void startNoteEditor(int i) {
        Intent intent = new Intent(getApplicationContext(), NotesEditor.class);
        intent.putExtra("noteId", i);
        startActivity(intent);
    }
}
