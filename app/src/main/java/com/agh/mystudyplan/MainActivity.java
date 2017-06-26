package com.agh.mystudyplan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static ViewPager mViewPager;


    // wygenerowane automatycznie podczas tworzenia projektu
    // konstruktor activity deklarowane sa tu layouty i wszystko co ma sie stac po uruchomieniu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the seven
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddToPlanDialog.class);
                startActivity(intent);
            }
        });
    }

    // wygenerowane automatycznie podczas tworzenia projektu
    // tworzy liste opcji w menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // wygenerowane automatycznie podczas tworzenia projektu
    // sprawdza ktora opcje w menu wybralismy
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // wygenerowane automatycznie podczas tworzenia projektu

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        //result daje nam informaje o tym ktora akcja zostala wykonana, zeby odebrac jej wyniki
        private static final int RESULT_NEW_SUBJECT = 10;

        boolean isLongClicked = false;
        CalendarAdapter calendarAdapter;
        static PlanDataBase planDataBase;

        public PlaceholderFragment() {
        }

        // wygenerowane automatycznie podczas tworzenia projektu

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            Bundle args = getArguments();

            final ArrayList<MySubject> subjectsList = getArray(getContext(), args.getInt(ARG_SECTION_NUMBER));
            calendarAdapter = new CalendarAdapter(getContext(), R.layout.calendar_row,
                    subjectsList);

            ListView listView = (ListView) rootView.findViewById(R.id.calendary);
            listView.setAdapter(calendarAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!isLongClicked) {
                        Intent intent = new Intent(getContext(), NotesActivity.class);
                        intent.putExtra("day", mViewPager.getCurrentItem());
                        intent.putExtra("subject", position);
                        startActivityForResult(intent, RESULT_NEW_SUBJECT);
                    }
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    isLongClicked = true;

                    // tworze alert dialog w ktorym informuje uzytkownika o probie usuniecia
                    // przedmiotu z listy
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true);
                    builder.setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            subjectsList.remove(position);
                            planDataBase.putArray(subjectsList);
                            calendarAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                            isLongClicked = false;
                        }
                    });
                    builder.setMessage("Czy chcesz usunąć przedmiot " +
                            subjectsList.get(position).getSubject());

                    builder.setTitle("Usuń przedmiot");
                    builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isLongClicked = false;
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;
                }
            });


            return rootView;
        }

        public static ArrayList<MySubject> getArray(Context context, int position) {
            switch (position) {
                case 1:
                    planDataBase = new PlanDataBase(context, context.getString(R.string.pn));
                    break;
                case 2:
                    planDataBase = new PlanDataBase(context, context.getString(R.string.wt));
                    break;
                case 3:
                    planDataBase = new PlanDataBase(context, context.getString(R.string.sr));
                    break;
                case 4:
                    planDataBase = new PlanDataBase(context, context.getString(R.string.czw));
                    break;
                case 5:
                    planDataBase = new PlanDataBase(context, context.getString(R.string.pt));
                    break;
                case 6:
                    planDataBase = new PlanDataBase(context, context.getString(R.string.sob));
                    break;
                case 7:
                    planDataBase = new PlanDataBase(context, context.getString(R.string.niedz));
                    break;
                default:
                    planDataBase = new PlanDataBase(context, context.getString(R.string.niedz));
                    break;
            }
            return planDataBase.getRecords();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mSectionsPagerAdapter.notifyDataSetChanged();

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        //ilosc elementow
        @Override
        public int getCount() {
            // Show 7 total pages.
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.pn);
                case 1:
                    return getString(R.string.wt);
                case 2:
                    return getString(R.string.sr);
                case 3:
                    return getString(R.string.czw);
                case 4:
                    return getString(R.string.pt);
                case 5:
                    return getString(R.string.sob);
                case 6:
                    return getString(R.string.niedz);
            }
            return null;
        }
    }
}
