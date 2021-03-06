package ru.predanie.predanie.view.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.annimon.stream.Stream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.predanie.predanie.R;
import ru.predanie.predanie.api.CustomGson;
import ru.predanie.predanie.api.IApi;
import ru.predanie.predanie.api.ServiceGenerator;
import ru.predanie.predanie.model.Composition;
import ru.predanie.predanie.view.adapter.CompositionRecycleAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment {

  private final String TAG = "PopularFragment";

  private RecyclerView compositionRecycleView;
  private RecyclerView.LayoutManager layoutManager;
  private CompositionRecycleAdapter compositionRecycleAdapter;
  private ProgressBar progressBar;
  private FrameLayout frameLayout;

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
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_popular, container, false);
    setRetainInstance(true);

    compositionRecycleView = (RecyclerView) rootView.findViewById(R.id.compositionRecyclerView);
    progressBar = (ProgressBar) rootView.findViewById(R.id.popular_spinner);
    frameLayout = (FrameLayout) rootView.findViewById(R.id.popularRootLayout);

    layoutManager = new LinearLayoutManager(getActivity());
    compositionRecycleView.setLayoutManager(layoutManager);
    getCompositions();

    return rootView;
  }

  private void getCompositions() {
    progressBar.setVisibility(View.VISIBLE);
    Call<List<Composition>> call = compositionApi.getCompositions("popular", queryMap);
    call.enqueue(new Callback<List<Composition>>() {
      @Override
      public void onResponse(Call<List<Composition>> call, Response<List<Composition>> response) {
        Stream.of(response.body()).forEach(compositionList::add);
        compositionRecycleAdapter = new CompositionRecycleAdapter(compositionList);
        compositionRecycleView.setAdapter(compositionRecycleAdapter);
        progressBar.setVisibility(View.GONE);
      }

      @Override public void onFailure(Call<List<Composition>> call, Throwable t) {
        progressBar.setVisibility(View.GONE);
        Log.d(TAG, "GetPopular Network fetch failure");
        Snackbar.make(frameLayout, getString(R.string.error_network), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry), v -> {
                getCompositions();
            }).show();
      }
    });
  }
}
