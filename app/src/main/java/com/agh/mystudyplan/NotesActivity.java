package com.agh.mystudyplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NotesActivity extends AppCompatActivity {

    Button save;
    Button reject;
    EditText notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Intent intent = getIntent();
        getSupportActionBar().setTitle(" " + intent.getIntExtra("day", 9));

        reject = (Button) findViewById(R.id.cancel_button);
        save = (Button) findViewById(R.id.add_button);
        notes = (EditText) findViewById(R.id.sub_notes_edit);

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
