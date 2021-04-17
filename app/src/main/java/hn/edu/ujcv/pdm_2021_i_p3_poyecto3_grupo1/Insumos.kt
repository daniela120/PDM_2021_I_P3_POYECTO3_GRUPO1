package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_insumos.*
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.InsumosDataCollectionItem

class Insumos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insumos)
        btn_regresarInsumos.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabListar_Insumos).setOnClickListener {
            Mostrar() }


    }
    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }
    private fun Mostrar() {
        val intent = Intent(this, MostrarInsumos::class.java)
        startActivity(intent)
    }

    private fun callServicePostInsumo() {
        val insumosinf = InsumosDataCollectionItem(
                nombre = txt_NombreInsumo.text.toString(),
                tipo = txt_Tipo.text.toString(),
                cantidad = txt_TipoCantidad.text.toString().toLong() ,
                 preciocompra = txt_PrecioCon.text.toString().toLong() ,
                precioventa = txt_PrecioVenta.text.toString().toLong())

        addInsumos(insumosinf {
            if (it?.id != null) {
                android.widget.Toast.makeText(this@MainActivity,"OK"+it?.id, android.widget.Toast.LENGTH_LONG).show()
            } else {
                android.widget.Toast.makeText(this@MainActivity,"Error", android.widget.Toast.LENGTH_LONG).show()
            }
        }
    }

    }


    private  fun guardar() {

        if (txt_IdInsumo.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID del Insumo", Toast.LENGTH_SHORT).show()
        }else {
            if (txt_NombreInsumo.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un Nombre de Insumo", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_Tipo.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese una Tipo de Compra", Toast.LENGTH_SHORT).show()
                        } else{
                            Toast.makeText(this, "Realizada con exito!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


}