package hr.algebra.lol_projekt

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.lol_projekt.framework.setBooleanPreference
import hr.algebra.lol_projekt.framework.startActivity

class LoLReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED, true)
        context.startActivity<HostActivity>()
    }
}