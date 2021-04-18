package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_produccion.*
import kotlinx.android.synthetic.main.activity_productos.*
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProductoDataCollectionItem
import kotlinx.android.synthetic.main.activity_mostrar_productos.*
import kotlinx.android.synthetic.main.activity_proveedores.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Productos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)
        btn_regresarProductos.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabListar_Productos).setOnClickListener {
            Mostrar() }
        findViewById<FloatingActionButton>(R.id.idFabConfirmar_Productos).setOnClickListener {
            guardar() }
    }

    private fun callServicePostProductos() {
        try {


            val productosInfo = ProductoDataCollectionItem(id = null,
                    nombre = txt_NombreProductos.text.toString(),
                    descripcion = txt_DescripcionProducto.text.toString(),
                    preciocompra = txt_PrecioComProducto.text.toString().toLong(),
                    precioventa = txt_PrecioVenProducto.text.toString().toLong()
            )



            addProducto(productosInfo) {
                if (it?.id != null) {
                    android.widget.Toast.makeText(this@Productos, "PRODUCTO AGREGADO", android.widget.Toast.LENGTH_LONG).show()
                } else {
                    android.widget.Toast.makeText(this@Productos, "Error", android.widget.Toast.LENGTH_LONG).show()
                }
            }

        }catch (e:Exception){
            Toast.makeText(this, e.message+" ERROR, POR FAVOR VERIFIQUE LOS DATOS", Toast.LENGTH_SHORT).show()

        }
    }

    fun addProducto(productoData: ProductoDataCollectionItem, onResult: (ProductoDataCollectionItem?) -> Unit) {
        val retrofit = RestEngine.buildService().create(ProductoService::class.java)
        var result: Call<ProductoDataCollectionItem> = retrofit.addProducto(productoData)

        result.enqueue(object : Callback<ProductoDataCollectionItem> {
            override fun onFailure(call: Call<ProductoDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(call: Call<ProductoDataCollectionItem>,
                                    response: Response<ProductoDataCollectionItem>) {
                if (response.isSuccessful) {
                    val addedProducto = response.body()!!
                    onResult(addedProducto)
                } else if (response.code() == 401) {
                    Toast.makeText(this@Productos, "Sesion expirada", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@Productos, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                }
            }

        }
        )

    }

    private  fun guardar() {

        if (txt_NombreProductos.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del Producto", Toast.LENGTH_SHORT).show()
        }else {
            if (txt_DescripcionProducto.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese la descripcion del Producto", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_PrecioComProducto.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese el precio de compra del Producto", Toast.LENGTH_SHORT).show()
                } else {
                    if (txt_PrecioVenProducto.text.toString().isEmpty()) {
                        Toast.makeText(this, "Ingrese el precio de venta del Producto", Toast.LENGTH_SHORT).show()
                        }else{
                            callServicePostProductos()
                            reset()
                        }
                    }
                }
            }
        }


    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

    private fun Mostrar() {
        val intent = Intent(this, MostrarProductos::class.java)
        startActivity(intent)
    }

    fun reset(){
        txt_NombreProductos.setText("")
        txt_DescripcionProducto.setText("")
        txt_PrecioComProducto.setText("")
        txt_PrecioVenProducto.setText("")
    }
}