package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_compras.*

class MostrarCompras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_compras)
        btn_regresarCompras2.setOnClickListener { Regresar()}
    }
    private fun Regresar() {
        val intent = Intent(this, Compras::class.java)
        startActivity(intent)
    }
}