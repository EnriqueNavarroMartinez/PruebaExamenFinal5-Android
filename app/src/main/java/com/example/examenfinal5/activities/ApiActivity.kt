package com.example.examenfinal5.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
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

class ApiActivity : AppCompatActivity(), OnClickGatoListener {

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
                val gatos = response.body()!! // List<Results>

                val gatoEntities = gatos.map { gato ->
                    GatoEntity(
                        name = gato.name,
                        temperament = gato.temperament,
                        origin = gato.origin,
                        description = gato.description
                    )
                }

                // 🖥️ Actualiza la UI en el hilo principal
                withContext(Dispatchers.Main) {
                    characterAdapter = GatoAdapter(gatoEntities,this@ApiActivity)
                    linearLayoutManager = LinearLayoutManager(this@ApiActivity)
                    binding.recycler.apply {
                        layoutManager = linearLayoutManager
                        adapter = characterAdapter
                    }
                }

            } else {
                Log.e("GATOS_LIST", "Error al obtener gatos: ${response.code()}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ApiActivity, "Error al obtener gatos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // ✅ Manejar el clic en un gato
    override fun onClick(gatoEntity: GatoEntity) {
        Toast.makeText(this, "Seleccionado: ${gatoEntity.name}", Toast.LENGTH_SHORT).show()


       // binding.imagen.setImageResource(gatoEntity.image)
    }
}
