package ua.dp.rename.dniprostreets.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_region_cell.view.*
import ua.dp.rename.dniprostreets.R
import ua.dp.rename.dniprostreets.entity.CityRegion
import ua.dp.rename.dniprostreets.util.invisible
import ua.dp.rename.dniprostreets.util.visible
import java.util.*

class CityRegionsAdapter(
      private val itemClickListener: (item: CityRegion) -> Unit
) : RecyclerView.Adapter<CityRegionsAdapter.ViewHolder>() {

   private val dataSet: MutableList<CityRegion> = mutableListOf()

   fun setAll(data: List<CityRegion>) {
      dataSet.clear()
      dataSet.addAll(data)
      notifyDataSetChanged()
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_region_cell, parent, false)

      return ViewHolder(view)
   }

   override fun getItemCount(): Int = dataSet.size

   override fun onBindViewHolder(holder: ViewHolder, position: Int): Unit = holder.bind(dataSet[position])

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

      fun bind(item: CityRegion) {
         with(itemView) {
            itemRootView.setOnClickListener { itemClickListener.invoke(item) }

            regionTextView.text = item.newAreaName

            if (item.newAreaName == item.oldAreaName) {
               regionFormerTextView.invisible()
            } else {
               regionFormerTextView.visible()
               regionFormerTextView.text = String.format(Locale.US,
                     context.getString(R.string.region_former_name_caption), item.oldAreaName)
            }
         }
      }
   }
}
