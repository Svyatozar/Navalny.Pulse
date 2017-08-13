package bled.navalny.com;

import android.app.Application;
import android.content.Context;

import java.io.IOException;

import bled.navalny.com.api.BledService;
import bled.navalny.com.helpers.SharedPreferenceHelper;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by persick on 13/08/2017.
 */

public class ApplicationWrapper extends Application {
    public static final String  BASE_URL = "http://navalny2018pulsepublicapi.azurewebsites.net/api";

    public static BledService bledService;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context =  getApplicationContext();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
                                      @Override
                                      public Response intercept(Interceptor.Chain chain) throws IOException
                                      {
                                          Request original = chain.request();

                                          Request request = original.newBuilder()
                                                  .header("Pulse-Auth-Token", SharedPreferenceHelper.getToken())
                                                  .method(original.method(), original.body())
                                                  .build();

                                          return chain.proceed(request);
                                      }
                                  });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                //.client(httpClient.build()) // network requests logging
                .build();
        bledService = retrofit.create(BledService.class);
    }
}
