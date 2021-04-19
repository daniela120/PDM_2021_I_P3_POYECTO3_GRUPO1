package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem
import kotlinx.android.synthetic.main.activity_listar_cliente.*
import kotlinx.android.synthetic.main.activity_listar_compras.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ListarCompras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_compras)
        btn_regresarListarC.setOnClickListener { regresar() }
        callServiceGetProducto1()
       
    }
    private fun regresar(){
        val intent = Intent(this,Compras::class.java)
        startActivity(intent)
    }
    private fun callServiceGetProducto1() {
        var listac: java.util.HashSet<String> = hashSetOf()
        val tipoService: ComprasService = RestEngine.buildService().create(ComprasService::class.java)
        var result: Call<List<ComprasDataCollectionItem>> = tipoService.listCompras()

        result.enqueue(object : Callback<List<ComprasDataCollectionItem>> {
            override fun onFailure(call: Call<List<ComprasDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@ListarCompras, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<ComprasDataCollectionItem>>,
                    response: Response<List<ComprasDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        listac.add("    "+i.id.toString()+ "                    "+ i.cai )
                    }


                    iniciar2(listac)

                } catch (e: Exception) {
                    println("No hay datos del producto")

                }

            }
        })

    }

    fun iniciar2(a: java.util.HashSet<String>){
        val list = findViewById<ListView>(R.id.list_INSUMOS)
        var valor:String

        var A: java.util.ArrayList<String> = java.util.ArrayList()

        for(i in a){
            val data = i.toString().split("|")
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