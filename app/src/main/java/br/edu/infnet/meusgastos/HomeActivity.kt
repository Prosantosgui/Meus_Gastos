package br.edu.infnet.meusgastos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import br.edu.infnet.meusgastos.databinding.ActivityHomeBinding
import br.edu.infnet.meusgastos.login.ui.LoginActivity
import br.edu.infnet.meusgastos.main.ui.*
import br.edu.infnet.meusgastos.utils.toast
import br.edu.infnet.meusgastos.utils.nav

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val view = binding.root
        setupClickListeners()
        setContentView(view)


        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> navController.navigate(R.id.dashboardFragment)
                R.id.moedas -> navController.navigate(R.id.moedasFragment)
                R.id.resumo -> navController.navigate(R.id.resumoFragment)
                R.id.grafico -> navController.navigate(R.id.chartFragment)
                else ->{
                    throw Exception("Erro durante a navegação!")
                }

            }

            true
        }
       // MobileAds.initialize(this) {}
       // val adRequest = AdRequest.Builder().build()
       // binding.adView.loadAd(adRequest)

//        setupViewPager(binding.tabViewpager)
//        setSupportActionBar()

    }


    private fun setupClickListeners() {
        binding.apply {
            logoutBtn.setOnClickListener{
                viewModel.logout()
                startLoginActivity()
            }
            IvUserEmail.setOnClickListener {
                Toast.makeText(this@HomeActivity, viewModel.getCurrentUserEmail(), Toast.LENGTH_SHORT).show()
            }

            //            button.setOnClickListener{
//                lifecycleScope.launch {
//                    val key = "rememberKey"
//                    testeds.text = readData(key)
//
//                }
//            }
        }
    }


//    // This function is used to add items in arraylist and assign
//    // the adapter to view pager
//    private fun setupViewPager(viewpager: ViewPager) {
//        var adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
//
//        // LoginFragment is the name of Fragment and the Login
//        // is a title of tab
//        adapter.addFragment(DashboardFragment(), "Dashboard")
//        adapter.addFragment(EditarDespesaFragment(), "Editar")
//
//        // setting adapter to view pager.
//        viewpager.setAdapter(adapter)
//    }
//
//    class ViewPagerAdapter : FragmentPagerAdapter {
//
//        // objects of arraylist. One is of Fragment type and
//        // another one is of String type.*/
//        private final var fragmentList1: ArrayList<Fragment> = ArrayList()
//        private final var fragmentTitleList1: ArrayList<String> = ArrayList()
//
//        // this is a secondary constructor of ViewPagerAdapter class.
//        public constructor(supportFragmentManager: FragmentManager)
//                : super(supportFragmentManager)
//
//        // returns which item is selected from arraylist of fragments.
//        override fun getItem(position: Int): Fragment {
//            return fragmentList1.get(position)
//        }
//
//        // returns which item is selected from arraylist of titles.
//        override fun getPageTitle(position: Int): CharSequence {
//            return fragmentTitleList1.get(position)
//        }
//
//        // returns the number of items present in arraylist.
//        override fun getCount(): Int {
//            return fragmentList1.size
//        }
//
//        // this function adds the fragment and title in 2 separate  arraylist.
//        fun addFragment(fragment: Fragment, title: String) {
//            fragmentList1.add(fragment)
//            fragmentTitleList1.add(title)
//        }
//    }


    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}