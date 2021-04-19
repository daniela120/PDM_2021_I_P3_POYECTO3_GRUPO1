package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_listar_detalle_compra.*

class ListarDetalleCompra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_detalle_compra)
        btn_regresarListarDetalleCompras.setOnClickListener{ Regresar()}
    }
    private fun Regresar() {
        val intent = Intent(this, DetalleCompra::class.java)
        startActivity(intent)
    }
}