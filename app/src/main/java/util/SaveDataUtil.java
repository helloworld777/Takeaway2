package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SaveDataUtil {

	
	public static final String APPINFO="appinfo";
	public static final String USERINFO="userinfo";
	
	public static String getString(Context context,String key){
		SharedPreferences sharedPreferences=context.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, "tourist");
	}
	public static void setString(Context context,String key,String value){
		SharedPreferences sharedPreferences=context.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		Editor dEditor=sharedPreferences.edit();
		dEditor.putString(key, value);
		dEditor.commit();
	}
	public static boolean getBoolean(Context context,String key){
		SharedPreferences sharedPreferences=context.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, false);
	}
	public static void setBoolean(Context context,String key,boolean value){
		SharedPreferences sharedPreferences=context.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		Editor dEditor=sharedPreferences.edit();
		dEditor.putBoolean(key, value);
		dEditor.commit();
	}
	public static boolean isFirstRun(Context contex){
		
		final SharedPreferences sharedPreferences=contex.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		int isFirst=sharedPreferences.getInt("isFirst", 0);
		if(isFirst==0){
			Editor  editor=sharedPreferences.edit();
			editor.putInt("isFirst", 1);
			editor.commit();
			return true;
		}
		return false;
	}
	public static int getAppLockState(Context contex){
		final SharedPreferences sharedPreferences=contex.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		int  isLock=sharedPreferences.getInt("isLock", 0);
		return isLock;
	}
	public static int getInt(Context contex,String key){
		final SharedPreferences sharedPreferences=contex.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		return sharedPreferences.getInt(key, 0);
	}
	public static void putInt(Context contex,String key,int value){
		final SharedPreferences sharedPreferences=contex.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		Editor dEditor=sharedPreferences.edit();
		dEditor.putInt(key, value);
		dEditor.commit();
	}
	/**
	 * @param contex
	 * @param isLock
	 */
	public static void setAppLock(Context contex,int isLock){
		final SharedPreferences sharedPreferences=contex.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putInt("isLock", isLock);
		editor.commit();
		
	}
	public static int getAppToBack(Context contex){
		final SharedPreferences sharedPreferences=contex.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		int  isLock=sharedPreferences.getInt("moveTaskToBack", 1);
		return isLock;
	}
	public static void setAppToBack(Context contex,int state){
		final SharedPreferences sharedPreferences=contex.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putInt("moveTaskToBack",state);
		editor.commit();
		
	}
	public static int getQieGeIndex(Context contex){
		final SharedPreferences sharedPreferences=contex.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		int  isLock=sharedPreferences.getInt("qieGeIndex", 0);
		return isLock;
	}
	public static void setQieGeIndex(Context contex,int state){
		final SharedPreferences sharedPreferences=contex.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putInt("qieGeIndex",state);
		editor.commit();
		
	}
	public static boolean getYaoYiYao(Context contex){
		final SharedPreferences sharedPreferences=contex.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		boolean  isLock=sharedPreferences.getBoolean("yaoyiyao", true);
		return isLock;
	}
	public static void setYaoYiYao(Context contex,boolean state){
		final SharedPreferences sharedPreferences=contex.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putBoolean("yaoyiyao",state);
		editor.commit();
		
	}
	

}
