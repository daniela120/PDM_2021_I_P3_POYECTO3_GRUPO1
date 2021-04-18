package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.InsumosDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProductoDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_insumos.*
import kotlinx.android.synthetic.main.activity_mostrar_productos.*
import okhttp3.ResponseBody
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
        findViewById<FloatingActionButton>(R.id.idFabActualizar_Productos).setOnClickListener {
            callServicePutProducto()
        }

        findViewById<FloatingActionButton>(R.id.idFabEliminar_Productos).setOnClickListener {
            callServiceDeleteProducto()
        }

        findViewById<FloatingActionButton>(R.id.idFabLimpiar_Producto).setOnClickListener {
            reset()
        }
    }
    private fun Regresar() {
        val intent = Intent(this, Productos::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val productosService:ProductoService = RestEngine.buildService().create(ProductoService::class.java)
        var result: Call<ProductoDataCollectionItem> = productosService.getProductoById(txt_IdProductos2.text.toString().toLong())

        result.enqueue(object : Callback<ProductoDataCollectionItem> {
            override fun onFailure(call: Call<ProductoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarProductos, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProductoDataCollectionItem>,
                    response: Response<ProductoDataCollectionItem>
            ) {
                try {


                    var b = response.body()!!.nombre
                    var a = response.body()!!.descripcion
                    var c = response.body()!!.preciocompra
                    var d = response.body()!!.precioventa

                    txt_DescripcionProducto2.setText(a)
                    txt_PrecioComProducto2.setText(c.toString())
                    txt_PrecioVenProducto2.setText(d.toString())
                    txt_NombreProductos2.setText(b)

                    txt_DescripcionProducto2.isEnabled = true
                    txt_PrecioComProducto2.isEnabled = true
                    txt_PrecioVenProducto2.isEnabled = true
                    txt_NombreProductos2.isEnabled = true

                } catch (e:Exception) {
                    Toast.makeText(this@MostrarProductos, "ERROR, EL PRODUCTO CON ID:" +txt_IdProductos2.text.toString()+ " ES INVALIDO", Toast.LENGTH_SHORT).show()
                reset()
                }

            }
        })


    }


    fun reset(){
        txt_IdProductos2.setText("")
        txt_NombreProductos2.setText("")
        txt_DescripcionProducto2.setText("")
        txt_PrecioComProducto2.setText("")
        txt_PrecioVenProducto2.setText("")


        txt_NombreProductos2.isEnabled = false
        txt_DescripcionProducto2.isEnabled = false
        txt_PrecioComProducto2.isEnabled = false
        txt_PrecioVenProducto2.isEnabled = false

    }
    //Actualizar
    private fun callServicePutProducto() {
        try {


            val productoinfo = ProductoDataCollectionItem(id = txt_IdProductos2.text.toString().toLong(),
                    nombre = txt_NombreProductos2.text.toString(),
                    descripcion = txt_DescripcionProducto2.text.toString(),
                    preciocompra = txt_PrecioComProducto2.text.toString().toLong(),
                    precioventa = txt_PrecioVenProducto2.text.toString().toLong()

            )


            val retrofit = RestEngine.buildService().create(ProductoService::class.java)
            var result: Call<ProductoDataCollectionItem> = retrofit.updateProducto(productoinfo)


            result.enqueue(object : Callback<ProductoDataCollectionItem> {
                override fun onFailure(call: Call<ProductoDataCollectionItem>, t: Throwable) {
                    Toast.makeText(this@MostrarProductos, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<ProductoDataCollectionItem>,
                                        response: Response<ProductoDataCollectionItem>) {
                    if (response.isSuccessful) {
                        val updatedPerson = response.body()!!
                        Toast.makeText(this@MostrarProductos, " PRODUCTO ACTUALIZADO", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@MostrarProductos, "Sesion expirada", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MostrarProductos, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                    }
                }

            })
        }catch (e:Exception){
            Toast.makeText(this@MostrarProductos, "ERROR, POR FAVOR VERIFIQUE LOS DATOS", Toast.LENGTH_SHORT).show()
        }

    }
    //Metodo Delete
    private fun callServiceDeleteProducto() {
        try {


            val insumosService: ProductoService = RestEngine.buildService().create(ProductoService::class.java)
            var result: Call<ResponseBody> = insumosService.deleteProducto(txt_IdProductos2.text.toString().toLong())

            result.enqueue(object :  Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MostrarProductos,"Error",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        reset()
                        Toast.makeText(this@MostrarProductos,"PRODUCTO ELIMINADO",Toast.LENGTH_LONG).show()
                    }
                    else if (response.code() == 401){
                        Toast.makeText(this@MostrarProductos,"Sesion expirada",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this@MostrarProductos,"Fallo al traer el item",Toast.LENGTH_LONG).show()
                    }
                }
            })
        }catch (e: java.lang.Exception){
            Toast.makeText(this@MostrarProductos,"NO SE PUDO ELIMINAR EL PRODUCTO CON EL ID: "+ txt_IdProductos2.text.toString(),Toast.LENGTH_LONG).show()
            reset()
        }    }


}