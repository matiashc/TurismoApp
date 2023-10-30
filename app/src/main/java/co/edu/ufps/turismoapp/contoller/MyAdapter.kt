package co.edu.ufps.turismoapp.contoller

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import co.edu.ufps.turismoapp.vista.MiPerfil
import co.edu.ufps.turismoapp.vista.MisSitios
import co.edu.ufps.turismoapp.vista.SitioTuristico

class MyAdapter(var context: Context,
    fm: FragmentManager,
    val totalTabs: Int): FragmentPagerAdapter (fm){
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 ->{
                SitioTuristico()
            }
            1 ->{
                MisSitios()
            }
            2 -> {
                MiPerfil()
            }
            else -> getItem(position)
        }
    }


}