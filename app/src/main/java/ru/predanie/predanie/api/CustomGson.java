package ru.predanie.predanie.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import ru.predanie.predanie.model.Composition;

/**
 * Created by NArtur on 08.04.2016.
 */
public class CustomGson {

  public static Gson getGsonCompositions() {
    Type listType = new TypeToken<List<Composition>>(){}.getType();
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(listType, new CompositionDeserializer())
        .create();
    return gson;
  }

  public static Gson getGsonCompDetail() {
    Type type = new TypeToken<Composition>(){}.getType();
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(type, new CompDetailDeserializer())
        .create();
    return gson;
  }

}
