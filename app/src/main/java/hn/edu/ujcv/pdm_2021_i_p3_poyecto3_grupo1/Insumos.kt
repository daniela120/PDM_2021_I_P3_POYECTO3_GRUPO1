package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_departamento.*
import kotlinx.android.synthetic.main.activity_insumos.*
import com.getbase.floatingactionbutton.FloatingActionButton

class Insumos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insumos)
        btn_regresarInsumos.setOnClickListener { Regresar() }
    }
    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }


    private  fun guardar() {

        if (txt_IdInsumo.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID del Insumo", Toast.LENGTH_SHORT).show()
        }else {
            if (txt_NombreInsumo.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un Nombre de Insumo", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_TipoCompra_Insumo.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese una Tipo de Compra", Toast.LENGTH_SHORT).show()
                        } else{
                            Toast.makeText(this, "Realizada con exito!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


}