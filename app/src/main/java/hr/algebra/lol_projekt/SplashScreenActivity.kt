package hr.algebra.lol_projekt

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.lol_projekt.R.layout.activity_splash_screen
import hr.algebra.lol_projekt.framework.applyAnimation
import hr.algebra.lol_projekt.framework.getBooleanPreference
import hr.algebra.lol_projekt.framework.isOnline
import hr.algebra.lol_projekt.framework.startActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.util.concurrent.DelayQueue

private const val DELAY : Long = 3000
const val DATA_IMPORTED = "hr.algebra.lol_projekt.data_imported"

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private var backPressedTime = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_splash_screen)
        startAnimations()
        redirect()

    }

    override fun onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
        }else{
            Toast.makeText(applicationContext,"No cant leave",Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis().toInt()
    }

    private fun startAnimations() {
        ivSplash.applyAnimation(R.anim.rotate)
        tvSplash.applyAnimation(R.anim.blink)
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    private fun redirect() {
        if (getBooleanPreference(DATA_IMPORTED)) {
            Handler(Looper.getMainLooper()).postDelayed(
                    {startActivity<HostActivity>()},
                    DELAY
            )
        } else {
            if (isOnline()) {
                // start service
                Intent(this, LoLService::class.java).apply {
                    LoLService.enqueueWork(this@SplashScreenActivity, this)
                }
            }else{
                Toast.makeText(this, getString(R.string.please_connect_to_internet), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}