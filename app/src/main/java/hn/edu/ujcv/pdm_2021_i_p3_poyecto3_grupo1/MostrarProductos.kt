package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProductoDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_productos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MostrarProductos:  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_productos)
        btn_regresarProductos2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarProducto)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}
    }
    private fun Regresar() {
        val intent = Intent(this, Productos::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val productosService:ProductoService = RestEngine.buildService().create(ProductoService::class.java)
        var result: Call<ProductoDataCollectionItem> = productosService.getProductoById(1)

        result.enqueue(object : Callback<ProductoDataCollectionItem> {
            override fun onFailure(call: Call<ProductoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarProductos,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProductoDataCollectionItem>,
                    response: Response<ProductoDataCollectionItem>
            ) {
                Toast.makeText(this@MostrarProductos,"OK"+response.body()!!.descripcion, Toast.LENGTH_LONG).show()
            }
        })
    }
}