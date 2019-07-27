package com.ashhasib.architask;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;


    public NoteRepository(Application application) {
        NoteDatabase database=NoteDatabase.getInstance(application);
        noteDao = database.noteDao(); //abstract method implemented by room
        allNotes = noteDao.getAllNotes(); //auto implemented
    }


    public void insert(Note note) {
        new InsertNoteAsync(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsync(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsync(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsync(noteDao).execute();
    }

    //room will return allNotes
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }








    /**
     * AsyncTasks
     */

    private static class InsertNoteAsync extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;
        private InsertNoteAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }


    private static class UpdateNoteAsync extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;
        private UpdateNoteAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }




    private static class DeleteNoteAsync extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;
        private DeleteNoteAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }



    private static class DeleteAllNotesAsync extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;
        private DeleteAllNotesAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }

}
