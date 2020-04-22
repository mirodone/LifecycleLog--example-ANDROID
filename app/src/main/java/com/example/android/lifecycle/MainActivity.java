package com.example.android.lifecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*
     * This tag will be used for logging. It is best practice to use the class's name using
     * getSimpleName as that will greatly help to identify the location from which your logs are
     * being posted.
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /* Constant values for the names of each respective lifecycle callback */
    private static final String ON_CREATE = "onCreate";
    private static final String ON_START = "onStart";
    private static final String ON_RESUME = "onResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_RESTART = "onRestart";
    private static final String ON_DESTROY = "onDestroy";
    private static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState";

    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";

    /*
     * This TextView will contain a running log of every lifecycle callback method called from this
     * Activity. This TextView can be reset to its default state by clicking the Button labeled
     * "Reset Log"
     */
    private TextView mLifecycleDisplay;

    /*
     * This ArrayList will keep track of lifecycle callbacks that occur after we are able to save
     * them. Since, as we've observed, the contents of the TextView are saved in onSaveInstanceState
     * BEFORE onStop and onDestroy are called, we must track when onStop and onDestroy are called,
     * and then update the UI in onStart when the Activity is back on the screen.
     */
    private static final ArrayList<String> mLifecycleCallbacks = new ArrayList<>();

    /**
     * Called when the activity is first created. This is where you should do all of your normal
     * static set up: create views, bind data to lists, etc.
     *
     * Always followed by onStart().
     *
     * @param savedInstanceState The Activity's previously frozen state, if there was one.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLifecycleDisplay = (TextView) findViewById(R.id.tv_lifecycle_events_display);

        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_KEY)) {
                String allPrevLifecycleCallbacks = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
                mLifecycleDisplay.setText(allPrevLifecycleCallbacks);
            }
        }

        /*
         * Since any updates to the UI we make after onSaveInstanceState (onStop, onDestroy, etc),
         * we use an ArrayList to track if these lifecycle events had occurred. If any of them have
         * occurred, we append their respective name to the TextView.
         *
         * The reason we iterate starting from the back of the ArrayList and ending in the front
         * is that the most recent callbacks are inserted into the front of the ArrayList, so
         * naturally the older callbacks are stored further back. We could have used a Queue to do
         * this, but Java has strange API names for the Queue interface that we thought might be
         * more confusing than this ArrayList solution.
         */
        for (int i = mLifecycleCallbacks.size() - 1; i >= 0; i--) {
            mLifecycleDisplay.append(mLifecycleCallbacks.get(i) + "\n");
        }

        /*
         * Once we've appended each callback from the ArrayList to the TextView, we need to clean
         * the ArrayList so we don't get duplicate entries in the TextView.
         */
        mLifecycleCallbacks.clear();

        logAndAppend(ON_CREATE);
    }

    // TODO (2) Override onStart, call super.onStart, and call logAndAppend with ON_START

    @Override
    protected void onStart() {
        super.onStart();
        logAndAppend(ON_START);
    }

    // TODO (3) Override onResume, call super.onResume, and call logAndAppend with ON_RESUME

    @Override
    protected void onResume() {
        super.onResume();
        logAndAppend(ON_RESUME);
    }

    // TODO (4) Override onPause, call super.onPause, and call logAndAppend with ON_PAUSE

    @Override
    protected void onPause() {
        super.onPause();
        logAndAppend(ON_PAUSE);
    }

    // TODO (5) Override onStop, call super.onStop, and call logAndAppend with ON_STOP

    @Override
    protected void onStop() {
        super.onStop();

        /*
         * Since any updates to the UI we make after onSaveInstanceState (onStop, onDestroy, etc),
         * we use an ArrayList to track if these lifecycle events had occurred. If any of them have
         * occurred, we append their respective name to the TextView.
         */
        mLifecycleCallbacks.add(0, ON_STOP);
        logAndAppend(ON_STOP);
    }

    // TODO (6) Override onRestart, call super.onRestart, and call logAndAppend with ON_RESTART

    @Override
    protected void onRestart() {
        super.onRestart();

        logAndAppend(ON_RESTART);
    }

    // TODO (7) Override onDestroy, call super.onDestroy, and call logAndAppend with ON_DESTROY

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*
         * Since any updates to the UI we make after onSaveInstanceState (onStop, onDestroy, etc),
         * we use an ArrayList to track if these lifecycle events had occurred. If any of them have
         * occurred, we append their respective name to the TextView.
         */
        mLifecycleCallbacks.add(0, ON_DESTROY);
        logAndAppend(ON_DESTROY);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logAndAppend(ON_SAVE_INSTANCE_STATE);
        String lifecycleDisplayTextViewContents  = mLifecycleDisplay.getText().toString();
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_KEY, lifecycleDisplayTextViewContents );
    }

    /**
     * Logs to the console and appends the lifecycle method name to the TextView so that you can
     * view the series of method callbacks that are called both from the app and from within
     * Android Studio's Logcat.
     *
     * @param lifecycleEvent The name of the event to be logged.
     */
    private void logAndAppend(String lifecycleEvent) {
        Log.d(TAG, "Lifecycle Event: " + lifecycleEvent);

        mLifecycleDisplay.append(lifecycleEvent + "\n");
    }

    /**
     * This method resets the contents of the TextView to its default text of "Lifecycle callbacks"
     *
     * @param view The View that was clicked. In this case, it is the Button from our layout.
     */
    public void resetLifecycleDisplay(View view) {
        mLifecycleDisplay.setText("Lifecycle callbacks:\n");
    }
}
