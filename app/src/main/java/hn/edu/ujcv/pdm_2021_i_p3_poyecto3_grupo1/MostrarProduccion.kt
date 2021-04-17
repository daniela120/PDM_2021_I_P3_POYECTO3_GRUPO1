package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProduccionDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_produccion.*

class MostrarProduccion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_produccion)
        btn_regresarProduccion2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarProduccion)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}
    }
    private fun Regresar() {
        val intent = Intent(this, Produccion::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val produccionService:ProduccionService = RestEngine.buildService().create(ProduccionService::class.java)
        var result: Call<ProduccionDataCollectionItem> = produccionService.getProduccionById(1)

        result.enqueue(object : Callback<ProduccionDataCollectionItem> {
            override fun onFailure(call: Call<ProduccionDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarProduccion,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProduccionDataCollectionItem>,
                    response: Response<ProduccionDataCollectionItem>
            ) {
                Toast.makeText(this@MostrarProduccion,"OK"+response.body()!!.nombrecompleto, Toast.LENGTH_LONG).show()
            }
        })
    }
}