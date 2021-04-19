package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentasDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProductoDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentaDetalleDataCollectionItem
import kotlinx.android.synthetic.main.activity_detalle_ventas.*
import kotlinx.android.synthetic.main.activity_listar_detalle_venta.*
import kotlinx.android.synthetic.main.activity_mostrar_detalle_ventas.*
import kotlinx.android.synthetic.main.activity_produccion.*
import kotlinx.android.synthetic.main.activity_ventas.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleVenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_ventas)
        btn_regresarFactura.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabListar_DetalleVenta).setOnClickListener {
            Mostrar()}
        findViewById<FloatingActionButton>(R.id.idFabConfirmar_DetalleVenta).setOnClickListener {
            validacion()}
        findViewById<FloatingActionButton>(R.id.idListarDV).setOnClickListener {
            ir()
        }

        callServiceGetVenta()
        callServiceGetProducto()
    }

    private fun ir() {
        val intent = Intent(this, ListarDetalleVenta::class.java)
        startActivity(intent)
    }

    private fun Mostrar() {
        val intent = Intent(this, MostrarDetalleVenta::class.java)
        startActivity(intent)
    }

    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

    private fun callServicePostVentaDetalle() {
        try {


            val VentaDetalleInfo = VentaDetalleDataCollectionItem(id = null,
                    precio = txt_PrecioFactura1.text.toString().toLong(),
                    cantidad = txt_CantidadFactura1.text.toString().toLong(),
                    idventa = spinnerIdVenta1.selectedItem.toString().toLong(),
                    idproducto = spinnerIdproductoFac1.selectedItem.toString().toLong()


            )
            addVentaDetalle(VentaDetalleInfo) {
                if (it?.id != null) {
                    android.widget.Toast.makeText(this@DetalleVenta, "OK" + it?.id, android.widget.Toast.LENGTH_LONG).show()
                } else {
                    android.widget.Toast.makeText(this@DetalleVenta, "Error", android.widget.Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message + " ERROR,POR FAVOR VERIFICAR LOS DATOS", Toast.LENGTH_SHORT).show()

        }
    }

    fun addVentaDetalle(VentaDetalleData: VentaDetalleDataCollectionItem, onResult: (VentaDetalleDataCollectionItem?) -> Unit){
        val retrofit = RestEngine.buildService().create(DetalleVentaService::class.java)
        var result: Call<VentaDetalleDataCollectionItem> = retrofit.addVentaDetalle(VentaDetalleData)

        result.enqueue(object : Callback<VentaDetalleDataCollectionItem> {
            override fun onFailure(call: Call<VentaDetalleDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(call: Call<VentaDetalleDataCollectionItem>,
                                    response: Response<VentaDetalleDataCollectionItem>) {
                if (response.isSuccessful) {
                    val addedVentaDetalle = response.body()!!
                    onResult(addedVentaDetalle)
                } else if (response.code() == 401) {
                    Toast.makeText(this@DetalleVenta, "Sesion expirada", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@DetalleVenta, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                }
            }
        }
        )
    }

    /*SPINNER VENTA*/
    private fun callServiceGetVenta() {
        var lista: java.util.HashSet<String> = hashSetOf()

        val tipoService: VentasService= RestEngine.buildService().create(VentasService::class.java)
        var result: Call<List<VentasDataCollectionItem>> = tipoService.listVentas()

        result.enqueue(object : Callback<List<VentasDataCollectionItem>> {
            override fun onFailure(call: Call<List<VentasDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@DetalleVenta, "Error", Toast.LENGTH_LONG).show()
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

    fun iniciar1(a: java.util.HashSet<String>){
        val spinnerventa = findViewById<Spinner>(R.id.spinnerIdVenta1)
        var valor:String
        var A: java.util.ArrayList<String> = java.util.ArrayList()
        for(i in a){
            val data = i.toString().split("|")
            valor=data[2].toString()
            A.add(valor)

        }

        val adaptador = ArrayAdapter(this,android.R.layout.simple_spinner_item,A)

        spinnerventa.adapter =adaptador
        spinnerventa.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener { override fun onNothingSelected(parent: AdapterView<*>?) {
        }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                callServiceGetVentaById(A[position].toString().toLong())


            }
        }

    }

    private fun callServiceGetVentaById(a:Long) {
        var idven=""
        val pagoservice: VentasService = RestEngine.buildService().create(VentasService::class.java)
        var result: Call<VentasDataCollectionItem> = pagoservice.getVentasById(a)

        result.enqueue(object : Callback<VentasDataCollectionItem> {
            override fun onFailure(call: Call<VentasDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@DetalleVenta,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<VentasDataCollectionItem>,
                    response: Response<VentasDataCollectionItem>
            ) {
                idven = response.body()!!.descripcion.toString()
                txv_SeleccionVentaId1.text = idven

            }
        })


    }/*SPINER PRODUCTO*/

    private fun callServiceGetProducto() {
        var lista: java.util.HashSet<String> = hashSetOf()

        val tipoService: ProductoService = RestEngine.buildService().create(ProductoService::class.java)
        var result: Call<List<ProductoDataCollectionItem>> = tipoService.listProductos()

        result.enqueue(object : Callback<List<ProductoDataCollectionItem>> {
            override fun onFailure(call: Call<List<ProductoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@DetalleVenta, "Error", Toast.LENGTH_LONG).show()
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

    fun iniciar2(a: java.util.HashSet<String>){
        val spinner_Producto = findViewById<Spinner>(R.id.spinnerIdproductoFac1)
        var valor:String
        var A: java.util.ArrayList<String> = java.util.ArrayList()
        for(i in a){
            val data = i.toString().split("|")
            valor=data[0].toString()
            A.add(valor)

        }

        val adaptador = ArrayAdapter(this,android.R.layout.simple_spinner_item,A)

        spinner_Producto.adapter =adaptador
        spinner_Producto.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener { override fun onNothingSelected(parent: AdapterView<*>?) {
        }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                callServiceGetProductoById(A[position].toString().toLong())


            }
        }

    }

    private fun callServiceGetProductoById(a:Long) {
        var idpro=""
        val productoservice: ProductoService = RestEngine.buildService().create(ProductoService::class.java)
        var result: Call<ProductoDataCollectionItem> = productoservice.getProductoById(a)

        result.enqueue(object : Callback<ProductoDataCollectionItem> {
            override fun onFailure(call: Call<ProductoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@DetalleVenta,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProductoDataCollectionItem>,
                    response: Response<ProductoDataCollectionItem>
            ) {
                idpro = response.body()!!.id.toString()
                txv_SeleccionProductoId1.text = idpro

            }
        })
    }

    private  fun guardar():Boolean {
        var a = true
        if (txt_PrecioFactura1.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese el Precio", Toast.LENGTH_SHORT).show()
            a = false
        } else {
            if (txt_CantidadFactura1.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese la Cantidad", Toast.LENGTH_SHORT).show()
                a = false
                if (spinnerIdVenta1.isSelected.toString().isEmpty()) {
                    a = false
                    Toast.makeText(this, "Seleccione la venta", Toast.LENGTH_SHORT).show()
                } else {
                    if (spinnerIdproductoFac1.isSelected.toString().isEmpty()) {
                        a = false
                        Toast.makeText(this, "Seleccione el producto", Toast.LENGTH_SHORT).show()
                        } else {
                            a = true
                        }
                    }
                }

            }
        return a
    }

    fun validacion(){
        if(guardar().equals(true)){
            callServicePostVentaDetalle()
            Toast.makeText(this, "TRANSACCION EXITOSA!", Toast.LENGTH_SHORT).show()
        }

    }
}