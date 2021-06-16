package hr.algebra.lol_projekt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.lol_projekt.model.Item
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class ItemPagerAdapter(private val items: MutableList<Item>, private val context: Context)
    : RecyclerView.Adapter<ItemPagerAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem : ImageView = itemView.findViewById(R.id.ivItem)
        private val ivRead : ImageView = itemView.findViewById(R.id.ivRead)
        private val tvNameItem : TextView = itemView.findViewById(R.id.tvNameItem)
        private val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
        private val tvBlurb : TextView = itemView.findViewById(R.id.tvBlurb)

        fun bind(item: Item){
            Picasso.get()
                .load(item.image)
                .error(R.drawable.leaguehome)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
            ivRead.setImageResource(if (item.read)R.drawable.teemo else R.drawable.devteemo)
            tvNameItem.text = item.name
            tvTitle.text = item.title
            tvBlurb.text = item.blurb
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pager, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size
}