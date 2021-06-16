package hr.algebra.lol_projekt

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import hr.algebra.lol_projekt.api.LolFetcher
import hr.algebra.lol_projekt.framework.sendBroadcast

private const val JOB_ID = 1

class LoLService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        LolFetcher(this).fetchItems()
    }
    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, LoLService::class.java, JOB_ID, intent)
        }
    }
}