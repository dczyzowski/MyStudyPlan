package com.agh.mystudyplan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddToPlanDialog extends AppCompatActivity {

    EditText subject;
    Spinner daySpinner;
    Spinner subjectTypeSpinner;

    Button mAddButton;
    Button mCancelButton;

    String carName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_to_plan);

        //pobiera z intencji informacje wejsciowa, nazwe samochodu
        carName = getIntent().getStringExtra("carName");

        // deklaruje obiekty
        subject = (EditText) findViewById(R.id.subject_edit);
        daySpinner = (Spinner) findViewById(R.id.day_spinner);
        subjectTypeSpinner = (Spinner) findViewById(R.id.subject_type_spinner);

        mAddButton = (Button) findViewById(R.id.add_button);
        mCancelButton = (Button) findViewById(R.id.cancel_button);

        //getString pobiera z values/strings poszczegolne wyrazy
        ArrayList<String> days = new ArrayList<>();
        days.add(getString(R.string.pn));
        days.add(getString(R.string.wt));
        days.add(getString(R.string.sr));
        days.add(getString(R.string.czw));
        days.add(getString(R.string.pt));
        days.add(getString(R.string.sob));
        days.add(getString(R.string.niedz));

        // adapter generuje liste elementow w spinnerze
        daySpinner.setAdapter(new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item, days));

        // elementy ktore wyswietla sie w spinnerze z typem zajec
        ArrayList<String> types = new ArrayList<>();
        types.add(getString(R.string.lektorat));
        types.add(getString(R.string.laboratoryjne));
        types.add(getString(R.string.projektowe));
        types.add(getString(R.string.wyklad));
        types.add(getString(R.string.audytoyjne));

        // generuje liste elementow w spinerze
        subjectTypeSpinner.setAdapter(new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item, types));


        // Obiekt klasy numberPicker odpowiada za element w ktorym mozemy przesowac palcem
        // aby wybrac interesujaca nas godzine
        final NumberPicker startPicker = (NumberPicker) findViewById(R.id.start_picker);
        final NumberPicker endPicker = (NumberPicker) findViewById(R.id.end_picker);

        // wybieram ilosc elementow w NumbierPickerach
        startPicker.setMaxValue(63);
        endPicker.setMaxValue(63);

        // generuje godziny ktore pojawia sie w NumberPickerach
        final String[] hours = new String[64];
        int i = 0;
        for(int godz = 7; godz < 23 ; godz++){
            for (int min = 0; min < 60; min += 15) {
                if (min == 0)
                    hours[i] = String.format("%d:0%d", godz, min);
                else
                    hours[i] = String.format("%d:%d", godz, min);
                i++;
            }
        }

        startPicker.setDisplayedValues(hours);
        endPicker.setDisplayedValues(hours);


        //dodaje nowy obiekt do bazy danych
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok = true;
                if (subject.getText().toString().isEmpty()) {
                    subject.setError(getString(R.string.fill_this));
                    ok = false;
                }
                if (ok) {
                    PlanDataBase dataBase = new PlanDataBase(getBaseContext(),
                            (String) daySpinner.getSelectedItem());

                    dataBase.storeNewRecord(new MySubject(hours[startPicker.getValue()],
                            hours[endPicker.getValue()], subject.getText().toString(),
                            (String) subjectTypeSpinner.getSelectedItem()));

                    finish();
                }
            }
        });

        // nie dodaje nowego obiektu do mojej bazy danych
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
