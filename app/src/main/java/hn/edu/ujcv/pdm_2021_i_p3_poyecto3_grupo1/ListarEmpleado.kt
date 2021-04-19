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
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.EmpleadoDataCollectionItem
import kotlinx.android.synthetic.main.activity_listar_empleado.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ListarEmpleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_empleado)
        btn_regresarListarEmpleado.setOnClickListener{Regresar()}
        callServiceGetEmpleados()
    }



    private fun callServiceGetEmpleados() {
        var listac: java.util.HashSet<String> = hashSetOf()
        val tipoService: EmpleadoService = RestEngine.buildService().create(EmpleadoService::class.java)
        var result: Call<List<EmpleadoDataCollectionItem>> = tipoService.listEmpleados()

        result.enqueue(object : Callback<List<EmpleadoDataCollectionItem>> {
            override fun onFailure(call: Call<List<EmpleadoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@ListarEmpleado, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<EmpleadoDataCollectionItem>>,
                    response: Response<List<EmpleadoDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        listac.add("    "+i.id.toString()+ "                    "+ i.nombrecompleto )
                    }


                    iniciar2(listac)

                } catch (e: Exception) {
                    println("No hay datos del empleado")

                }

            }
        })

    }

    fun iniciar2(a: java.util.HashSet<String>){
        val list = findViewById<ListView>(R.id.list_empleado)
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
        val intent = Intent(this, Empleado::class.java)
        startActivity(intent)
    }
}