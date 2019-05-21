package com.example.interstellarwar;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {
    /*
    * Author: Chunze Fu
    * */

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity  mActivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(StartActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        mActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View textview = mActivity.findViewById(R.id.textView);
        assertNotNull("Main activity launch fails!",textview);
    }

    @Test
    public void testStartButton (){
        View btn = mActivity.findViewById(R.id.button);
        assertNotNull("Test startbutton fails! - button not found. ", btn);
        onView(withId(R.id.button)).perform(click());
        Activity startActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull("start activity not launched - button failure! ",startActivity);
        startActivity.finish();
    }

    @Test
    public void testStateTransition (){

    }



    @After
    public void tearDown() throws Exception {
        mActivity = null;

    }
}