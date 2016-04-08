package ru.predanie.predanie.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import ru.predanie.predanie.model.Composition;

/**
 * Created by NArtur on 08.04.2016.
 */

public class CompositionDeserializer implements JsonDeserializer<List<Composition>> {
  @Override
  public List<Composition> deserialize(JsonElement je, Type typeOfT, JsonDeserializationContext jdc)
      throws JsonParseException {

    JsonArray data = je.getAsJsonObject().getAsJsonArray("compositions");
    List<Composition> myList = new ArrayList<>();

    for (JsonElement e : data)
    {
      myList.add((Composition)jdc.deserialize(e, Composition.class));
    }

    return myList;
  }
}
