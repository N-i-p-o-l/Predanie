package ru.predanie.predanie.api;

import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NArtur on 08.04.2016.
 */
public class ServiceGenerator {
  public static final String API_BASE_URL = "http://predanie.ru";

  public static <T> T createService(Class<T> serviceClass) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    return retrofit.create(serviceClass);
  }

  public static <T> T createService(Class<T> serviceClass, Gson gson) {
      Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();
    return retrofit.create(serviceClass);
  }
}
