package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProveedoresDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_proveedores.*

class MostrarProveedores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_proveedores)
        btn_regresarProveedor2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarProveedor)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}
    }
    private fun Regresar() {
        val intent = Intent(this, Proveedores::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val proveedoresService:ProveedoresService = RestEngine.buildService().create(ProveedoresService::class.java)
        var result: Call<ProveedoresDataCollectionItem> = proveedoresService.getProveedoresById(1)

        result.enqueue(object : Callback<ProveedoresDataCollectionItem> {
            override fun onFailure(call: Call<ProveedoresDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarProveedores,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProveedoresDataCollectionItem>,
                    response: Response<ProveedoresDataCollectionItem>
            ) {
                Toast.makeText(this@MostrarProveedores,"OK"+response.body()!!.nombrecompleto, Toast.LENGTH_LONG).show()
            }
        })
    }
}