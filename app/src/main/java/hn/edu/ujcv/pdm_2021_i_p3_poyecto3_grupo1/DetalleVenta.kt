package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentaDetalleDataCollectionItem
import kotlinx.android.synthetic.main.activity_detalle_ventas.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleVenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_ventas)
        btn_regresarFactura.setOnClickListener { Regresar() }


    }


    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

}