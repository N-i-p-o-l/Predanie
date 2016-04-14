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
import ru.predanie.predanie.model.Part;
import ru.predanie.predanie.model.Track;
import ru.predanie.predanie.view.activity.CompositionDetailActivity;
import ru.predanie.predanie.view.adapter.ExpandableRecycleAdapter;

/**
 * Created by NArtur on 12.04.2016.
 */
public class CompPartsFragment extends Fragment {

  private RecyclerView recyclerView;

  private ArrayList<ExpandableRecycleAdapter.PartTrackItem> data;
  private Track[] tracks;
  private Part[] parts;
  private String imageUrl;
  private String compName;

  public CompPartsFragment() {
    data = new ArrayList<>();
  }

  public static CompPartsFragment newInstance() {
    CompPartsFragment fragment = new CompPartsFragment();
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    tracks = ((CompositionDetailActivity) getActivity()).getComposition().getTracks();
    parts = ((CompositionDetailActivity) getActivity()).getComposition().getParts();
    imageUrl = ((CompositionDetailActivity) getActivity()).getComposition().getImageBigUrl();
    compName = ((CompositionDetailActivity) getActivity()).getComposition().getName();

    fillList();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.comparts_layout, container, false);
    setRetainInstance(true);

    recyclerView = (RecyclerView) rootView.findViewById(R.id.compPartsRecyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    recyclerView.setAdapter(new ExpandableRecycleAdapter(data, imageUrl, compName));

    return rootView;
  }

  private void fillList() {
    if (parts != null) {
      for (int i = 0; i < parts.length; i++) {
        ExpandableRecycleAdapter.PartTrackItem items = new ExpandableRecycleAdapter
            .PartTrackItem(ExpandableRecycleAdapter.HEADER, parts[i].getName());
        items.invisibleChildren = new ArrayList<>();
        for (int j = 0; j < tracks.length; j++) {
          if (tracks[j].getParent() == parts[i].getId()) {
            items.invisibleChildren.add(new ExpandableRecycleAdapter.PartTrackItem(ExpandableRecycleAdapter.CHILD,
                tracks[j].getId(), tracks[j].getName(), tracks[j].getUrl()));
          }
        }
        data.add(items);
      }
    } else {
      for (int j = 0; j < tracks.length; j++) {
        data.add(new ExpandableRecycleAdapter
            .PartTrackItem(ExpandableRecycleAdapter.HEADER, tracks[j].getName(), tracks[j].getId()
            ,tracks[j].getUrl()));
      }
    }
  }
}
