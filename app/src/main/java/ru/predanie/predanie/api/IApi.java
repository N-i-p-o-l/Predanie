package ru.predanie.predanie.api;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import ru.predanie.predanie.model.Composition;

/**
 * Created by NArtur on 07.04.2016.
 */
public interface IApi {

  @Headers({"Content-Type: application/json"})
  @GET("/api/mobile/v1/compositions/{category}/")
  Call<List<Composition>> getCompositions(@Path("category") String category,
      @QueryMap Map<String, String> options);

  @GET("/api/mobile/v1/composition-single/")
  Call<Composition> getCompositionDetail(@Query("composition_id") long id);
}
