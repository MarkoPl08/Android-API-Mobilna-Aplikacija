package hr.algebra.lol_projekt.framework

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.view.animation.AnimationUtils
import androidx.preference.PreferenceManager
import hr.algebra.lol_projekt.LOL_PROJEKT_PROVIDER_CONTENT_URI
import hr.algebra.lol_projekt.model.Item

fun View.applyAnimation(resourceId: Int) = startAnimation(AnimationUtils.loadAnimation(context,resourceId))

inline fun <reified T : Activity> Context.startActivity() = Intent(this, T::class.java).apply{
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(this)
}
inline fun <reified T : Activity> Context.startActivity(key: String, value: Int) = Intent(this, T::class.java).apply{
    putExtra(key, value)
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(this)
}
inline fun<reified T: BroadcastReceiver> Context.sendBroadcast() = sendBroadcast(Intent(this, T::class.java))

fun Context.setBooleanPreference(key: String, value: Boolean) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit()
        .putBoolean(key, value)
        .apply()

fun Context.getBooleanPreference(key: String) : Boolean
        = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(key, false)

fun Context.isOnline() : Boolean{
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    if (network!=null){
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if (network!=null){
            if (networkCapabilities != null) {
                return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        }
    }

    return false
}

fun Context.fetchItems() : MutableList<Item>{
    val items = mutableListOf<Item>()
    val cursor = contentResolver?.query(LOL_PROJEKT_PROVIDER_CONTENT_URI, null, null, null, null)

    if (cursor != null){
        while (cursor.moveToNext()){
            items.add(Item(
                cursor.getLong(cursor.getColumnIndex(Item::_id.name)),
                cursor.getString(cursor.getColumnIndex(Item::name.name)),
                cursor.getString(cursor.getColumnIndex(Item::title.name)),
                cursor.getString(cursor.getColumnIndex(Item::blurb.name)),
                cursor.getString(cursor.getColumnIndex(Item::image.name)),
                cursor.getInt(cursor.getColumnIndex(Item::read.name)) == 1
            ))
        }
    }
    return items
}