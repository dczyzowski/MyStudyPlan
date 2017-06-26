package com.agh.mystudyplan;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Damian on 2017-06-26.
 */

public class NotesDataBase {

    SharedPreferences planLocalDatabase;

    public NotesDataBase(Context context, String subject) {
        planLocalDatabase = context.getSharedPreferences(subject, 0);
    }

    public void storeNewRecord(String note) {

        SharedPreferences.Editor spEditor = planLocalDatabase.edit();
        spEditor.putString("note", note);
        spEditor.apply();
    }
}
