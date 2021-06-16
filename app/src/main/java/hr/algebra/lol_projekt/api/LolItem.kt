package hr.algebra.lol_projekt.api

import com.google.gson.annotations.SerializedName

class LolItem(
    @SerializedName("name") val name : String,
    @SerializedName("title") val title : String,
    @SerializedName("blurb") val blurb : String,
    @SerializedName("image") val image : String
)