package ru.predanie.predanie.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.w3c.dom.Text;
import ru.predanie.predanie.R;
import ru.predanie.predanie.view.activity.CompositionDetailActivity;

/**
 * Created by NArtur on 12.04.2016.
 */
public class CompDescFragment extends Fragment {

  private String description;

  private TextView compDesc;

  public CompDescFragment() {}

  public static CompDescFragment newInstance() {
    CompDescFragment fragment = new CompDescFragment();
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    description = ((CompositionDetailActivity) getActivity()).getComposition().getDesc();
    description = Html.fromHtml(description).toString();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.compdesc_layout, container, false);
    setRetainInstance(true);

    compDesc = (TextView) rootView.findViewById(R.id.compdesc_text);
    compDesc.setText(description);

    return rootView;
  }

}
