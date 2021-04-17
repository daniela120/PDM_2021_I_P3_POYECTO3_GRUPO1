package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_insumos.*
import kotlinx.android.synthetic.main.activity_proveedores.*

class Proveedores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedores)
        btn_regresarProveedor.setOnClickListener { Regresar() }

    }
    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

    private fun Mostrar() {
        val intent = Intent(this, MostrarProveedores::class.java)
        startActivity(intent)
    }

    /*
    private  fun guardar() {

        if (txt_IdAlmacen_Inventario.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID del Proveedores", Toast.LENGTH_SHORT).show()
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
    }*/
}