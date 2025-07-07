package br.edu.ifsp.dmo.gtuner.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo.gtuner.data.model.Sheet
import br.edu.ifsp.dmo.gtuner.ui.listener.SheetItemListener
import br.edu.ifsp.dmo.gtuner.R
import br.edu.ifsp.dmo.gtuner.databinding.SheetItemBinding


class SheetAdapter(private val listener: SheetItemListener) :
    RecyclerView.Adapter<SheetAdapter.ViewHolder>() {

    private var dataset: List<Sheet> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sheet_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sheet = dataset[position]

        holder.binding.tvName.text = sheet.name
        holder.binding.tvAuthor.text = sheet.author
        holder.binding.tvArrangment.text = sheet.arrangment

        holder.itemView.setOnClickListener {
            listener.onSheetClick(sheet)
        }
    }
    override fun getItemCount(): Int {
        return dataset.size
    }
    fun submitDataset(data: List<Sheet>) {
        dataset = data
        this.notifyDataSetChanged()
    }
    fun getDatasetItem(position: Int): Sheet {
        return dataset[position]
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: SheetItemBinding = SheetItemBinding.bind(view)
    }
}