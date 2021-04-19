package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.PagoDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProductoDataCollectionItem
import kotlinx.android.synthetic.main.activity_listar_tipo_pago.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ListarTipoPago : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_tipo_pago)
        btn_regresarListarPago.setOnClickListener { Regresar() }
        callServiceGetListatp()
    }

    private fun Regresar() {
        val intent = Intent(this, TipoPago::class.java)
        startActivity(intent)
    }
    private fun callServiceGetListatp() {
        var lista: java.util.HashSet<String> = hashSetOf()

        val tipoService: PagoService = RestEngine.buildService().create( PagoService ::class.java)
        var result: Call<List<PagoDataCollectionItem>> = tipoService.listPagos()

        result.enqueue(object : Callback<List<PagoDataCollectionItem>> {
            override fun onFailure(call: Call<List<PagoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@ListarTipoPago ,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<PagoDataCollectionItem>>,
                    response: Response<List<PagoDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        lista.add("  "+i.id.toString()+"                    "+i.descripcion+"                            " +i.estado)
                    }


                    iniciar2(lista)

                } catch (e: Exception) {
                    println("No hay datos del producto")

                }

            }
        })

    }

    fun iniciar2(a: java.util.HashSet<String>){
        val list = findViewById<ListView>(R.id.list_Pago)

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