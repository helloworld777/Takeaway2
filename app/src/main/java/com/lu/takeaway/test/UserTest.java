package com.lu.takeaway.test;

import android.app.Activity;
import android.test.InstrumentationTestCase;

import com.lu.takeaway.bean.User;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by lenovo on 2016/3/25.
 */
//@RunWith(RobolectricGradleTestRunner.class)
//@Config(constants = BuildConfig.class, manifest = "app/src/main/AndroidManifest.xml",application=com.lu.takeaway.view.app.DingDanApplication.class)
public class UserTest  extends InstrumentationTestCase {
//    private Solo solo;
//    public UserTest(){
//        super(LaunchActivity.class);
//    }
     @Override
    public void setUp()  throws Exception {
        // 获取待测Activity
//        mActivity = Robolectric.setupActivity(BaseFragmentActivity.class);
        System.out.println("setUp");
//        mActivity=l;
//        solo=new Solo(getInstrumentation(),getActivity());
    }
    // 引用待测Activity
    private Activity mActivity;

    public void testSave(){
        System.out.println("aaaaaaaaaaaaaaaa");
        System.out.println("aaaaaaaaaaaaaaaa:"+mActivity);
        final User user = new User();
        user.setUsername("123123");
        user.setPassword("123123");

        user.save(mActivity, new SaveListener() {

            @Override
            public void onSuccess() {
                System.out.println("onSuccess");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                System.out.println("onFailure");
                System.out.println("arg1:"+arg1);
            }
        });
//        assertEquals(true, true);
    }

}
