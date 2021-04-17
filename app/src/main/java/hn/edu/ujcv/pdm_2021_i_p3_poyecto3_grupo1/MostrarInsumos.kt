package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_insumos.*

class MostrarInsumos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_insumos)
        btn_regresarInsumos2.setOnClickListener { Regresar()}
    }
    private fun Regresar() {
        val intent = Intent(this, Insumos::class.java)
        startActivity(intent)
    }
}