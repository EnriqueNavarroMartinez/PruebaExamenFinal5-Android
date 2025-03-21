package com.example.examenfinal5.fragments

import com.example.examenfinal5.adapter.OnClickGatoListener
import com.example.examenfinal5.entity.GatoEntity


interface GatoListener : OnClickGatoListener {
    fun onCharacterSeleccionado(gatoEntity: GatoEntity)
}

