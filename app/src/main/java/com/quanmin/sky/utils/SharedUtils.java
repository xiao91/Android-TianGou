package com.quanmin.sky.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences保存数据
 * 
 */
public class SharedUtils {

	// xml文件名字
	public static final String SHARED_XML_NAME = "qm";

	// 登录标识
	public static final String TOKEN = "token";

	// 用户id
	public static final String USER_ID = "user_id";

	// 添加boolean
	public static boolean getSharedBoolean(Context context, String key, boolean defaultValue) {
		SharedPreferences mPreferences = context.getSharedPreferences(SHARED_XML_NAME, Context.MODE_PRIVATE);
		return mPreferences.getBoolean(key, defaultValue);
		
	}
	public static void setSharedBoolean(Context context, String key, boolean value) {
		SharedPreferences mPreferences = context.getSharedPreferences(SHARED_XML_NAME, Context.MODE_PRIVATE);
		mPreferences.edit().putBoolean(key, value).apply();
		
	}

	// 添加字符串
	public static void  setSharedString(Context context, String key, String value) {
		SharedPreferences mPreferences = context.getSharedPreferences(SHARED_XML_NAME, Context.MODE_PRIVATE);
		mPreferences.edit().putString(key, value).apply();
	}

	public static String getSharedString(Context context, String key, String defaultValue) {
		SharedPreferences mPreferences = context.getSharedPreferences(SHARED_XML_NAME, Context.MODE_PRIVATE);
		return mPreferences.getString(key, defaultValue);

	}

	// 添加int
	public static void  setSharedInt(Context context, String key, int value) {
		SharedPreferences mPreferences = context.getSharedPreferences(SHARED_XML_NAME, Context.MODE_PRIVATE);
		mPreferences.edit().putInt(key, value).apply();
	}

	public static int getSharedInt(Context context, String key, int defaultValue) {
		SharedPreferences mPreferences = context.getSharedPreferences(SHARED_XML_NAME, Context.MODE_PRIVATE);
		return mPreferences.getInt(key, defaultValue);

	}
}
