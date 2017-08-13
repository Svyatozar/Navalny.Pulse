package bled.navalny.com.api;

import bled.navalny.com.model.PhoneNumber;
import bled.navalny.com.model.Profile;
import bled.navalny.com.model.RegistrationInfo;
import bled.navalny.com.model.ResponseToken;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by persick on 13/08/2017.
 */

public interface BledService {
    @POST("/auth/send_code")
    Call<Void> sendCode(@Body PhoneNumber phoneNumber);

    @POST("/auth/sign_in")
    ResponseToken signIn(@Body RegistrationInfo registrationInfo);

    @POST("/me/profile")
    Call<Void> refreshProfile();

    @GET("/me/profile")
    Call<Profile> getProfile(@Header("Pulse-Auth-Token") String token);
}
