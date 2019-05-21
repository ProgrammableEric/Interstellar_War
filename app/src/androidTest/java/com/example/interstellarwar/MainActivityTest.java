package com.example.interstellarwar;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity  mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mActivity.findViewById(R.id.textView);
        assertNotNull("Main activity launch fails!",view);

    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;


    }
}