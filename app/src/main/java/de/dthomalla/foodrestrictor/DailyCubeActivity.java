package de.dthomalla.foodrestrictor;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DailyCubeActivity extends AppCompatActivity {

    final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
    private String mSaveDir;
    private Date mActiveDate = new Date();

    ArrayList<DailyActivityButton> dailyActivitiesGrid = new ArrayList<>();
    private CaldroidFragment dialogCaldroidFragment;

    private GestureDetector gestureDetector;

    private void initializeFiles() {
        mSaveDir = getFileDir("saves/");
    }

    private String getFileDir(String folder) {
//        String fileDirPath = getFilesDir().getAbsolutePath();
//        File file = new File(fileDirPath + "/" + folder);
        File file = getExternalFilesDir(folder);
        file.mkdir();
        if (!file.isDirectory()) {
            ErrorActivity.handleError(this, "Die benötigten Ordner für die Anwendung konnten nicht angelegt werden.");
        }
        return file.getAbsolutePath();
    }

    private File getSaveFile(Date date){
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd").format(date);
        String fileName = String.format("SavedDay_%s.json", timeStamp);
        return new File(mSaveDir, fileName);
    }

    @Override
    public void onBackPressed() {
        try {
            saveDay();
            super.onBackPressed();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            ErrorActivity.handleError(this, e.getMessage());
        }
    }

    private void refreshActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(formatter.format(mActiveDate));
            actionBar.setLogo(R.mipmap.ic_launcher);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_cube);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDailyCube);
        setSupportActionBar(toolbar);

        initializeFiles();
        initDailyActivitiesGrid();
        try {
            loadDay(mActiveDate);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            ErrorActivity.handleError(this, e.getMessage());
        }

        gestureDetector = new GestureDetector(new SwipeGestureDetector());

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getApplicationContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();
                try {
                    saveDay();
                    loadDay(date);
                    dialogCaldroidFragment.dismiss();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
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
                        dialogCaldroidFragment.setBackgroundDrawableForDate(new ColorDrawable(Color.GREEN) , mActiveDate);
                        dialogCaldroidFragment.setArguments(bundle);
                    }

                    dialogCaldroidFragment.show(getSupportFragmentManager(), dialogTag);
                }
            });
        }

        //setGridFull();
    }

    private void clearDailyActivitiesGrid(){
        for (DailyActivityButton btn : dailyActivitiesGrid){
            btn.setState(DailyActivityState.EMPTY);
        }
    }

    private JSONArray getDailyActivitiesGridAsJson(){
        JSONArray json = new JSONArray();
        for (int i=0; i<dailyActivitiesGrid.size(); i++){
            json.put(dailyActivitiesGrid.get(i).getState().getValue());
        }
        return json;
    }

    private void setDailyActivitiesGridFromJson(JSONArray json){
        for (int i=0; i<json.length(); i++){
            dailyActivitiesGrid.get(i).setState(DailyActivityState.parseInt(json.optInt(i, 0)));
        }
    }

    private void saveDay() throws JSONException, IOException {
        File file = getSaveFile(mActiveDate);
        JSONArray jsonArray = getDailyActivitiesGridAsJson();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("actGrid", jsonArray);
        String saveStr = jsonObject.toString(2);
        FileOutputStream fOut = new FileOutputStream(file);
        fOut.write(saveStr.getBytes());
    }

    private void loadDay(Date date) throws IOException, JSONException {
        mActiveDate = date;
        File file = getSaveFile(mActiveDate);
        if (file.exists()){
            String jsonFileString = FileManager.getStringFromFile(file.getAbsolutePath());
            JSONObject jsonObject = new JSONObject(jsonFileString);
            JSONArray jsonArray = jsonObject.optJSONArray("actGrid");
            setDailyActivitiesGridFromJson(jsonArray);
        } else {
            clearDailyActivitiesGrid();
        }
        refreshActionBar();
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void onLeftSwipe() {
        // Do something
        Calendar c = Calendar.getInstance();
        c.setTime(mActiveDate);
        c.add(Calendar.DATE, 1);
        try {
            saveDay();
            loadDay(c.getTime());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            ErrorActivity.handleError(this, e.getMessage());
        }
    }

    private void onRightSwipe() {
        // Do something
        Calendar c = Calendar.getInstance();
        c.setTime(mActiveDate);
        c.add(Calendar.DATE, -1);
        try {
            saveDay();
            loadDay(c.getTime());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            ErrorActivity.handleError(this, e.getMessage());
        }
    }

    // Private class for gestures
    private class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        // Swipe properties, you can change it to make the swipe
        // longer or shorter and speed
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 200;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            try {
                float diffAbs = Math.abs(e1.getY() - e2.getY());
                float diff = e1.getX() - e2.getX();

                if (diffAbs > SWIPE_MAX_OFF_PATH)
                    return false;

                // Left swipe
                if (diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    DailyCubeActivity.this.onLeftSwipe();

                    // Right swipe
                } else if (-diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    DailyCubeActivity.this.onRightSwipe();
                }
            } catch (Exception e) {
                Log.e("YourActivity", "Error on gestures");
            }
            return false;
        }
    }
}
