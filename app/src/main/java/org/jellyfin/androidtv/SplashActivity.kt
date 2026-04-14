package uk.rinzler.tv

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil3.load
import uk.rinzler.tv.ui.startup.StartupActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.splashLogo)
        logo.load(R.drawable.rinzler_loading_spinner)

        logo.postDelayed({
            startActivity(Intent(this, StartupActivity::class.java))
            finish()
        }, 1800)
    }
}
