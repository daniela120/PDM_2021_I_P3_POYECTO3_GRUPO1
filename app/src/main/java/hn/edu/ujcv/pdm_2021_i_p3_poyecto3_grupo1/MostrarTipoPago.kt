
package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.PagoDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_tipo_pago.*

class MostrarTipoPago:  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_tipo_pago)
        btn_regresarPago2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarPago)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}
    }
    private fun Regresar() {
        val intent = Intent(this, TipoPago::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val pagoService:PagoService = RestEngine.buildService().create(PagoService::class.java)
        var result: Call<PagoDataCollectionItem> = pagoService.getPagoById( 1)

        result.enqueue(object : Callback<PagoDataCollectionItem> {
            override fun onFailure(call: Call<PagoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarTipoPago,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<PagoDataCollectionItem>,
                    response: Response<PagoDataCollectionItem>
            ) {
                Toast.makeText(this@MostrarTipoPago,"OK"+response.body()!!.nombrecompleto, Toast.LENGTH_LONG).show()
            }
        })
    }
}