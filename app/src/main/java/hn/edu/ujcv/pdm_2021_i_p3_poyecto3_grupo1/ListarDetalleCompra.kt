package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.CLIENTES.ClienteService
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ClienteDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.CompraDetalleDataCollectionItem
import kotlinx.android.synthetic.main.activity_listar_detalle_compra.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ListarDetalleCompra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_detalle_compra)
        LISTARDC.setOnClickListener { Regresar() }
        callServiceGetListaDC()
    }
    private fun Regresar() {
        val intent = Intent(this, DetalleCompra::class.java)
        startActivity(intent)
    }


    private fun callServiceGetListaDC() {
        var lista: java.util.HashSet<String> = hashSetOf()

        val tipoService: DetalleCompraService = RestEngine.buildService().create(DetalleCompraService ::class.java)
        var result: Call<List<CompraDetalleDataCollectionItem>> = tipoService.listCompraDetalle()

        result.enqueue(object : Callback<List<CompraDetalleDataCollectionItem>> {
            override fun onFailure(call: Call<List<CompraDetalleDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@ListarDetalleCompra, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<CompraDetalleDataCollectionItem>>,
                    response: Response<List<CompraDetalleDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        lista.add("  "+i.id.toString()+"                    "+i.idcompra+"                            " +i.cantidad )
                    }


                    iniciar2(lista)

                } catch (e: Exception) {
                    println("No hay datos del producto")

                }

            }
        })

    }

    fun iniciar2(a: java.util.HashSet<String>){
        val list = findViewById<ListView>(R.id.list_detalle_compras)

        var valor:String

        var A: java.util.ArrayList<String> = java.util.ArrayList()

        for(i in a){
            val data = i.toString().split(" | ")
            valor=data[0].toString()
            A.add(valor)

        }




        val adaptador = ArrayAdapter(this,android.R.layout.simple_list_item_1,A)

        list.adapter =adaptador
        list.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener { override fun onNothingSelected(parent: AdapterView<*>?) {
        }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {



            }
        }




    }
}