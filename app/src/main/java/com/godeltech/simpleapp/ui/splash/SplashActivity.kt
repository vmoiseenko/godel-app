package com.godeltech.simpleapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.godeltech.simpleapp.R
import com.godeltech.simpleapp.ui.main.MainActivity


class SplashActivity : AppCompatActivity()  {

    val SPLASH_DELAY: Long = 5000
    var isPaused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        runBlocking {
//            delay(SPLASH_DELAY)
//        }

        launchApp()

    }

    override fun onResume() {
        super.onResume()
        isPaused = false
    }

    override fun onPause() {
        super.onPause()
        isPaused = true
    }

    private fun launchApp() {
        if(!isPaused && !isDestroyed){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
