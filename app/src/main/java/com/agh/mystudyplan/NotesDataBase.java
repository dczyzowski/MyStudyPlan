package com.agh.mystudyplan;

import android.content.Context;
import android.content.SharedPreferences;

public class NotesDataBase {

    SharedPreferences planLocalDatabase;

    public NotesDataBase(Context context, String subject) {
        planLocalDatabase = context.getSharedPreferences(subject, 0);
    }

    public void storeNewNotes(String note) {
        SharedPreferences.Editor spEditor = planLocalDatabase.edit();
        spEditor.putString("note", note);
        spEditor.apply();
    }

    public String getNotes(){
        return planLocalDatabase.getString("note", "Moje notatki...");
    }
}
