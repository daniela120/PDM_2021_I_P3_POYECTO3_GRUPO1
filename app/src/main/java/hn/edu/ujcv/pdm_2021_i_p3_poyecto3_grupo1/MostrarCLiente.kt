package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_cliente.*

class MostrarCLiente:  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_cliente)
        btn_regresarCliente2.setOnClickListener { Regresar()}

    }
        private fun Regresar() {
            val intent = Intent(this, Cliente::class.java)
            startActivity(intent)
        }
}