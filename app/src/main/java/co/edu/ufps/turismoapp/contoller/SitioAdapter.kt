package co.edu.ufps.turismoapp.contoller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import co.edu.ufps.turismoapp.R
import co.edu.ufps.turismoapp.modelo.Sitio
import com.squareup.picasso.Picasso

class SitioAdapter(var context: Context?, var dataSet:ArrayList<Sitio>, var recurso: Int): Adapter<SitioAdapter.SitioViewHolder>(){
    class SitioViewHolder (view: View): ViewHolder(view){
        var nombre: TextView
        var descripcion: TextView
        var foto: ImageView
        init {
            nombre = view.findViewById(R.id.nombre)
            descripcion = view.findViewById(R.id.descripcion)
            foto = view.findViewById(R.id.foto)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SitioViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)
        val view: View = layoutInflate.inflate(R.layout.card,parent,false)
        return SitioViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: SitioViewHolder, position: Int) {
       holder.nombre.text = dataSet.get(position).nombre
        holder.descripcion.text = dataSet.get(position).descripcion
        Picasso.get().load(dataSet.get(position).foto).into(holder.foto);

    }

}