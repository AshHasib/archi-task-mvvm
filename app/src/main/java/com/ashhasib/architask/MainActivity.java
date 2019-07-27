package com.ashhasib.architask;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE =1 ;
    private static final int EDIT_REQUEST_CODE =2 ;
    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter= new NoteAdapter();

        recyclerView.setAdapter(adapter);

        noteViewModel = ViewModelProviders
                .of(this)
                .get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                //update our RecyclerView
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_LONG).show();

                adapter.submitList(notes);
            }
        });







        FloatingActionButton fab= findViewById(R.id.fab);

        fab.setOnClickListener(v->{
            startActivityForResult(new Intent(this, AddEditNoteActivity.class), REQUEST_CODE);
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);



        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent= new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.TITLE, note.getName());
                intent.putExtra(AddEditNoteActivity.DESC, note.getDesc());
                intent.putExtra(AddEditNoteActivity.PRIORITY, note.getPriority());
                intent.putExtra(AddEditNoteActivity.ID, note.getId());

                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(REQUEST_CODE==requestCode && resultCode == RESULT_OK) {
            String title= data.getStringExtra(AddEditNoteActivity.TITLE);
            String desc= data.getStringExtra(AddEditNoteActivity.DESC);
            int pr= data.getIntExtra(AddEditNoteActivity.PRIORITY, 1);

            Note note = new Note(title,desc,pr);

            noteViewModel.insert(note);

            Toast.makeText(MainActivity.this, "Note saved", Toast.LENGTH_LONG).show();

        }


        if(EDIT_REQUEST_CODE==requestCode && resultCode == RESULT_OK) {
            int id= data.getIntExtra(AddEditNoteActivity.ID,-1);

            if(id==-1) {
                Toast.makeText(MainActivity.this, "Note cant be updated", Toast.LENGTH_LONG).show();
                return;
            }

            String title= data.getStringExtra(AddEditNoteActivity.TITLE);
            String desc= data.getStringExtra(AddEditNoteActivity.DESC);
            int pr= data.getIntExtra(AddEditNoteActivity.PRIORITY, 1);

            Note note = new Note(title,desc,pr);
            note.setId(id);

            noteViewModel.update(note);
            Toast.makeText(MainActivity.this, "Note Updated", Toast.LENGTH_LONG).show();


        }

        else {
            Toast.makeText(MainActivity.this, "Note not saved", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();

        switch (id) {
            case R.id.delete_all :{
                noteViewModel.deleteAllNotes();
                Toast.makeText(MainActivity.this, "All Notes Deleted", Toast.LENGTH_LONG).show();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
