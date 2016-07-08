package shadow.play.box.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedUtil {

	private SharedUtil() {}


	/**
	 * 설정값 저장 - Int형
	 * @param context
	 * @param name
	 * @param key
	 * @param value
	 */
	public static void putShared(Context context, String name, String key, int value) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * 설정값 저장 - Int형
	 * @param context
	 * @param name
	 * @param key
	 * @param value
	 */
	public static void putShared(Context context, String name, String key, long value) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	/**
	 * 설정값 저장 - Boolean형
	 * @param context
	 * @param name
	 * @param key
	 * @param value
	 */
	public static void putShared(Context context, String name, String key, boolean value) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 설정값 저장 - String형
	 * @param context
	 * @param name
	 * @param key
	 * @param value
	 */
	public static void putShared(Context context, String name, String key, String value) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 설정값 리턴 - String형
	 * @param context
	 * @param name
	 * @param key
	 * @return
	 */
	public static String getSharedStringValue(Context context, String name, String key) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return preferences.getString(key, null);
	}

	/**
	 * 설정값 리턴 - Int형
	 * @param context
	 * @param name
	 * @param key
	 * @return
	 */
	public static int getSharedIntValue(Context context, String name, String key) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return preferences.getInt(key, 0);
	}
	
	/**
	 * 설정값 리턴 - Long형
	 * @param context
	 * @param name
	 * @param key
	 * @return
	 */
	public static long getSharedLongValue(Context context, String name, String key) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return preferences.getLong(key, 0);
	}
	
	/**
	 * 설정값 리턴 - Float형
	 * @param context
	 * @param name
	 * @param key
	 * @return
	 */
	public static float getSharedFloatValue(Context context, String name, String key) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return preferences.getFloat(key, 0);
	}

	/**
	 * 설정값 리턴 - Boolean형
	 * @param context
	 * @param name
	 * @param key
	 * @return
	 */
	public static boolean getSharedBooleanValue(Context context, String name, String key) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return preferences.getBoolean(key, false);
	}
	
	public static boolean getSharedBooleanValue2(Context context, String name, String key) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return preferences.getBoolean(key, true);
	}
}
