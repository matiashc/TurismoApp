package co.edu.ufps.turismoapp.vista

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.ufps.turismoapp.R
import co.edu.ufps.turismoapp.contoller.SitioAdapter
import co.edu.ufps.turismoapp.modelo.Sitio

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SitioTuristico.newInstance] factory method to
 * create an instance of this fragment.
 */
class SitioTuristico : Fragment() {
    lateinit var contenedor: RecyclerView
    lateinit var sitioAdapter: SitioAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sitio_turistico, container, false)
        contenedor = view.findViewById(R.id.contenedor)
        sitioAdapter = SitioAdapter(context,cargarDatos(),R.id.card)
        val linearLayout = LinearLayoutManager(context)
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        contenedor.adapter = sitioAdapter
        return view
    }


    fun cargarDatos(): ArrayList<Sitio>{
        val sitios: ArrayList<Sitio> = ArrayList()
        sitios.add(Sitio("XER","SITIO 1","SITIO 1 TURISTICO","https://www.eltiempo.com/files/article_main_1200/uploads/2023/04/10/64347a6b4f9ec.jpeg",0.0,0.0))
        sitios.add(Sitio("XER","SITIO 2","SITIO 2 TURISTICO","https://www.comparaonline.cl/blog-statics/cl/uploads/2016/12/lugares-turisticos-de-colombia.png",0.0,0.0))
        sitios.add(Sitio("XER","SITIO 3","SITIO 3 TURISTICO","https://cdn.inteligenciaviajera.com/wp-content/uploads/2020/04/sitios-turisticos-2.jpg",0.0,0.0))
        return sitios

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SitioTuristico.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SitioTuristico().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}