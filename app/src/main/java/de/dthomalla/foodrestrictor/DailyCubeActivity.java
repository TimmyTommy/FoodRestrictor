package de.dthomalla.foodrestrictor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class DailyCubeActivity extends AppCompatActivity {

    ArrayList<DailyActivityButton> dailyActivitiesGrid = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_cube);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDailyActivitiesGrid();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
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
}
