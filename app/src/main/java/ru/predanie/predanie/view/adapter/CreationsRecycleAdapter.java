package ru.predanie.predanie.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import ru.predanie.predanie.R;
import ru.predanie.predanie.model.Creation;

/**
 * Created by NArtur on 07.04.2016.
 */
public class CreationsRecycleAdapter extends RecyclerView.Adapter {

  private List<Creation> creationList;

  public CreationsRecycleAdapter(List<Creation> creationList) {
    this.creationList = creationList;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View layoutView = LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.creation_item_layout, null);
    return new CreationsViewHolder(layoutView);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Creation creation = creationList.get(position);
    CreationsViewHolder creationsViewHolder = (CreationsViewHolder) holder;

    creationsViewHolder.creationName.setText(creation.getName());
    creationsViewHolder.creationAuthor.setText(creation.getAuthor());
  }

  @Override public int getItemCount() {
    return creationList.size();
  }

  public class CreationsViewHolder extends RecyclerView.ViewHolder {

    public TextView creationName;
    public TextView creationAuthor;

    public CreationsViewHolder(View itemView) {
      super(itemView);

      creationName = (TextView) itemView.findViewById(R.id.creation_name);
      creationAuthor = (TextView) itemView.findViewById(R.id.creation_author);
    }
  }
}
