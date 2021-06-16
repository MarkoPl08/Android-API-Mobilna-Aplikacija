package hr.algebra.lol_projekt

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_media.*
import java.lang.Exception

class MediaActivity : AppCompatActivity() {
    private var mp: MediaPlayer? = null
    private var currentSong: MutableList<Int> = mutableListOf(R.raw.legendsneverdie)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        controlSound(currentSong[0])

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    private fun controlSound(id: Int){

        btn_play.setOnClickListener {

            if (mp == null){
                mp = MediaPlayer.create(this,id)
                Log.d("MainActivity","ID: ${mp!!.audioSessionId}")

                initialiseSeekBar()
            }
            mp?.start()
            Log.d("MainActivity","Duration: ${mp!!.duration/1000} seconds")
        }

        btn_pause.setOnClickListener {
            if (mp !== null)mp?.pause()
            Log.d("MainActivity","Paused: ${mp!!.currentPosition/1000} seconds")
        }

        btn_stop.setOnClickListener {
            if (mp !== null){
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }
    private fun initialiseSeekBar() {
        seekbar.max = mp!!.duration

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object: Runnable {
            override fun run(){
                try {
                    seekbar.progress = mp!!.currentPosition
                    handler.postDelayed(this,1000)
                }
                catch (e: Exception){
                    seekbar.progress = 0
                }
            }
        }, 0)
    }

}