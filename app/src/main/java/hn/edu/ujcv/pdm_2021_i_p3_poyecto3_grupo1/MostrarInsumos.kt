package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.InsumosDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_insumos.*

class MostrarInsumos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_insumos)
        btn_regresarInsumos2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarInsumos)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}
    }
    private fun Regresar() {
        val intent = Intent(this, Insumos::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val insumosService:InsumosService = RestEngine.buildService().create(InsumosService::class.java)
        var result: Call<InsumosDataCollectionItem> = insumosService.getInsumosById(1)

        result.enqueue(object : Callback<InsumosDataCollectionItem> {
            override fun onFailure(call: Call<InsumosDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarInsumos,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<InsumosDataCollectionItem>,
                    response: Response<InsumosDataCollectionItem>
            ) {
                Toast.makeText(this@MostrarInsumos,"OK"+response.body()!!.nombrecompleto, Toast.LENGTH_LONG).show()
            }
        })
    }
}