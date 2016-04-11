package ru.predanie.predanie.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import org.json.JSONArray;
import ru.predanie.predanie.model.Composition;
import ru.predanie.predanie.model.Part;
import ru.predanie.predanie.model.Track;

/**
 * Created by NArtur on 11.04.2016.
 */
public class CompDetailDeserializer implements JsonDeserializer<Composition> {
  @Override
  public Composition deserialize(JsonElement je, Type typeOfT, JsonDeserializationContext jdc)
      throws JsonParseException {

    final JsonObject jO = je.getAsJsonObject().getAsJsonObject("data");

    //Composition composition = jdc.deserialize(jO, Composition.class);
    Composition composition = new Composition();
    composition.setId(jO.get("id").getAsLong());
    composition.setName(jO.get("name").getAsString());
    composition.setDesc(jO.get("desc").getAsString());
    composition.setAuthor(jO.get("author_name").getAsString());
    composition.setImageMediumUrl(jO.get("img_medium").getAsString());
    composition.setImageBigUrl(jO.get("img_big").getAsString());

    Track[] tracks = jdc.deserialize(jO.get("tracks"), Track[].class);
    Part[] parts = jdc.deserialize(jO.get("parts"), Part[].class);

    composition.setTracks(tracks);
    composition.setParts(parts);

    return composition;
  }
}
