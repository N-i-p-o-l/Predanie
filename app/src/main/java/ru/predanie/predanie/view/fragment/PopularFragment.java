package ru.predanie.predanie.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.predanie.predanie.R;
import ru.predanie.predanie.api.CompositionDeserializer;
import ru.predanie.predanie.api.CustomGson;
import ru.predanie.predanie.api.IApi;
import ru.predanie.predanie.api.ServiceGenerator;
import ru.predanie.predanie.model.Composition;
import ru.predanie.predanie.view.adapter.CreationsRecycleAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment {

  private final String TAG = "PopularFragment";

  private RecyclerView creationsRecycleView;
  private RecyclerView.LayoutManager layoutManager;
  private CreationsRecycleAdapter creationsRecycleAdapter;

  private List<Composition> compositionList;
  private IApi compositionApi;
  private Map<String, String> queryMap;

  public PopularFragment() {
    compositionList = new CopyOnWriteArrayList<>();
    compositionApi = ServiceGenerator.createService(IApi.class, CustomGson.getGsonCompositions());

    queryMap = new HashMap<>(4);
    queryMap.put("hd", "0");
    queryMap.put("limit", "20");
    queryMap.put("offset", "0");
    queryMap.put("type", "audio,music");
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Call<List<Composition>> call = compositionApi.getCompositions("popular", queryMap);
    call.enqueue(new Callback<List<Composition>>() {
      @Override
      public void onResponse(Call<List<Composition>> call, Response<List<Composition>> response) {
        Stream.of(response.body()).forEach(compositionList::add);
      }

      @Override public void onFailure(Call<List<Composition>> call, Throwable t) {
        Log.d(TAG, "GetPopular Network fetch failure");
      }
    });
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_popular, container, false);
    setRetainInstance(true);

    creationsRecycleView = (RecyclerView) rootView.findViewById(R.id.creationsRecyclerView);
    creationsRecycleAdapter = new CreationsRecycleAdapter(compositionList);
    creationsRecycleView.setAdapter(creationsRecycleAdapter);
    layoutManager = new LinearLayoutManager(getActivity());
    creationsRecycleView.setLayoutManager(layoutManager);

    return rootView;
  }

}
