package de.dthomalla.foodrestrictor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DailyCubeActivity extends AppCompatActivity {

    ArrayList<DailyActivityButton> dailyActivitiesGrid = new ArrayList<>();
    private CaldroidFragment dialogCaldroidFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_cube);

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDailyCube);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(formatter.format(new Date()));
            actionBar.setLogo(R.mipmap.ic_launcher);
        }

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getApplicationContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
//                if (caldroidFragment.getLeftArrowButton() != null) {
//                    Toast.makeText(getApplicationContext(),
//                            "Caldroid view is created", Toast.LENGTH_SHORT)
//                            .show();
//                }
            }

        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//            });
            final Bundle state = savedInstanceState;

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Setup caldroid to use as dialog
                    dialogCaldroidFragment = new CaldroidFragment();
                    dialogCaldroidFragment.setCaldroidListener(listener);

                    // If activity is recovered from rotation
                    final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
                    if (state != null) {
                        dialogCaldroidFragment.restoreDialogStatesFromKey(
                                getSupportFragmentManager(), state, "DIALOG_CALDROID_SAVED_STATE", dialogTag);
                        Bundle args = dialogCaldroidFragment.getArguments();
                        if (args == null) {
                            args = new Bundle();
                            dialogCaldroidFragment.setArguments(args);
                        }
                    } else {
                        // Setup arguments
                        Bundle bundle = new Bundle();
                        // Setup dialogTitle
                        dialogCaldroidFragment.setArguments(bundle);
                    }

                    dialogCaldroidFragment.show(getSupportFragmentManager(), dialogTag);
                }
            });
        }

        setGridFull();
    }

    private void setGridFull(){
        for (DailyActivityButton btn : dailyActivitiesGrid){
            btn.setState(DailyActivityState.EMPTY);
        }
    }

    private void initDailyActivitiesGrid(){
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc1_1) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc1_2) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc1_3) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc1_4) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc1_5) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc1_6) );

        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc2_1) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc2_2) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc2_3) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc2_4) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc2_5) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc2_6) );

        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc3_1) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc3_2) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc3_3) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc3_4) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc3_5) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc3_6) );

        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc4_1) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc4_2) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc4_3) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc4_4) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc4_5) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc4_6) );

        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc5_1) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc5_2) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc5_3) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc5_4) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc5_5) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc5_6) );

        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc6_1) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc6_2) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc6_3) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc6_4) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc6_5) );
        dailyActivitiesGrid.add( (DailyActivityButton)findViewById(R.id.dc6_6) );
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

//        if (caldroidFragment != null) {
//            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
//        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }
}
