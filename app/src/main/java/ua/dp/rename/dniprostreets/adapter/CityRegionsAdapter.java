package ua.dp.rename.dniprostreets.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ua.dp.rename.dniprostreets.R;
import ua.dp.rename.dniprostreets.entity.CityRegion;
import ua.dp.rename.dniprostreets.event.CityRegionClickedEvent;

public class CityRegionsAdapter extends RecyclerView.Adapter<CityRegionsAdapter.ViewHolder> {

    private String formerNameCaptionFormat;

    private List<CityRegion> dataSet;

    private View.OnClickListener itemClickListener;

    public CityRegionsAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public CityRegionsAdapter(Context context, List<CityRegion> dataSet) {
        this.dataSet = dataSet;
        formerNameCaptionFormat = context.getString(R.string.region_former_name_caption);
        itemClickListener = v -> EventBus.getDefault().post(
                new CityRegionClickedEvent((CityRegion) v.getTag(R.id.recycler_view_item_tag)));
    }

    public void setAll(Collection<CityRegion> dataSet) {
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_region_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CityRegion item = dataSet.get(position);
        holder.rootView.setTag(R.id.recycler_view_item_tag, item);
        holder.regionName.setText(item.getNewAreaName());
        if (item.getNewAreaName().equals(item.getOldAreaName())) {
            holder.regionFormerName.setText("");
            holder.regionFormerName.setVisibility(View.INVISIBLE);
        } else {
            holder.regionFormerName.setText(String.format(formerNameCaptionFormat, item.getOldAreaName()));
            holder.regionFormerName.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.rootView) View rootView;
        @Bind(R.id.regionTextView) TextView regionName;
        @Bind(R.id.regionFormerTextView) TextView regionFormerName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            rootView.setOnClickListener(itemClickListener);
        }
    }
}
