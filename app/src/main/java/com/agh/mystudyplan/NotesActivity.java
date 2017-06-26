package com.agh.mystudyplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    Button save;
    Button reject;
    EditText notes;
    NotesDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Intent intent = getIntent();
        final int arrayDay = intent.getIntExtra("day", 9) + 1;
        int arrayPosition = intent.getIntExtra("subject", 9);

        ArrayList<MySubject> subjects = MainActivity.PlaceholderFragment.getArray(getApplicationContext(),
                arrayDay);

        String subject = subjects.get(arrayPosition).getSubject();

        getSupportActionBar().setTitle(subject);

        reject = (Button) findViewById(R.id.cancel_button);
        save = (Button) findViewById(R.id.add_button);
        notes = (EditText) findViewById(R.id.sub_notes_edit);

        dataBase = new NotesDataBase(getBaseContext(), subject + arrayDay +
                subjects.get(arrayPosition).getStartHour());

        notes.setText(dataBase.getNotes());
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBase.storeNewNotes(notes.getText().toString());
                finish();
            }
        });
    }
}
