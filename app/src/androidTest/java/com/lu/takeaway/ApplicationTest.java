package com.lu.takeaway;

import android.test.ActivityTestCase;

import com.lu.takeaway.bean.User;

import cn.bmob.v3.listener.SaveListener;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityTestCase {
    public ApplicationTest() {
//        super(DingDanApplication.class);
        System.out.println(".............asdasdadasdasdaaa");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.out.println(".............asdasdadasdasdaaa");
    }

    public void testSave() throws Exception {
        final int expected = 1;
        final int reality = 1;
        final User user = new User();
        user.setUsername("123123");
        user.setPassword("123123");
        System.out.println(".............aaa");
        user.signUp(getActivity(), new SaveListener() {

            @Override
            public void onSuccess() {
                System.out.println(".............onSuccess");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                System.out.println(".............onFailure");
            }
        });
        assertEquals(expected, reality);
    }
}