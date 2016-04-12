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
import ru.predanie.predanie.R;
import ru.predanie.predanie.view.adapter.ExpandableRecycleAdapter;

/**
 * Created by NArtur on 12.04.2016.
 */
public class CompPartsFragment extends Fragment {

  private RecyclerView recyclerView;

  public CompPartsFragment() {}

  public static CompPartsFragment newInstance() {
    CompPartsFragment fragment = new CompPartsFragment();
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.comparts_layout, container, false);
    setRetainInstance(true);

    recyclerView = (RecyclerView) rootView.findViewById(R.id.compPartsRecyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    List<ExpandableRecycleAdapter.PartTrackItem> data = new ArrayList<>();
    data.add(new ExpandableRecycleAdapter.PartTrackItem(ExpandableRecycleAdapter.HEADER, 0, "Book 1", ""));
    data.add(new ExpandableRecycleAdapter.PartTrackItem(ExpandableRecycleAdapter.CHILD, 555, "", "Track 1"));

    recyclerView.setAdapter(new ExpandableRecycleAdapter(data));

    return rootView;
  }
}
