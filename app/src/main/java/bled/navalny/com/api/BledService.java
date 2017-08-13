package bled.navalny.com.api;

import bled.navalny.com.model.PhoneNumber;
import bled.navalny.com.model.Profile;
import bled.navalny.com.model.RegistrationInfo;
import bled.navalny.com.model.ResponseToken;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by persick on 13/08/2017.
 */

public interface BledService {
    @POST("/auth/send_code.json")
    Call<Void> sendCode(@Body PhoneNumber phoneNumber);

    @POST("/auth/sign_in.json")
    ResponseToken signIn(@Body RegistrationInfo registrationInfo);

    @POST("/me/profile.json")
    Call<Void> refreshProfile();

    @GET("/me/profile.json")
    Call<Profile> getProfile();

    @GET("/alerts.json")
    Call<Profile> getAlerts(@Query("lat") Float latitude, @Query("lon") Float longitude);
}
