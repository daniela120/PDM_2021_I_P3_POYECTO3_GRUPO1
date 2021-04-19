package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.CompraDetalleDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.InsumosDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.PagoDataCollectionItem
import kotlinx.android.synthetic.main.activity_compras.*
import kotlinx.android.synthetic.main.activity_mostrar_compras.*

import kotlinx.android.synthetic.main.activity_mostrar_detalle_compra.*
import kotlinx.android.synthetic.main.activity_mostrar_insumos.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception

class MostrarDetalleCompra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_detalle_compra)
        btn_regresarMDetalleCompra.setOnClickListener { Regresar() }
        val botonGetId = findViewById<Button>(R.id.btn_BuscarDetalleCompra)
        findViewById<FloatingActionButton>(R.id.idFabActualizar_DetalleCompra).setOnClickListener{
            callServicePutDetalleCompras()
           }
        findViewById<FloatingActionButton>(R.id.idFabLimpiar_DetalleCompra).setOnClickListener{
            reset() }
        findViewById<FloatingActionButton>(R.id.idFabEliminar_DetalleCompra).setOnClickListener{
            callServiceDeleteDetalleCompra()
            }
        botonGetId.setOnClickListener { v ->
            callServiceGetDetalleCompra()

        }
        callServiceGetCompra()
        txv_SeleccionCompraID.setText("")

    }





    fun reset(){
        txv_SeleccionCompraID.setText("")
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


    /*sPINNER ID COMPRA*/
    private fun callServiceGetCompra() {
        var lista: HashSet<String> = hashSetOf()

        val tipoService:ComprasService = RestEngine.buildService().create(ComprasService::class.java)
        var result: Call<List<ComprasDataCollectionItem>> = tipoService.listCompras()

        result.enqueue(object : Callback<List<ComprasDataCollectionItem>> {
            override fun onFailure(call: Call<List<ComprasDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@MostrarDetalleCompra, "Error", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                    call: Call<List<ComprasDataCollectionItem>>,
                    response: Response<List<ComprasDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        lista.add(i.id.toString())
                    }

                    iniciar2(lista)

                } catch (e: Exception) {
                    println(e.message+ "No hay datos de tipo de pago")

                }

            }
        })

    }


    fun iniciar2(a:HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerIdCompra4)
        var valor:String
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
                callServiceGetComprabyId(A[position].toString().toLong())


            }
        }

    }
    private fun callServiceGetComprabyId(a: Long){
        var nombrep=""
        val compraService:ComprasService = RestEngine.buildService().create(ComprasService::class.java)
        var result: Call<ComprasDataCollectionItem> = compraService.getComprasById(a)

        result.enqueue(object : Callback<ComprasDataCollectionItem> {
            override fun onFailure(call: Call<ComprasDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarDetalleCompra,"Error",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                    call: Call<ComprasDataCollectionItem>,
                    response: Response<ComprasDataCollectionItem>
            ) {
                nombrep = response.body()!!.cai.toString()
                txv_SeleccionCompraID.text = nombrep

            }
        })
    }


    /*CRUD*/



    //Actualizar
    private fun callServicePutDetalleCompras() {
        try {

            val detallecomprainfo = CompraDetalleDataCollectionItem(  id = txt_DCID.text.toString().toLong(),
                    idcompra = spinnerIdCompra4.selectedItem.toString().toLong(),
                    cantidad = txt_DCCantidad2.text.toString().toLong(),
                    precio = txt_DCPrecio2.text.toString().toLong(),
                    total = txt_DCTotal2.text.toString().toLong()
            )


            val retrofit = RestEngine.buildService().create(DetalleCompraService::class.java)
            var result: Call<CompraDetalleDataCollectionItem> = retrofit.updateCompraDetalle(detallecomprainfo)


            result.enqueue(object : Callback<CompraDetalleDataCollectionItem> {
                override fun onFailure(call: Call<CompraDetalleDataCollectionItem>, t: Throwable) {
                    Toast.makeText(this@MostrarDetalleCompra, "Error", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<CompraDetalleDataCollectionItem>,
                                        response: Response<CompraDetalleDataCollectionItem>) {
                    if (response.isSuccessful) {
                        val updatedPerson = response.body()!!
                        Toast.makeText(this@MostrarDetalleCompra, "DETALLE DE COMPRA ACTUALIZADO", Toast.LENGTH_SHORT).show()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@MostrarDetalleCompra, "Sesion expirada", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MostrarDetalleCompra, "Fallo al traer el item", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }catch (e: java.lang.Exception){
            Toast.makeText(this@MostrarDetalleCompra, e.message+" ERROR, POR FAVOR VERIFIQUE LOS DATOS", Toast.LENGTH_SHORT).show()
        }

    }
    //Metodo Delete
    private fun callServiceDeleteDetalleCompra() {
        try {


            val detallecompraService: DetalleCompraService = RestEngine.buildService().create(DetalleCompraService::class.java)
            var result: Call<ResponseBody> = detallecompraService.deleteCompraDetalle(txt_DCID.text.toString().toLong())

            result.enqueue(object :  Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MostrarDetalleCompra,"Error",Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        reset()
                        Toast.makeText(this@MostrarDetalleCompra,"DETALLE DE COMPRA ELIMINADO",Toast.LENGTH_SHORT).show()
                    }
                    else if (response.code() == 401){
                        Toast.makeText(this@MostrarDetalleCompra,"Sesion expirada",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@MostrarDetalleCompra,"Fallo al traer el item",Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }catch (e: java.lang.Exception){
            Toast.makeText(this@MostrarDetalleCompra,"NO SE PUEDO ELIMINAR LA COMPRA CON EL ID: "+ txt_DCID.text.toString(),Toast.LENGTH_SHORT).show()
            reset()
        }    }


    /*GET*/
    private fun callServiceGetDetalleCompra() {
        val dcomprasService:DetalleCompraService = RestEngine.buildService().create(DetalleCompraService::class.java)
        var result: Call<CompraDetalleDataCollectionItem> = dcomprasService.getCompraDetalleById(txt_DCID.text.toString().toLong())

        result.enqueue(object : Callback<CompraDetalleDataCollectionItem> {
            override fun onFailure(call: Call<CompraDetalleDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarDetalleCompra,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<CompraDetalleDataCollectionItem>,
                    response: Response<CompraDetalleDataCollectionItem>
            ) = try {


                var a = response.body()!!.idcompra
                var b = response.body()!!.cantidad
                var c = response.body()!!.precio
                var d = response.body()!!.total
                txv_SeleccionCompraID.setText(a.toString())
                txt_DCCantidad2.setText(b.toString())
                txt_DCPrecio2.setText(c.toString())
                txt_DCTotal2.setText(d.toString())

                txv_SeleccionCompraID.isEnabled = true
                txt_DCCantidad2.isEnabled = true
                txt_DCPrecio2.isEnabled = true



            }catch (e: java.lang.Exception){

                Toast.makeText(this@MostrarDetalleCompra, e.message+" No existe el detalle de la compra con el id: "+txt_DCID.text.toString(), Toast.LENGTH_SHORT ).show()
                reset()
            }

        })
    }




}