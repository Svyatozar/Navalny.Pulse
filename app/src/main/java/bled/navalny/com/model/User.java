package bled.navalny.com.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by svyatozar on 13.08.17.
 */

public class User
{
	@SerializedName("age")
	public Integer age;
	@SerializedName("gender")
	public Integer gender;
	@SerializedName("name")
	public String name;
	@SerializedName("photo_url")
	public String photoUrl;
	@SerializedName("url")
	public String url;
}
