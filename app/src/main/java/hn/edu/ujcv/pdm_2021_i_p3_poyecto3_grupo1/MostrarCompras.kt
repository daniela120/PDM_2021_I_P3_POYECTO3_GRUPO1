package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_compras.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MostrarCompras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_compras)
        btn_regresarCompras2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarrCompra2)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}
    }
    private fun Regresar() {
        val intent = Intent(this, Compras::class.java)
        startActivity(intent)
    }
    private fun callServiceGetPerson() {
        val comprasService:ComprasService = RestEngine.buildService().create(ComprasService::class.java)
        var result: Call<ComprasDataCollectionItem> = comprasService.getComprasById(txt_IdCompra2.text.toString().toLong())

        result.enqueue(object : Callback<ComprasDataCollectionItem> {
            override fun onFailure(call: Call<ComprasDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarCompras,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ComprasDataCollectionItem>,
                    response: Response<ComprasDataCollectionItem>
            ) {
                try {


                    var a = response.body()!!.cai
                    var b = response.body()!!.fechacompra
                    var c = response.body()!!.fechaentrega
                    var d = response.body()!!.formapago
                    var e = response.body()!!.numerotarjeta
                    var f = response.body()!!.insumos
                    var g = response.body()!!.proveedores
                    ver()
                    txt_MostrarCCai.setText(a)
                    txt_MostrarCCantidad.setText(b)
                    txt_MostrarCFecha.setText(c)
                    txt_MostrarCProveedor.setText(g.toString())
                    txt_MostrarCFormapago.setText(d.toString())
                }catch (e:Exception){
                    Toast.makeText(this@MostrarCompras, "No existe la compra con el id: "+txt_IdCompra2.text.toString(), Toast.LENGTH_SHORT ).show()
                }




            }
        })
    }

    fun ver(){
        txt_MostrarCCai.isEnabled =  true
        txt_MostrarCCantidad.isEnabled = true
        txt_MostrarCFecha.isEnabled = true
        txt_MostrarCProveedor.isEnabled = true
        txt_MostrarCFormapago.isEnabled = true

    }


}