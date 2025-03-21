package com.example.examenfinal5.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenfinal5.R
import com.example.examenfinal5.adapter.GatoAdapter
import com.example.examenfinal5.adapter.OnClickGatoListener
import com.example.examenfinal5.api.RetrofitClient
import com.example.examenfinal5.databinding.DialogPersonalBinding
import com.example.examenfinal5.databinding.FragmentApiBinding
import com.example.examenfinal5.databinding.FragmentRoomBinding
import com.example.examenfinal5.entity.GatoEntity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RoomFragment : Fragment(), OnClickGatoListener {

    //Declaramos la variablesa utilizar
    private lateinit var characterAdapter: GatoAdapter
    private lateinit var binding: FragmentRoomBinding
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.api.getListadoGatos()

            if (response.isSuccessful && response.body() != null) {
                val gatos = response.body()!! // List<Results>

                // Filtra los gatos cuyo origin sea "United States"
                val gatosFiltrados = gatos.filter { it.origin == "United States" }

                // Convierte la lista filtrada a GatoEntity
                val gatoEntities = gatosFiltrados.map { gato ->
                    GatoEntity(
                        name = gato.name,
                        temperament = gato.temperament,
                        origin = gato.origin,
                        description = gato.description
                    )
                }

                withContext(Dispatchers.Main) {
                    characterAdapter = GatoAdapter(gatoEntities, this@RoomFragment)
                    linearLayoutManager = LinearLayoutManager(requireContext())
                    binding.recycler.apply {
                        layoutManager = linearLayoutManager
                        adapter = characterAdapter
                    }
                }

            } else {
                Log.e("gatos_LIST", "Error al obtener gatos: ${response.code()}")
            }
        }

    }


    // Para realizar onClick
    override fun onClick(gatoEntity: GatoEntity) {

        binding.tvNombre.text = "Nombre: ${gatoEntity.name}"
        binding.tvOrigen.text = "Origen: ${gatoEntity.origin}"
        binding.tvTemperamento.text = "Temperamento: ${gatoEntity.temperament}"
        binding.tvDescripcion.text = "Descripcion: ${gatoEntity.description}"



        // Inflamos el binding del layout del diálogo
        val dialogBinding = DialogPersonalBinding.inflate(layoutInflater)

        // Asignamos datos con binding
        with(dialogBinding) {


            tvnombre.text = "Nombre: ${gatoEntity.name}"
            tvOrigen.text = "Origen: ${gatoEntity.origin}"
            tvTemperamento.text = "Temperamento: ${gatoEntity.temperament}"
            tvDescripcion.text = "Descripcion: ${gatoEntity.description}"
        }

        // Mostrar el diálogo con la vista de binding
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Detalles del gato")
            .setView(dialogBinding.root)  // Aquí se usa binding.root
            .setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }
            .show()
    }

}

