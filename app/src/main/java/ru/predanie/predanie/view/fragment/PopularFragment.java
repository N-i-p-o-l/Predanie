package ru.predanie.predanie.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import ru.predanie.predanie.R;
import ru.predanie.predanie.model.Creation;
import ru.predanie.predanie.view.adapter.CreationsRecycleAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment {

  private RecyclerView creationsRecycleView;
  private RecyclerView.LayoutManager layoutManager;
  private CreationsRecycleAdapter creationsRecycleAdapter;
  private List<Creation> creationList;

  public PopularFragment() {
    //Mock
    Creation mockCreation = new Creation();
    mockCreation.setAuthor("Test Author");
    mockCreation.setName("Test Name");
    creationList = new CopyOnWriteArrayList<>();
    creationList.add(mockCreation);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_popular, container, false);
    setRetainInstance(true);

    creationsRecycleView = (RecyclerView) rootView.findViewById(R.id.creationsRecyclerView);
    creationsRecycleAdapter = new CreationsRecycleAdapter(creationList);
    creationsRecycleView.setAdapter(creationsRecycleAdapter);
    layoutManager = new LinearLayoutManager(getActivity());
    creationsRecycleView.setLayoutManager(layoutManager);

    return rootView;
  }

}
