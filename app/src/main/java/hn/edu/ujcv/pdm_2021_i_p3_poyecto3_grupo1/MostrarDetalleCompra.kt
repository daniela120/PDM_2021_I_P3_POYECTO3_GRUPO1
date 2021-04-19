package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.CompraDetalleDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem

import kotlinx.android.synthetic.main.activity_mostrar_detalle_compra.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MostrarDetalleCompra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_detalle_compra)
        btn_regresarMDetalleCompra.setOnClickListener { Regresar() }
        val botonGetId = findViewById<Button>(R.id.btn_BuscarDetalleCompra)
        findViewById<FloatingActionButton>(R.id.idFabEliminar_DetalleCompra).setOnClickListener{
            callServiceDeleteDetalleCompra() }
        findViewById<FloatingActionButton>(R.id.idFabLimpiar_DetalleCompra).setOnClickListener{
            reset() }
        findViewById<FloatingActionButton>(R.id.idFabEliminar_DetalleCompra).setOnClickListener{
            callServicePutDetalleCompra() }
        botonGetId.setOnClickListener { v -> callServiceGetDetalleCompra()}
    }


    private fun callServiceGetDetalleCompra() {
        val CompraDetalleService: DetalleCompraService= RestEngine.buildService().create(DetalleCompraService::class.java)
        var result: Call<CompraDetalleDataCollectionItem> = CompraDetalleService.getCompraDetalleById(1)

        result.enqueue(object : Callback<CompraDetalleDataCollectionItem> {
            override fun onFailure(call: Call<CompraDetalleDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarDetalleCompra, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<CompraDetalleDataCollectionItem>,
                    response: Response<CompraDetalleDataCollectionItem>
            ) = try {

                txt_DCCantidad2.isEnabled = true
                txt_DCPrecio2.isEnabled = true


                var a = response.body()!!.idcompra
                var b = response.body()!!.cantidad
                var c = response.body()!!.precio
                var d = response.body()!!.total

                ver()
                txv_SeleccionCompraID.setText(a.toString())
                txt_DCCantidad2.setText(b.toString())
                txt_DCPrecio2.setText(c.toString().toInt())
                txt_DCTotal2.setText(d.toString().toInt())

            } catch (e: Exception) {
                Toast.makeText(this@MostrarDetalleCompra, "No existe la informacion con el id: " + txt_DCID.text.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        txt_DCCantidad2.isEnabled
        txt_DCPrecio2.isEnabled
        txt_DCTotal2.isEnabled
    }

    fun ver() {
        txt_DCCantidad2.isEnabled = true
        txt_DCPrecio2.isEnabled = true
        txt_DCTotal2.isEnabled = true
    }


    private fun callServiceDeleteDetalleCompra() {
        try {


            val DetalleCompraService: DetalleCompraService = RestEngine.buildService().create(DetalleCompraService::class.java)
            var result: Call<ResponseBody> = DetalleCompraService.deleteCompraDetalle(txt_DCID.text.toString().toLong())

            result.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MostrarDetalleCompra, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MostrarDetalleCompra, "ELIMINADO CON EXITO", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@MostrarDetalleCompra, "Sesion expirada", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MostrarDetalleCompra, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                    }
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@MostrarDetalleCompra, "NO SE PUEDO ELIMINAR LA INFORMACION CON EL ID: " + txt_DCID.text.toString(), Toast.LENGTH_LONG).show()

        }
    }

    private fun callServicePutDetalleCompra() {
        try {
            val Info = CompraDetalleDataCollectionItem(id = txt_DCID.text.toString().toLong(),
                    idcompra = spinnerIdCompra4.selectedItemId.toString().toLong(),
                    cantidad = txt_DCCantidad2.text.toString().toLong(),
                    precio = txt_DCPrecio2.text.toString().toLong(),
                    total = txt_DCTotal2.text.toString().toLong()
            )


            val retrofit = RestEngine.buildService().create(DetalleCompraService::class.java)
            var result: Call<CompraDetalleDataCollectionItem> = retrofit.updateCompraDetalle(Info)

            result.enqueue(object : Callback<CompraDetalleDataCollectionItem> {
                override fun onFailure(call: Call<CompraDetalleDataCollectionItem>, t: Throwable) {
                    Toast.makeText(this@MostrarDetalleCompra, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<CompraDetalleDataCollectionItem>,
                                        response: Response<CompraDetalleDataCollectionItem>) {
                    if (response.isSuccessful) {
                        val updatedDetalleCompra = response.body()!!
                        Toast.makeText(this@MostrarDetalleCompra, "DATOS ACTUALIZADO", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@MostrarDetalleCompra, "Sesion expirada", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MostrarDetalleCompra, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                    }
                }

            })
        } catch (e: Exception) {
            Toast.makeText(this@MostrarDetalleCompra, "NO SE PUEDO ELIMINAR LA INFORMACION CON EL ID: " + txt_DCID.text.toString(), Toast.LENGTH_LONG).show()

        }
    }
    /*Spinner*/
    private fun callServiceGetIDCompra() {
        var lista: HashSet<String> = hashSetOf()

        val tipoService:ComprasService= RestEngine.buildService().create(ComprasService::class.java)
        var result: Call<List<ComprasDataCollectionItem>> = tipoService.listCompras()

        result.enqueue(object : Callback<List<ComprasDataCollectionItem>> {
            override fun onFailure(call: Call<List<ComprasDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@MostrarDetalleCompra, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<ComprasDataCollectionItem>>,
                    response: Response<List<ComprasDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        lista.add(i.id.toString())
                    }
                    initial(lista)

                } catch (e: Exception) {
                    println("No hay datos de Compras")

                }

            }
        })

    }

    fun initial(a:HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerIdCompra3)
        var valor:String
        println("THIS IS"+a.toString())
        var A:ArrayList<String> = ArrayList()
        for(i in a){
            val data = i.toString().split("|")
            valor=data[0].toString()
            A.add(valor)

        }

        val adaptador = ArrayAdapter(this,android.R.layout.simple_spinner_item,A)

        spinner_Puestos.adapter =adaptador
        spinner_Puestos.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener { override fun onNothingSelected(parent: AdapterView<*>?) {
        }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                callServiceGetCompraById(A[position].toString().toLong())
            }
        }


    }
    private  fun  callServiceGetCompraById(a:Long) {
        var idComp=""
        val comprasService:ComprasService =  RestEngine.buildService().create(ComprasService::class.java)
        var result: Call<ComprasDataCollectionItem> =comprasService.getComprasById(a)

        result.enqueue(object : Callback<ComprasDataCollectionItem> {
            override fun onFailure(call: Call<ComprasDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarDetalleCompra,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ComprasDataCollectionItem>,
                    response: Response<ComprasDataCollectionItem>
            ) {
                idComp = response.body()!!.id.toString()
                txv_SeleccionCompraID.text =  idComp

            }
        })


    }


    fun reset(){
        txt_DCCantidad2.setText("")
        txt_DCPrecio2.setText("")
        txt_DCTotal2.setText("")
        txt_DCCantidad2.isEnabled = false
        txt_DCPrecio2.isEnabled = false
        txt_DCTotal2.isEnabled = false

    }

    private fun Regresar() {
        val intent = Intent(this, DetalleCompra::class.java)
        startActivity(intent)
    }
}