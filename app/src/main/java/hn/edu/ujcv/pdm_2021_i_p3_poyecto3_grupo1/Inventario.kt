package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_insumos.*
import kotlinx.android.synthetic.main.activity_inventario.*

class Inventario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)
        btn_RegresarInventario.setOnClickListener { Regresar() }
        btn_Guardar3.setOnClickListener { guardar() }

    }
    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }
    private  fun guardar() {

        if (txt_IdAlmacen_Inventario.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID del Inventario", Toast.LENGTH_SHORT).show()
        }else {
            if (txt_IdProduccion_Inventario.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un Id de Produccion", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_Cantidad.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese una Cantidad", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(this, "Realizada con exito!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}