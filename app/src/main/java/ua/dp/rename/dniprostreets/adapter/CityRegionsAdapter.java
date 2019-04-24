package ua.dp.rename.dniprostreets.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import de.greenrobot.event.EventBus;
import ua.dp.rename.dniprostreets.R;
import ua.dp.rename.dniprostreets.entity.CityRegion;
import ua.dp.rename.dniprostreets.event.CityRegionClickedEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CityRegionsAdapter extends RecyclerView.Adapter<CityRegionsAdapter.ViewHolder> {

    private String formerCaptionFormat;

    private List<CityRegion> dataSet;

    private View.OnClickListener itemClickListener;

    public CityRegionsAdapter(String formerCaptionFormat) {
        this.formerCaptionFormat = formerCaptionFormat;

        dataSet = new ArrayList<>();
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
            holder.regionFormerName.setText(String.format(formerCaptionFormat, item.getOldAreaName()));
            holder.regionFormerName.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        TextView regionName;
        TextView regionFormerName;

        ViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.rootView);
            regionName = itemView.findViewById(R.id.regionTextView);
            regionFormerName = itemView.findViewById(R.id.regionFormerTextView);

            rootView.setOnClickListener(itemClickListener);
        }
    }
}
