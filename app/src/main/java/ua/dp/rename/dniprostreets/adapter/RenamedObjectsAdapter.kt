package ua.dp.rename.dniprostreets.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_renamed_object_cell.view.*
import ua.dp.rename.dniprostreets.R
import ua.dp.rename.dniprostreets.entity.RenamedObject

class RenamedObjectsAdapter(
      private val itemClickListener: (RenamedObject) -> Unit
): RecyclerView.Adapter<RenamedObjectsAdapter.ViewHolder>() {

   private val dataSet = mutableListOf<RenamedObject>()

   fun setAll(data: List<RenamedObject>) {
      dataSet.clear()
      dataSet.addAll(data)
      notifyDataSetChanged()
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_renamed_object_cell, parent, false)

      return ViewHolder(view)
   }

   override fun getItemCount(): Int = dataSet.size

   override fun onBindViewHolder(holder: ViewHolder, position: Int): Unit = holder.bind(dataSet[position])

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

      fun bind(item: RenamedObject) {
         with(itemView) {
            itemRootView.setOnClickListener { itemClickListener.invoke(item) }

            typeTextView.text = context.getString(item.type.captionResId)
            renamedTextView.text = item.oldName
            formerTextView.text = item.newName
         }
      }
   }
}
