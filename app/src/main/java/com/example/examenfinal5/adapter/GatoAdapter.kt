package com.example.examenfinal5.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examenfinal5.R
import com.example.examenfinal5.databinding.ItemGatoBinding
import com.example.examenfinal5.entity.GatoEntity
import com.squareup.picasso.Picasso

class GatoAdapter(private val gatos: List<GatoEntity>, private val listener: OnClickGatoListener) : RecyclerView.Adapter<GatoAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        // Inicializamos el binding (item)
        val binding = ItemGatoBinding.bind(view)
        // Llamamos a onClickListener
        fun setListener(gatoEntity: GatoEntity ){
            binding.root.setOnClickListener {
                listener.onClick(gatoEntity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GatoAdapter.ViewHolder {
        // Inflamos la vista del Intem del Recycler
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_gato, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GatoAdapter.ViewHolder, position: Int) {

        val gato = gatos.get(position)

        with(holder){
            setListener(gato)
            binding.nombre.text = gato.name
            binding.descripcion.text = gato.description
            Thread {
            }.start()
        }


    }


    override fun getItemCount(): Int = gatos.size


}