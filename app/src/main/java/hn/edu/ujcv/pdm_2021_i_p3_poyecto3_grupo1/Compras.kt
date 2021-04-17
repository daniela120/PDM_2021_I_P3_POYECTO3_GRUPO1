package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cliente.*

import kotlinx.android.synthetic.main.activity_compras.*

class Compras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compras)
        btn_regresarCompras.setOnClickListener { Regresar() }

    }

    /*private  fun guardar() {

        if (txt_IdCompra.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID de Compra", Toast.LENGTH_SHORT).show()
        }else {
            if (txt_TipoCompra.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un tipo de compra", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_CantidadCompra.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese una cantidad", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(this, "Realizada con exito!", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }*/

    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }
    private fun Mostrar() {
        val intent = Intent(this, MostrarCompras::class.java)
        startActivity(intent)
    }
}