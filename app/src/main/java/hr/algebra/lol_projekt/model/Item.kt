package hr.algebra.lol_projekt.model

import android.media.Image

class Item (
    var _id : Long?,
    val name : String,
    val title : String,
    val blurb : String,
    val image : String,
    var read : Boolean
)