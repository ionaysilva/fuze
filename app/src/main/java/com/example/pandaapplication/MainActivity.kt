package com.example.pandaapplication

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.pandaapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installMySplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun installMySplashScreen() {
        installSplashScreen().apply {
            setOnExitAnimationListener { splashScreen ->
                ObjectAnimator.ofFloat(
                    splashScreen.view,
                    View.TRANSLATION_Y,
                    0f, splashScreen.view.height.toFloat()
                ).apply {
                    interpolator = DecelerateInterpolator()
                    duration = 1000L
                    doOnEnd { splashScreen.remove() }
                    start()
                }
            }
        }
    }

}