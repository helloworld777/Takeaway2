package com.lu.takeaway;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.lu.takeaway.view.activity.LaunchActivity;

/**
 * Created by lenovo on 2016/3/25.
 */

public class TestClass extends ActivityInstrumentationTestCase2 {
    static final String LOG_TAG = "MathTest";
    protected int i1;
    protected int i2;

    public TestClass(Class activityClass) {
        super(LaunchActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        System.out.println("setUp");
        super.setUp();
    }

    public void testA() throws Exception{
        System.out.println("setUp");
        Log.d( LOG_TAG, "testAdd" );
        assertTrue( LOG_TAG+"1", ( ( i1 + i2 ) == 5 ) );
    }
}
