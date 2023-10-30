package co.edu.ufps.turismoapp.vista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import co.edu.ufps.turismoapp.R
import co.edu.ufps.turismoapp.contoller.MyAdapter
import com.google.android.material.tabs.TabLayout

class Contenedor : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var contenedorCard: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contenedor)
        tabLayout = findViewById(R.id.tab)
        contenedorCard = findViewById(R.id.contenedorcard)
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = MyAdapter(this, supportFragmentManager,tabLayout.tabCount)
        contenedorCard.adapter = adapter
        contenedorCard.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                contenedorCard.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }
    }
