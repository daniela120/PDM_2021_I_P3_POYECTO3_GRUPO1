package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentasDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_ventas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MostrarVentas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_ventas)
        btn_regresarVentas2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarVenta)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}
    }
    private fun Regresar() {
        val intent = Intent(this, Ventas::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val ventasService:VentasService = RestEngine.buildService().create(VentasService::class.java)
        var result: Call<VentasDataCollectionItem> = ventasService.getVentasById(1)

        result.enqueue(object : Callback<VentasDataCollectionItem> {
            override fun onFailure(call: Call<VentasDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarVentas,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<VentasDataCollectionItem>,
                    response: Response<VentasDataCollectionItem>
            ) {
                Toast.makeText(this@MostrarVentas,"OK"+response.body()!!.cai, Toast.LENGTH_LONG).show()
            }
        })
    }
}