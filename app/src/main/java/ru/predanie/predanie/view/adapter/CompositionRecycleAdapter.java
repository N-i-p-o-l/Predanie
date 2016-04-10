package ru.predanie.predanie.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import ru.predanie.predanie.R;
import ru.predanie.predanie.model.Composition;

/**
 * Created by NArtur on 07.04.2016.
 */
public class CompositionRecycleAdapter extends RecyclerView.Adapter {

  private List<Composition> compositionList;

  public CompositionRecycleAdapter(List<Composition> compositionList) {
    this.compositionList = compositionList;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View layoutView = LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.creation_item_layout, null);
    return new CreationsViewHolder(layoutView);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Composition composition = compositionList.get(position);
    CreationsViewHolder creationsViewHolder = (CreationsViewHolder) holder;

    creationsViewHolder.creationName.setText(composition.getName());
    creationsViewHolder.creationAuthor.setText(composition.getAuthor());
    Glide.with(creationsViewHolder.compositionImage.getContext())
        .load(composition.getImageUrl())
        .into(creationsViewHolder.compositionImage);
  }

  @Override public int getItemCount() {
    return compositionList.size();
  }

  public class CreationsViewHolder extends RecyclerView.ViewHolder {

    public TextView creationName;
    public TextView creationAuthor;
    public ImageView compositionImage;

    public CreationsViewHolder(View itemView) {
      super(itemView);

      creationName = (TextView) itemView.findViewById(R.id.creation_name);
      creationAuthor = (TextView) itemView.findViewById(R.id.creation_author);
      compositionImage = (ImageView) itemView.findViewById(R.id.creation_image);
    }
  }
}
