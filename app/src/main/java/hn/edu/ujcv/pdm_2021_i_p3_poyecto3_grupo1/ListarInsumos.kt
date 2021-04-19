package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.CompraDetalleDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.InsumosDataCollectionItem
import kotlinx.android.synthetic.main.activity_listar_insumos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ListarInsumos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_insumos)
        btn_RlistarInsumo.setOnClickListener { Regresar() }
        callServiceGetListaI()
    }

    private fun Regresar() {
        val intent = Intent(this, Insumos::class.java)
        startActivity(intent)

    }


    private fun callServiceGetListaI() {
        var lista: java.util.HashSet<String> = hashSetOf()

        val tipoService:InsumosService= RestEngine.buildService().create(InsumosService ::class.java)
        var result: Call<List<InsumosDataCollectionItem>> = tipoService.listInsumos()

        result.enqueue(object : Callback<List<InsumosDataCollectionItem>> {
            override fun onFailure(call: Call<List<InsumosDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@ListarInsumos ,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<InsumosDataCollectionItem>>,
                    response: Response<List<InsumosDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        lista.add("  "+i.id.toString()+"                    "+i.nombre+"                            " +i.tipo)
                    }


                    iniciar2(lista)

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