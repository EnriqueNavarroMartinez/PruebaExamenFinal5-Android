package com.example.examenfinal5.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenfinal5.R
import com.example.examenfinal5.adapter.GatoAdapter
import com.example.examenfinal5.adapter.OnClickGatoListener
import com.example.examenfinal5.api.RetrofitClient
import com.example.examenfinal5.databinding.FragmentApiBinding
import com.example.examenfinal5.entity.GatoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ApiFragment : Fragment(), OnClickGatoListener {

    //Declaramos la variablesa utilizar
    private lateinit var characterAdapter: GatoAdapter
    private lateinit var binding: FragmentApiBinding
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.api.getListadoGatos()

            if (response.isSuccessful && response.body() != null) {
                val gatos = response.body()!! // Esto ahora es List<Results>
                val gatoEntities = gatos.map { gato ->
                    GatoEntity(
                        name = gato.name,
                        temperament = gato.temperament,
                        origin = gato.origin,
                        description = gato.description
                    )
                }

                withContext(Dispatchers.Main) {
                    characterAdapter = GatoAdapter(gatoEntities,this@ApiFragment)
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
        val inflater = LayoutInflater.from(requireContext())
        val layout = inflater.inflate(R.layout.toast_layout, requireActivity().findViewById(R.id.lytLayout))

        val mensaje = layout.findViewById<TextView>(R.id.texto)
        mensaje.text = "Seleccion: ${gatoEntity.name}, ${gatoEntity.origin}, ${gatoEntity.temperament}, ${gatoEntity.description}"

        val toast = Toast(requireContext()).apply {
            duration = Toast.LENGTH_LONG
            view = layout
        }

        toast.show()
    }
}


