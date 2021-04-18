package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.DepartamentoDataCollectionItem
import kotlinx.android.synthetic.main.activity_mostrar_detalle_ventas.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MostrarDetalleVenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_detalle_ventas)
        btn_regresarFactura2.setOnClickListener { Regresar() }


    }


    private fun Regresar() {
        val intent = Intent(this, DetalleVenta::class.java)
        startActivity(intent)
    }

}