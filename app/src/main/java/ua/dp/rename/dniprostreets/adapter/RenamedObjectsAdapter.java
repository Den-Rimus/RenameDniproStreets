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
import ua.dp.rename.dniprostreets.R;
import ua.dp.rename.dniprostreets.entity.RenamedObject;

public class RenamedObjectsAdapter extends RecyclerView.Adapter<RenamedObjectsAdapter.ViewHolder> {

    private Context context;
    private List<RenamedObject> dataSet = new ArrayList<>();

    public RenamedObjectsAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public RenamedObjectsAdapter(Context context, List<RenamedObject> dataSet) {
        this.context = context;
        this.dataSet.addAll(dataSet);
    }

    public void setAll(Collection<RenamedObject> dataSet) {
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_renamed_object_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RenamedObject item = dataSet.get(position);
        holder.type.setText(item.getType().getTypeKey());
        holder.newName.setText(item.getNewName());
        holder.oldName.setText(item.getOldName());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.typeTextView) TextView type;
        @Bind(R.id.renamedTextView) TextView newName;
        @Bind(R.id.formerTextView) TextView oldName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
