package com.agh.mystudyplan;

// baza danych zostala oparta o SharedPreferences
// kazda zapisana wartosc ma swoj 'adres' (slowo kluczowe)

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PlanDataBase {

    SharedPreferences planLocalDatabase;

    public PlanDataBase(Context context, String day) {
        planLocalDatabase = context.getSharedPreferences(day, 0);
    }

    public void storeNewRecord(MySubject subject) {
        Set<String> daySubjects = new HashSet<>(planLocalDatabase.getStringSet("day", new HashSet<String>()));


        daySubjects.add(subject.getStartHour() + "@@" + subject.getEndHour() + "@@"
                + subject.getSubject() + "@@" + subject.getSubjectType());

        SharedPreferences.Editor spEditor = planLocalDatabase.edit();
        spEditor.putStringSet("day", daySubjects);
        spEditor.apply();
    }

    public ArrayList<MySubject> getRecords() {

        ArrayList<String> recordsTemp = new ArrayList<>(planLocalDatabase.getStringSet("day", new HashSet<String>()));
        ArrayList<MySubject> records = new ArrayList<>();

        for (String s : recordsTemp) {
            String[] temp = s.split("@@");
            records.add(new MySubject(temp[0], temp[1], temp[2], temp[3]));
        }

        return records;
    }

    public void clearDatabase() {
        SharedPreferences.Editor spEditor = planLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }
}

