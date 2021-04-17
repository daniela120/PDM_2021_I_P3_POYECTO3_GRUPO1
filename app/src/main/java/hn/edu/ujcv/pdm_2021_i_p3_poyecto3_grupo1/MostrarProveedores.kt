package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_proveedores.*

class MostrarProveedores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_proveedores)
        btn_regresarProveedor2.setOnClickListener { Regresar()}
    }
    private fun Regresar() {
        val intent = Intent(this, Proveedores::class.java)
        startActivity(intent)
    }
}