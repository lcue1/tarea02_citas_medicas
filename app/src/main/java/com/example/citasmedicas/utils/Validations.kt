package com.example.citasmedicas.utils

import android.util.Log
import android.widget.EditText

object Validations{
    fun validateEditTexts(editTexts:ArrayList<EditText>):Boolean{

        val invalid = editTexts.find{
            val isEmpty = it.text.toString() == ""
            if (isEmpty) {
                it.setError("Campo requerido")
            }
            isEmpty
        }
        if (invalid==null) return true
        return false
    }
}