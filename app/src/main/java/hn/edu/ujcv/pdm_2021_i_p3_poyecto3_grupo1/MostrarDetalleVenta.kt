package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProduccionDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProductoDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentaDetalleDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentasDataCollectionItem
import kotlinx.android.synthetic.main.activity_detalle_ventas.*
import kotlinx.android.synthetic.main.activity_mostrar_detalle_ventas.*
import kotlinx.android.synthetic.main.activity_mostrar_ventas.*
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
            callServiceDeleteDetalleVenta()
        }
        findViewById<FloatingActionButton>(R.id.idFabLimpiar_DetalleVenta).setOnClickListener {
            resetear()
        }
        findViewById<FloatingActionButton>(R.id.idFabActualizar_DetalleVenta).setOnClickListener {
            callServicePutDetalleVenta()
        }
        botonGetId.setOnClickListener { v -> callServiceGetDetalleVenta() }
        callServiceGetProducto()
        callServiceGetVenta()

    }

    private fun resetear() {

        txv_SeleccionProductoId2.setText("")
        txv_SeleccionVentaID2.setText("")
        txt_IdFactura2.setText("")
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
        val VentaDetalleService: DetalleVentaService = RestEngine.buildService().create(DetalleVentaService::class.java)
        var result: Call<VentaDetalleDataCollectionItem> = VentaDetalleService.getVentaDetalleById(txt_IdFactura2.text.toString().toLong())

        result.enqueue(object : Callback<VentaDetalleDataCollectionItem> {
            override fun onFailure(call: Call<VentaDetalleDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarDetalleVenta, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<VentaDetalleDataCollectionItem>,
                    response: Response<VentaDetalleDataCollectionItem>
            ) = try {



                var a = response.body()!!.idproducto
                var b = response.body()!!.idventa
                var c = response.body()!!.cantidad
                var d = response.body()!!.precio
                ver()
                txv_SeleccionProductoId2.setText(a.toString())
                txv_SeleccionVentaID2.setText(b.toString())
                txt_CantidadFactura2.setText(c.toString())
                txt_PrecioFactura2.setText(d.toString())

            } catch (e: Exception) {
                Toast.makeText(this@MostrarDetalleVenta, "No existe la informacion con el id: " + txt_IdFactura2.text.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        txt_CantidadFactura2.isEnabled
        txt_PrecioFactura2.isEnabled
        txv_SeleccionVentaID2.isEnabled
        txv_SeleccionProductoId2.isEnabled
    }

    fun ver() {
        txt_CantidadFactura2.isEnabled = true
        txt_PrecioFactura2.isEnabled = true
        txv_SeleccionVentaID2.isEnabled = true
        txv_SeleccionProductoId2.isEnabled = true
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
                    idproducto = spinnerIdProducto1.selectedItem.toString().toLong(),
                    idventa = spinnerIdVenta2.selectedItem.toString().toLong(),
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
    /*SPINER PRODUCTO*/

    private fun callServiceGetProducto() {
        var lista: java.util.HashSet<String> = hashSetOf()

        val tipoService: ProductoService = RestEngine.buildService().create(ProductoService::class.java)
        var result: Call<List<ProductoDataCollectionItem>> = tipoService.listProductos()

        result.enqueue(object : Callback<List<ProductoDataCollectionItem>> {
            override fun onFailure(call: Call<List<ProductoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@MostrarDetalleVenta, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<ProductoDataCollectionItem>>,
                    response: Response<List<ProductoDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        lista.add(i.id.toString())
                    }

                    iniciar2(lista)

                } catch (e: Exception) {
                    println("No hay datos del producto")

                }

            }
        })

    }

    fun iniciar2(a: java.util.HashSet<String>) {
        val spinner_Producto = findViewById<Spinner>(R.id.spinnerIdProducto1)
        var valor: String
        var A: java.util.ArrayList<String> = java.util.ArrayList()
        for (i in a) {
            val data = i.toString().split("|")
            valor = data[0].toString()
            A.add(valor)

        }

        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, A)

        spinner_Producto.adapter = adaptador
        spinner_Producto.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                callServiceGetProductoById(A[position].toString().toLong())


            }
        }

    }

    private fun callServiceGetProductoById(a: Long) {
        var idpro = ""
        val productoservice: ProductoService = RestEngine.buildService().create(ProductoService::class.java)
        var result: Call<ProductoDataCollectionItem> = productoservice.getProductoById(a)

        result.enqueue(object : Callback<ProductoDataCollectionItem> {
            override fun onFailure(call: Call<ProductoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarDetalleVenta, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProductoDataCollectionItem>,
                    response: Response<ProductoDataCollectionItem>
            ) {
                idpro = response.body()!!.nombre.toString()
                txv_SeleccionProductoId2.text = idpro

            }
        })
    }

    /*SPINNER VENTA*/
    private fun callServiceGetVenta() {
        var lista: java.util.HashSet<String> = hashSetOf()

        val tipoService: VentasService = RestEngine.buildService().create(VentasService::class.java)
        var result: Call<List<VentasDataCollectionItem>> = tipoService.listVentas()

        result.enqueue(object : Callback<List<VentasDataCollectionItem>> {
            override fun onFailure(call: Call<List<VentasDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@MostrarDetalleVenta, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<VentasDataCollectionItem>>,
                    response: Response<List<VentasDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        lista.add(i.id.toString())
                    }

                    iniciar1(lista)

                } catch (e: Exception) {
                    println("No hay datos de la venta")

                }

            }
        })

    }

    fun iniciar1(a: java.util.HashSet<String>) {
        val spinnerventa = findViewById<Spinner>(R.id.spinnerIdVenta2)
        var valor: String
        var A: java.util.ArrayList<String> = java.util.ArrayList()
        for (i in a) {
            val data = i.toString().split("|")
            valor = data[0].toString()
            A.add(valor)

        }

        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, A)

        spinnerventa.adapter = adaptador
        spinnerventa.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                callServiceGetVentaById(A[position].toString().toLong())


            }
        }

    }

    private fun callServiceGetVentaById(a: Long) {
        var idven = ""
        val pagoservice: VentasService = RestEngine.buildService().create(VentasService::class.java)
        var result: Call<VentasDataCollectionItem> = pagoservice.getVentasById(a)

        result.enqueue(object : Callback<VentasDataCollectionItem> {
            override fun onFailure(call: Call<VentasDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarDetalleVenta, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<VentasDataCollectionItem>,
                    response: Response<VentasDataCollectionItem>
            ) {
                idven = response.body()!!.descripcion.toString()
                txv_SeleccionVentaID2.text = idven

            }
        })


    }
}
