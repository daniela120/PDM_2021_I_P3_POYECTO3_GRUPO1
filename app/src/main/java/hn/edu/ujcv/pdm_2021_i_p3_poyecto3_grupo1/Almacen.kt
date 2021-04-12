package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_almacen.*
import kotlinx.android.synthetic.main.activity_menu.*

class Almacen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_almacen)
        btn_RegresarAl.setOnClickListener { Regresar() }
        btn_Guardar8.setOnClickListener{ guardar()}
    }

    private  fun guardar() {

        if (txt_IdAl.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID de Almacen", Toast.LENGTH_SHORT).show()
        }else {
            if (txt_NombreAl.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un Nombre", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_UbicacionAl.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese una ubicacion", Toast.LENGTH_SHORT).show()
                } else {
                    if (txt_CapacidadAl.text.toString().isEmpty()) {
                        Toast.makeText(this, "Ingrese su capacidad", Toast.LENGTH_SHORT).show()
                } else{
                        Toast.makeText(this, "Realizada con exito!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }
}