package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_costos.*
import kotlinx.android.synthetic.main.activity_tela.*

class Tela : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela)
        btn_RegresarT.setOnClickListener { Regresar() }
        btn_GuardarT.setOnClickListener { guardar() }
    }

    private  fun guardar() {

        if (txt_IdTela.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID de la Tela", Toast.LENGTH_SHORT).show()
        }else {
            if (txt_NombreTela.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un Nombre", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_MaterialTela.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese el Material", Toast.LENGTH_SHORT).show()
                } else {
                    if (txt_ColorTela.text.toString().isEmpty()) {
                        Toast.makeText(this, "Ingrese una cantidad", Toast.LENGTH_SHORT).show()
                    } else {
                        if (txt_TipoCompta_Tela.text.toString().isEmpty()) {
                            Toast.makeText(this, "Inreges el tipo de compra", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "Realizada con exito!", Toast.LENGTH_SHORT).show()
                        }
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