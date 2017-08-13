package bled.navalny.com.api;

import java.util.List;

import bled.navalny.com.model.Alert;
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
    @POST("/api/auth/send_code.json")
    Call<Void> sendCode(@Body PhoneNumber phoneNumber);

    @POST("/api/auth/sign_in.json")
    Call<ResponseToken> signIn(@Body RegistrationInfo registrationInfo);

    @POST("/api/me/profile.json")
    Call<Void> refreshProfile(@Body Profile profile);

    @GET("/api/me/profile.json")
    Call<Profile> getProfile();

    @GET("/api/alerts.json")
    Call<List<Alert>> getAlerts(@Query("lat") Double latitude, @Query("lon") Double longitude);
}
