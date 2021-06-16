package hr.algebra.lol_projekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.algebra.lol_projekt.framework.applyAnimation
import hr.algebra.lol_projekt.framework.fetchItems
import hr.algebra.lol_projekt.model.Item
import kotlinx.android.synthetic.main.activity_item_pager.*
import kotlinx.android.synthetic.main.item_pager.*

const val ITEM_POSITION = "hr.algebra.lol_projekt.item_position"

class ItemPagerActivity : AppCompatActivity() {

    private lateinit var items: MutableList<Item>
    private var itemPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_pager)

        init()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun init() {
        items = fetchItems()
        itemPosition = intent.getIntExtra(ITEM_POSITION, 0)

        viewPager.adapter = ItemPagerAdapter(items, this)
        viewPager.currentItem = itemPosition
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}