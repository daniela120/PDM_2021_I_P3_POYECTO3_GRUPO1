package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.CompraDetalleDataCollectionItem
import kotlinx.android.synthetic.main.activity_detalle_compra.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleCompra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_compra)
        btn_regresardetalleCompra.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabListardetallecompra).setOnClickListener {
            Mostrar()}
        findViewById<FloatingActionButton>(R.id.idFabConfirmardetallecompra).setOnClickListener {
            callServicePostCompraDetalle()
        }
    }

    private fun callServicePostCompraDetalle() {

        val CompraDetalleInfo = CompraDetalleDataCollectionItem(id = null,
                idcompra = spinnerIdCompra3.selectedItem.toString().toLong(),
                cantidad = txt_DCCantidad.text.toString().toLong(), 
                precio = txt_DCPrecio.text.toString().toLong(),
                total = txt_DCTotal.text.toString().toLong(),

                )



        addCompraDetalle(CompraDetalleInfo) {
            if (it?.id != null) {
                android.widget.Toast.makeText(this@DetalleCompra, "OK" + it?.id, android.widget.Toast.LENGTH_LONG).show()
            } else {
                android.widget.Toast.makeText(this@DetalleCompra, "Error", android.widget.Toast.LENGTH_LONG).show()
            }
        }
    }

    fun addCompraDetalle(CompraDetalleData: CompraDetalleDataCollectionItem, onResult: (CompraDetalleDataCollectionItem?) -> Unit) {
        val retrofit = RestEngine.buildService().create(DetalleCompraService::class.java)
        var result: Call<CompraDetalleDataCollectionItem> = retrofit.addCompraDetalle(CompraDetalleData)

        result.enqueue(object : Callback<CompraDetalleDataCollectionItem> {
            override fun onFailure(call: Call<CompraDetalleDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(call: Call<CompraDetalleDataCollectionItem>,
                                    response: Response<CompraDetalleDataCollectionItem>) {
                if (response.isSuccessful) {
                    val addedCompraDetalle= response.body()!!
                    onResult(addedCompraDetalle)
                } else if (response.code() == 401) {
                    Toast.makeText(this@DetalleCompra, "Sesion expirada", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@DetalleCompra, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                }
            }

        }
        )
    }
/*
    private  fun guardar() {
        if (spinnerIdCompra2.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese un ID ", Toast.LENGTH_SHORT).show()
        } else {
            if (spinnerIdCompra3.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un ID de Compra", Toast.LENGTH_SHORT).show()
            } else{
                if(txt_DCCantidad.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese una Cantidad", Toast.LENGTH_SHORT).show()
                }else{
                    if(txt_DCPrecio.text.toString().isEmpty()) {
                        Toast.makeText(this, "Ingrese el precio", Toast.LENGTH_SHORT).show()
                    }else{
                        if(txt_DCTotal.text.toString().isEmpty()) {
                            Toast.makeText(this, "Ingrese El total", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Realizada con exito!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}*/

    private fun Mostrar() {
        val intent = Intent(this, MostrarDetalleCompra::class.java)
        startActivity(intent)}

    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }
}