package br.edu.infnet.meusgastos.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import br.edu.infnet.meusgastos.R
import br.edu.infnet.meusgastos.ViewPagerAdapter
import br.edu.infnet.meusgastos.databinding.ActivityMainBinding
import br.edu.infnet.meusgastos.login.ui.LoginActivity
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

//TODO testar o atualizaUsuario da viewModel dentro do onCreate e ver se puxa as despesas apenas do usuario logado e não do usuario anteriormente logado
class MainActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var imageList: List<Int>
    lateinit var pular: Button

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // initializing variables
        // of below line with their id.
        viewPager = findViewById(R.id.idViewPager)
        pular = findViewById(R.id.btn_pular)
        // on below line we are initializing
        // our image list and adding data to it.
        imageList = ArrayList<Int>()
        //TODO condicional para incluir imagem
        imageList = imageList + R.drawable.background3

        //imageList = imageList + R.drawable.img


        // on below line we are initializing our view
        // pager adapter and adding image list to it.
        viewPagerAdapter = ViewPagerAdapter(this@MainActivity, imageList)

        // on below line we are setting
        // adapter to our view pager.
        viewPager.adapter = viewPagerAdapter




        pular.setOnClickListener(){
            startLoginActivity()
        }

    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}