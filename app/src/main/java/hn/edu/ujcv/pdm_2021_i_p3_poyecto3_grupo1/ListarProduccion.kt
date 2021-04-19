package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.EmpleadoDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProduccionDataCollectionItem
import kotlinx.android.synthetic.main.activity_listar_produccion.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ListarProduccion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_produccion)
        btn_regresarListarProduccion.setOnClickListener { Regresar() }
        callServiceGetEmpleados()
    }





    private fun callServiceGetEmpleados() {
        var listac: java.util.HashSet<String> = hashSetOf()
        val tipoService: ProduccionService = RestEngine.buildService().create(ProduccionService::class.java)
        var result: Call<List<ProduccionDataCollectionItem>> = tipoService.listProduccion()

        result.enqueue(object : Callback<List<ProduccionDataCollectionItem>> {
            override fun onFailure(call: Call<List<ProduccionDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@ListarProduccion, "Error", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                    call: Call<List<ProduccionDataCollectionItem>>,
                    response: Response<List<ProduccionDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        listac.add("    "+i.id.toString()+ "          "+ i.descripcion+  "      "+ i.tiempo)
                    }


                    iniciar2(listac)

                } catch (e: Exception) {
                    println("No hay datos del empleado")

                }

            }
        })

    }

    fun iniciar2(a: java.util.HashSet<String>){
        val list = findViewById<ListView>(R.id.list_produccion)
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



    private fun Regresar() {
        val intent = Intent(this, Produccion::class.java)
        startActivity(intent)
    }
}