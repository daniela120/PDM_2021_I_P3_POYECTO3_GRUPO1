package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.InsumosDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentaDetalleDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentasDataCollectionItem
import kotlinx.android.synthetic.main.activity_listar_ventas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ListarVentas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_ventas)
        btn_regresarListarVentas.setOnClickListener { Regresar() }
        callServiceGetListaV()
    }

    private fun Regresar() {
        val intent = Intent(this, Ventas::class.java)
        startActivity(intent)
    }

    private fun callServiceGetListaV() {
        var lista: java.util.HashSet<String> = hashSetOf()

        val tipoService:VentasService= RestEngine.buildService().create(VentasService ::class.java)
        var result: Call<List<VentasDataCollectionItem>> = tipoService.listVentas()

        result.enqueue(object : Callback<List<VentasDataCollectionItem>> {
            override fun onFailure(call: Call<List<VentasDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@ListarVentas,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<VentasDataCollectionItem>>,
                    response: Response<List<VentasDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        lista.add("  "+i.id.toString()+"                    "+i.descripcion+"                            " +i.numerotarjeta)
                    }


                    iniciar2(lista)

                } catch (e: Exception) {
                    println("No hay datos del producto")

                }

            }
        })

    }

    fun iniciar2(a: java.util.HashSet<String>){
        val list = findViewById<ListView>(R.id.list_ventas)

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