package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProduccionDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentaDetalleDataCollectionItem
import kotlinx.android.synthetic.main.activity_mostrar_detalle_ventas.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MostrarDetalleVenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_detalle_ventas)

        btn_regresarFactura2.setOnClickListener { Regresar() }
        val botonGetId = findViewById<Button>(R.id.btn_BuscarFactura)

        findViewById<FloatingActionButton>(R.id.idFabEliminar_DetalleVenta).setOnClickListener {
            callServiceDeleteDetalleVenta() }
        findViewById<FloatingActionButton>(R.id.idFabLimpiar_DetalleVenta).setOnClickListener {
            resetear() }
        findViewById<FloatingActionButton>(R.id.idFabActualizar_DetalleVenta).setOnClickListener {
            callServicePutDetalleVenta() }
        botonGetId.setOnClickListener { v -> callServiceGetDetalleVenta() }
    }

    private fun resetear() {
        txt_PrecioFactura2.setText("")
        txt_CantidadFactura2.setText("")
        txt_PrecioFactura2.isEnabled = false
        txt_CantidadFactura2.isEnabled = false

    }

    private fun Regresar() {
        val intent = Intent(this, DetalleVenta::class.java)
        startActivity(intent)
    }


    private fun callServiceGetDetalleVenta() {
        val VentaDetalleService: DetalleVentaService= RestEngine.buildService().create(DetalleVentaService::class.java)
        var result: Call<VentaDetalleDataCollectionItem> = VentaDetalleService.getVentaDetalleById(1)

        result.enqueue(object : Callback<VentaDetalleDataCollectionItem> {
            override fun onFailure(call: Call<VentaDetalleDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarDetalleVenta, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<VentaDetalleDataCollectionItem>,
                    response: Response<VentaDetalleDataCollectionItem>
            ) = try {

                txt_CantidadFactura2.isEnabled = true
                txt_PrecioFactura2.isEnabled = true

                var a = response.body()!!.idproducto
                var b = response.body()!!.idventa
                var c = response.body()!!.cantidad
                var d = response.body()!!.precio
                ver()
                txv_SeleccionProductoId2.setText(a.toString())
                txv_SeleccionVentaID2.setText(b.toString())
                txt_CantidadFactura2.setText(c.toString().toInt())
                txt_PrecioFactura2.setText(d.toString().toInt())

            } catch (e: Exception) {
                Toast.makeText(this@MostrarDetalleVenta, "No existe la informacion con el id: " + txt_IdFactura2.text.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        txt_CantidadFactura2.isEnabled
        txt_PrecioFactura2.isEnabled
    }

    fun ver() {
        txt_CantidadFactura2.isEnabled = true
        txt_PrecioFactura2.isEnabled = true
    }


    private fun callServiceDeleteDetalleVenta() {
        try {


            val DetalleVentaService: DetalleVentaService = RestEngine.buildService().create(DetalleVentaService::class.java)
            var result: Call<ResponseBody> = DetalleVentaService.deleteVentaDetalle(txt_IdFactura2.text.toString().toLong())

            result.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MostrarDetalleVenta, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MostrarDetalleVenta, "ELIMINADO CON EXITO", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@MostrarDetalleVenta, "Sesion expirada", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MostrarDetalleVenta, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                    }
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@MostrarDetalleVenta, "NO SE PUEDO ELIMINAR LA INFORMACION CON EL ID: " + txt_IdFactura2.text.toString(), Toast.LENGTH_LONG).show()

        }
    }

    private fun callServicePutDetalleVenta() {
        try {
            val Info = VentaDetalleDataCollectionItem(id = txt_IdFactura2.text.toString().toLong(),
                    idproducto = spinnerIdProducto1.selectedItemId.toString().toLong(),
                    idventa = spinnerIdVenta2.selectedItemId.toString().toLong(),
                    cantidad = txt_CantidadFactura2.text.toString().toLong(),
                    precio = txt_PrecioFactura2.text.toString().toLong()
            )


            val retrofit = RestEngine.buildService().create(DetalleVentaService::class.java)
            var result: Call<VentaDetalleDataCollectionItem> = retrofit.updateVentaDetalle(Info)

            result.enqueue(object : Callback<VentaDetalleDataCollectionItem> {
                override fun onFailure(call: Call<VentaDetalleDataCollectionItem>, t: Throwable) {
                    Toast.makeText(this@MostrarDetalleVenta, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<VentaDetalleDataCollectionItem>,
                                        response: Response<VentaDetalleDataCollectionItem>) {
                    if (response.isSuccessful) {
                        val updatedDetalleVenta = response.body()!!
                        Toast.makeText(this@MostrarDetalleVenta, "DEPT ACTUALIZADO", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@MostrarDetalleVenta, "Sesion expirada", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MostrarDetalleVenta, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                    }
                }

            })
        } catch (e: Exception) {
            Toast.makeText(this@MostrarDetalleVenta, "NO SE PUEDO ELIMINAR LA INFORMACION CON EL ID: " + txt_IdFactura2.text.toString(), Toast.LENGTH_LONG).show()

        }
    }
}