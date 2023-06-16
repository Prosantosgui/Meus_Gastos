package br.edu.infnet.meusgastos

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import br.edu.infnet.meusgastos.main.ui.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

       // val backgroundImage: ImageView = findViewById(R.idS)
      //  val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)

        Handler(Looper.getMainLooper()
        ).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}