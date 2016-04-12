package ru.predanie.predanie.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import ru.predanie.predanie.R;

/**
 * Created by NArtur on 12.04.2016.
 */
public class ExpandableRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final String TAG = "ExpRecycleAdapter";

  public static final int HEADER = 0;
  public static final int CHILD = 1;

  private List<PartTrackItem> data;

  public ExpandableRecycleAdapter(List<PartTrackItem> data) {
    this.data = data;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    float dp = context.getResources().getDisplayMetrics().density;
    int subItemPaddingLeft = (int) (18 * dp);
    int subItemPaddingTopAndBottom = (int) (5 * dp);
    switch (viewType) {
      case HEADER:
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.parts_header, parent, false);
        ListHeaderViewHolder header = new ListHeaderViewHolder(view);
        return header;
      case CHILD:
        TextView itemTextView = new TextView(context);
        itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0,
            subItemPaddingTopAndBottom);
        itemTextView.setLayoutParams(
            new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new RecyclerView.ViewHolder(itemTextView) {
        };
    }

    return null;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    final PartTrackItem item = data.get(position);
    holder.itemView.setOnClickListener((v) -> {
      Log.d(TAG, item.trackId + "clicked");
    });
    switch (item.type) {
      case HEADER:
        final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
        itemController.refferalItem = item;
        itemController.header_title.setText(item.partName);
        if (item.invisibleChildren == null) {
          itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
        } else {
          itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
        }
        itemController.btn_expand_toggle.setOnClickListener(v -> {
          if (item.invisibleChildren == null) {
            item.invisibleChildren = new ArrayList<>();
            int count = 0;
            int pos = data.indexOf(itemController.refferalItem);
            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
              item.invisibleChildren.add(data.remove(pos + 1));
              count++;
            }
            notifyItemRangeRemoved(pos + 1, count);
            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
          } else {
            int pos = data.indexOf(itemController.refferalItem);
            int index = pos + 1;
            for (PartTrackItem i : item.invisibleChildren) {
              data.add(index, i);
              index++;
            }
            notifyItemRangeInserted(pos + 1, index - pos - 1);
            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
            item.invisibleChildren = null;
          }
        });
        break;
      case CHILD:
        TextView itemTextView = (TextView) holder.itemView;
        itemTextView.setText(data.get(position).trackName);
        break;
    }
  }

  @Override
  public int getItemViewType(int position) {
    return data.get(position).type;
  }

  @Override public int getItemCount() {
    return data.size();
  }

  private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
    public TextView header_title;
    public ImageView btn_expand_toggle;
    public PartTrackItem refferalItem;

    public ListHeaderViewHolder(View itemView) {
      super(itemView);
      header_title = (TextView) itemView.findViewById(R.id.header_title);
      btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
    }
  }

  public static class PartTrackItem {

    public int type;
    public long trackId;
    public String partName;
    public String trackName;
    public List<PartTrackItem> invisibleChildren;

    public PartTrackItem(int type, String partName) {
      this.partName = partName;
      this.type = type;
    }

    public PartTrackItem(int type, long trackId, String trackName) {
      this.trackId = trackId;
      this.trackName = trackName;
      this.type = type;
    }

  }
}
