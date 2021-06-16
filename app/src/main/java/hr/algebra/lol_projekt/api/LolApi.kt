package hr.algebra.lol_projekt.api


import hr.algebra.lol_projekt.api.LolItem
import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://pastebin.com/"
interface LolApi {
    @GET("raw/36v5gwac")
    fun fetchItems() : Call<List<LolItem>>
}