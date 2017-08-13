package bled.navalny.com.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by svyatozar on 13.08.17.
 */

public class Alert {
	@SerializedName("id")
	public Integer id;
	@SerializedName("lon")
	public Double lon;
	@SerializedName("lat")
	public Double lat;
	@SerializedName("comment")
	public String comment;
	@SerializedName("created_at")
	public String createdAt;
	@SerializedName("user_id")
	public Integer userId;
	@SerializedName("user")
	public User user;

	public Date getDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date date = new Date();
		try
		{
			date = df.parse(createdAt);
		}
		catch (ParseException e) {
			Log.e("LOG", "ERROR", e);
		}

		return date;
	}
}