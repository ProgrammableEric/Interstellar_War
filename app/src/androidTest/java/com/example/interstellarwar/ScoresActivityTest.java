package com.example.interstellarwar;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class ScoresActivityTest {
    /*
    * Author: Chunze Fu
    * */


    @Rule
    public ActivityTestRule<ScoresActivity> scoresActivityActivityTestRule = new ActivityTestRule<ScoresActivity>(ScoresActivity.class);
    private ScoresActivity  scoreActivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(StartActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        scoreActivity = scoresActivityActivityTestRule.getActivity();
    }


    @Test
    public void testLaunch(){
        Button btn = scoreActivity.findViewById(R.id.button2);
        assertNotNull("Score activity launch fails!",btn);
    }

//    @Test
//    public void testStartButton (){
//        View btn = scoreActivity.findViewById(R.id.button2);
//        assertNotNull("Test startbutton fails! - button not found. ", btn);
//        onView(withId(R.id.button)).perform(click());
//        Activity startActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
//        assertNotNull("start activity not launched - restart failure! ",startActivity);
//        startActivity.finish();
//    }

    @After
    public void tearDown() throws Exception {
        scoreActivity = null;
    }
}