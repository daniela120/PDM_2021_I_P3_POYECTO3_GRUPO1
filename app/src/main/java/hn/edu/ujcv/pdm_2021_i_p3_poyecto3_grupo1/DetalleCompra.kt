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
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.CompraDetalleDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem


import kotlinx.android.synthetic.main.activity_detalle_compra.*
import kotlinx.android.synthetic.main.activity_compras.*
import kotlinx.android.synthetic.main.activity_produccion.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class DetalleCompra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_compra)
        btn_regresardetalleCompra.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabListardetallecompra).setOnClickListener {
            Mostrar()}
        findViewById<FloatingActionButton>(R.id.idFabConfirmardetallecompra).setOnClickListener {
            guardar()

        }

        findViewById<FloatingActionButton>(R.id.idFabCalcular).setOnClickListener {
            calcular()

        }



        callServiceGetIDCompra()
        txt_DCTotal.isEnabled = false

    }




private fun calcular(){
    var a = txt_DCCantidad.text.toString().toLong()
    var b =txt_DCPrecio.text.toString().toLong()
    var total = a*b

    txt_DCTotal.setText(total.toString())
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
                android.widget.Toast.makeText(this@DetalleCompra, "DETALLE DE COMPRA REGISTRADO", android.widget.Toast.LENGTH_SHORT).show()
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

    /*Spinner Compra*/
    private fun callServiceGetIDCompra() {
        var lista: HashSet<String> = hashSetOf()

        val tipoService:ComprasService= RestEngine.buildService().create(ComprasService::class.java)
        var result: Call<List<ComprasDataCollectionItem>> = tipoService.listCompras()

        result.enqueue(object : Callback<List<ComprasDataCollectionItem>> {
            override fun onFailure(call: Call<List<ComprasDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@DetalleCompra, "Error", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this@DetalleCompra,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ComprasDataCollectionItem>,
                    response: Response<ComprasDataCollectionItem>
            ) {
                idComp = response.body()!!.cai.toString()
                txv_selectIdCompra1.text =  idComp

            }
        })


    }





    private  fun guardar() {
        if (spinnerIdCompra3.toString().isEmpty()) {
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
                            Toast.makeText(this, "Por favor de click eb el boton calcular", Toast.LENGTH_SHORT).show()

                    }else{
                          validacion()
                    }
                }
            }
        }
    }
}

    private fun Mostrar() {
        val intent = Intent(this, MostrarDetalleCompra::class.java)
        startActivity(intent)}

    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

    fun validacion(){

            callServicePostCompraDetalle()


    }
}