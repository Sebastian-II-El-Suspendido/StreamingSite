package com.example.streamingsite

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.VideoView

class ScreenSplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_splash_screen)



        val videoView = findViewById<VideoView>(R.id.videoView)
       val videoPath = "android.resource://" + packageName + "/" + R.raw.intronitflex
        videoView.setVideoURI(Uri.parse(videoPath))

        videoView.setOnCompletionListener {
            goToMainActivity()
        }

        videoView.start()
    }


    private fun goToMainActivity() {
        startActivity(Intent(this, Login::class.java))
        finish()
    }
}