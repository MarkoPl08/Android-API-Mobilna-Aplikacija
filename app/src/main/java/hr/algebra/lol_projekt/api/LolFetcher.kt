package hr.algebra.lol_projekt.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.lol_projekt.LOL_PROJEKT_PROVIDER_CONTENT_URI
import hr.algebra.lol_projekt.LoLReceiver
import hr.algebra.lol_projekt.framework.sendBroadcast
import hr.algebra.lol_projekt.model.Item
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LolFetcher(private val context: Context) {

    private var lolApi : LolApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        lolApi = retrofit.create(LolApi::class.java)
    }

    fun fetchItems() {
        val request = lolApi.fetchItems()
        request.enqueue(object: Callback<List<LolItem>>{
            override fun onResponse(
                call: Call<List<LolItem>>,
                response: Response<List<LolItem>>
            ) {
                if (response.body() != null) {
                    populateItems(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<LolItem>>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }

        })
    }

    private fun populateItems(lolItems: List<LolItem>) {
        //GlobalScope.launch {

            lolItems.forEach {
                
                var values = ContentValues().apply {
                    put(Item::name.name, it.name)
                    put(Item::title.name, it.title)
                    put(Item::blurb.name, it.blurb)
                    put(Item::image.name, it.image)
                    put(Item::read.name, false)
                }
                context.contentResolver.insert(LOL_PROJEKT_PROVIDER_CONTENT_URI, values)
            }
            context.sendBroadcast<LoLReceiver>()
        //}
    }

}