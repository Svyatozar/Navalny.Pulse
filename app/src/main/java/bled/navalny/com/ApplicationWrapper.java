package bled.navalny.com;

import android.app.Application;
import android.content.Context;

import bled.navalny.com.api.BledService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by persick on 13/08/2017.
 */

public class ApplicationWrapper extends Application {
    public static final String  BASE_URL = "http://navalny2018pulsepublicapi.azurewebsites.net/api";

    public static BledService bledService;
    public static Context context;

    public static String token;

    @Override
    public void onCreate() {
        super.onCreate();
        context =  getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(httpClient.build()) // network requests logging
                .build();
        bledService = retrofit.create(BledService.class);
    }
}
