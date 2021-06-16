package hr.algebra.lol_projekt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.lol_projekt.framework.startActivity
import hr.algebra.lol_projekt.model.Item
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class ItemAdapter(private val items: MutableList<Item>, private val context: Context) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val ivItem: ImageView = itemView.findViewById(R.id.ivItem)
        private val txtItem: TextView = itemView.findViewById(R.id.txtItem)
        fun bind(item: Item){
            Picasso.get()
                .load(item.image)
                .error(R.drawable.leaguehome)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)

            txtItem.text = item.title
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            context.startActivity<ItemPagerActivity>(ITEM_POSITION, position)
        }

        holder.bind(items[position])
    }

    override fun getItemCount() = items.size


}