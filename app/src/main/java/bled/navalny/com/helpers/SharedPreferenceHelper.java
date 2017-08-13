package bled.navalny.com.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import bled.navalny.com.ApplicationWrapper;

/**
 * Created by svyatozar on 13.08.17.
 */

public class SharedPreferenceHelper {
	private final static String PREF_FILE = "PREF";
	private final static String TOKEN_KEY = "TOKEN_KEY";

	public static void setToken(String token) {
		setSharedPreferenceString(ApplicationWrapper.context, TOKEN_KEY, token);
	}

	public static String getToken() {
		return getSharedPreferenceString(ApplicationWrapper.context, TOKEN_KEY, "");
	}

	/**
	 * Set a string shared preference
	 * @param key - Key to set shared preference
	 * @param value - Value for the key
	 */
	public static void setSharedPreferenceString(Context context, String key, String value){
		SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.apply();
	}

	/**
	 * Set a integer shared preference
	 * @param key - Key to set shared preference
	 * @param value - Value for the key
	 */
	public static void setSharedPreferenceInt(Context context, String key, int value){
		SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		editor.apply();
	}

	/**
	 * Set a Boolean shared preference
	 * @param key - Key to set shared preference
	 * @param value - Value for the key
	 */
	public static void setSharedPreferenceBoolean(Context context, String key, boolean value){
		SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	/**
	 * Get a string shared preference
	 * @param key - Key to look up in shared preferences.
	 * @param defValue - Default value to be returned if shared preference isn't found.
	 * @return value - String containing value of the shared preference if found.
	 */
	public static String getSharedPreferenceString(Context context, String key, String defValue){
		SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
		return settings.getString(key, defValue);
	}

	/**
	 * Get a integer shared preference
	 * @param key - Key to look up in shared preferences.
	 * @param defValue - Default value to be returned if shared preference isn't found.
	 * @return value - String containing value of the shared preference if found.
	 */
	public static int getSharedPreferenceInt(Context context, String key, int defValue){
		SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
		return settings.getInt(key, defValue);
	}

	/**
	 * Get a boolean shared preference
	 * @param key - Key to look up in shared preferences.
	 * @param defValue - Default value to be returned if shared preference isn't found.
	 * @return value - String containing value of the shared preference if found.
	 */
	public static boolean getSharedPreferenceBoolean(Context context, String key, boolean defValue){
		SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
		return settings.getBoolean(key, defValue);
	}
}