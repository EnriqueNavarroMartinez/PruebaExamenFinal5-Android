package com.example.examenfinal5.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenfinal5.adapter.GatoAdapter
import com.example.examenfinal5.adapter.OnClickGatoListener
import com.example.examenfinal5.api.RetrofitClient
import com.example.examenfinal5.databinding.ActivityApiBinding
import com.example.examenfinal5.entity.GatoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomActivity : AppCompatActivity(), OnClickGatoListener {

    private lateinit var characterAdapter: GatoAdapter
    private lateinit var binding: ActivityApiBinding
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el layout con view binding
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar RecyclerView
        linearLayoutManager = LinearLayoutManager(this)
        characterAdapter = GatoAdapter(emptyList(), this)
        binding.recycler.apply {
            layoutManager = linearLayoutManager
            adapter = characterAdapter
        }

        // 🚀 Llamada a la API para obtener los gatos
        fetchGatosFromApi()
    }

    private fun fetchGatosFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.api.getListadoGatos()

            if (response.isSuccessful && response.body() != null) {
                val gatos = response.body()!!

                // 🐱 Filtrar gatos con origen "United States"
                val gatosFiltrados = gatos.filter { gato ->
                    gato.origin.equals("United States", ignoreCase = true)
                }

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
                    characterAdapter = GatoAdapter(gatoEntities,this@RoomActivity)
                    linearLayoutManager = LinearLayoutManager(this@RoomActivity)
                    binding.recycler.apply {
                        layoutManager = linearLayoutManager
                        adapter = characterAdapter
                    }

                }

            } else {
                Log.e("GATOS_LIST", "Error al obtener gatos: ${response.code()}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RoomActivity, "Error al obtener gatos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onClick(gatoEntity: GatoEntity) {
        Toast.makeText(this, "Seleccionado: ${gatoEntity.name}", Toast.LENGTH_SHORT).show()
    }
}
