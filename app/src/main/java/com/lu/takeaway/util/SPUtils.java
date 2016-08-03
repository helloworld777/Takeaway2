package com.lu.takeaway.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.lu.takeaway.bean.UserBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SPUtils implements Constants {
    /**
     */
    public static final String FILE_NAME = "takeaway";

    public static void saveUserInfo(Context mActivity, UserBean user) {
        SPUtils.put(mActivity, USERNAME, user.lusername);
        SPUtils.put(mActivity, PASSWORD, user.lpwd);
        SPUtils.put(mActivity, ADDRESS, user.address);
        SPUtils.put(mActivity, PHONE_NUMBER, user.phone);
        SPUtils.put(mActivity, HEADER_IMG, user.header_img);
        SPUtils.put(mActivity, REGISTER_DATE, user.date);
    }

    public static UserBean getUserInfo(Context mActivity) {
        UserBean userBean = new UserBean();
        userBean.lusername = (String) SPUtils.get(mActivity, USERNAME, "");
        userBean.password = (String) SPUtils.get(mActivity, PASSWORD, "");
        userBean.address = (String) SPUtils.get(mActivity, ADDRESS, "");
        userBean.phone = (String) SPUtils.get(mActivity, PHONE_NUMBER, "");
        userBean.header_img = (String) SPUtils.get(mActivity, HEADER_IMG, "");
        userBean.date = (String) SPUtils.get(mActivity, REGISTER_DATE, "");
        return userBean;
    }

    /**
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {
        if (object == null) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }
//    public static <T>T get(Context context, String key, T defaultObject)
//    {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
//                Context.MODE_PRIVATE);
//
//        if (defaultObject instanceof String)
//        {
//            return sp.getString(key, (String) defaultObject);
//        } else if (defaultObject instanceof Integer)
//        {
//            return sp.getInt(key, (Integer) defaultObject);
//        } else if (defaultObject instanceof Boolean)
//        {
//            return sp.getBoolean(key, (Boolean) defaultObject);
//        } else if (defaultObject instanceof Float)
//        {
//            return sp.getFloat(key, (Float) defaultObject);
//        } else if (defaultObject instanceof Long)
//        {
//            return sp.getLong(key, (Long) defaultObject);
//        }
//
//        return null;
//    }

    /**
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     *
     *
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

}