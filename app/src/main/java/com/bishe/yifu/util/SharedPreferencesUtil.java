package com.bishe.yifu.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/1/1.
 */
public class SharedPreferencesUtil {

	public static void setBoolean(Context context, String type, String key, boolean value) {
		SharedPreferences preferences = context.getSharedPreferences(type, context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value).commit();

	}

	public static boolean getBoolean(Context context, String type, String key) {
		SharedPreferences preferences = context.getSharedPreferences(type, context.MODE_PRIVATE);
		return preferences.getBoolean(key, false);
	}

	public static boolean getBoolean(Context context, String type, String key, boolean defaults) {
		SharedPreferences preferences = context.getSharedPreferences(type, context.MODE_PRIVATE);
		return preferences.getBoolean(key, defaults);
	}

	public static void setString(Context context, String type, String key, String value) {
		SharedPreferences preferences = context.getSharedPreferences(type, context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value).commit();

	}

	public static String getString(Context context, String type, String key) {
		SharedPreferences preferences = context.getSharedPreferences(type, context.MODE_PRIVATE);
		return preferences.getString(key, "admin");

	}

	public static void setInt(Context context, String type, String key, int value) {
		SharedPreferences preferences = context.getSharedPreferences(type, context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(key, value).commit();

	}

	public static int getInt(Context context, String type, String key) {
		SharedPreferences preferences = context.getSharedPreferences(type, context.MODE_PRIVATE);
		return preferences.getInt(key, 0);

	}

	

}
