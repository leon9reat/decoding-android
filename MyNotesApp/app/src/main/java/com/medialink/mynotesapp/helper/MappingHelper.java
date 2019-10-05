package com.medialink.mynotesapp.helper;

import android.database.Cursor;

import com.medialink.mynotesapp.model.Note;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.medialink.mynotesapp.db.DatabaseContract.NoteColumns.DATE;
import static com.medialink.mynotesapp.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.medialink.mynotesapp.db.DatabaseContract.NoteColumns.TITLE;

public class MappingHelper {

    public static ArrayList<Note> mapCursorToArrayList(Cursor noteCursor) {
        ArrayList<Note> noteList = new ArrayList<>();
        while (noteCursor.moveToNext()) {
            int id = noteCursor.getInt(noteCursor.getColumnIndexOrThrow(_ID));
            String title = noteCursor.getString(noteCursor.getColumnIndexOrThrow(TITLE));
            String description = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DESCRIPTION));
            String date = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DATE));
            noteList.add(new Note(id, title, description, date));
        }

        return noteList;
    }
}
