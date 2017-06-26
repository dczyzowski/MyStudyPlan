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

        ArrayList<MySubject> recordsSorted = new ArrayList<>();

        for (int i = 0; i < records.size() ; i++) {
            String[] startString = records.get(i).getStartHour().split(":");
            float startTime = Float.valueOf(startString[0] + "." + startString[1]);

            boolean notFound = true; // sprawdza czy znaleziono mniejsza liczbe

            if(!recordsSorted.isEmpty()){
                int j = 0; //index jaki bedie miala nasz liczba
                while (notFound){
                    try {
                        String[] nextStartString = recordsSorted.get(j).getStartHour().split(":");
                        float nextStartTime = Float.valueOf(nextStartString[0] + "." + nextStartString[1]);
                        if(nextStartTime > startTime) {
                            notFound = false;
                            recordsSorted.add(j, records.get(i));
                        }
                    }
                    catch (IndexOutOfBoundsException e){
                        notFound = false;
                        recordsSorted.add(j, records.get(i));
                    }

                    j++;
                }
            }
            else
                recordsSorted.add(i, records.get(i));
        }

        return recordsSorted;
    }

    public void putArray(ArrayList<MySubject> array){

        ArrayList<String> daySubject = new ArrayList<>();
        for (MySubject subject : array) {
            daySubject.add(subject.getStartHour() + "@@" + subject.getEndHour() + "@@"
                    + subject.getSubject() + "@@" + subject.getSubjectType());
        }

        Set<String> set = new HashSet<>(daySubject);

        SharedPreferences.Editor spEditor = planLocalDatabase.edit();
        spEditor.putStringSet("day", set);
        spEditor.apply();
    }


    public void clearDatabase() {
        SharedPreferences.Editor spEditor = planLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }
}

