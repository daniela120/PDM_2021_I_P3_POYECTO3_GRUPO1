package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.getbase.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_detalle_compra.*

class DetalleCompra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_compra)
        btn_regresardetalleCompra.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabListardetallecompra).setOnClickListener {
            Mostrar()}
    }

    private fun Mostrar() {
        val intent = Intent(this, MostrarDetalleCompra::class.java)
        startActivity(intent)}

    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }
}