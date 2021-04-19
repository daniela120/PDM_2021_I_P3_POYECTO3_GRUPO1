package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProveedoresDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentaDetalleDataCollectionItem
import kotlinx.android.synthetic.main.activity_listar_detalle_venta.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ListarDetalleVenta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_detalle_venta)
        setSupportActionBar(findViewById(R.id.toolbar))
        btn_regresarListarDVenta.setOnClickListener { Regresar() }
        callServiceGetDetalleVenta()


    }




    private fun callServiceGetDetalleVenta() {
        var listac: java.util.HashSet<String> = hashSetOf()
        val tipoService: DetalleVentaService = RestEngine.buildService().create(DetalleVentaService::class.java)
        var result: Call<List<VentaDetalleDataCollectionItem>> = tipoService.listVentaDetalle()

        result.enqueue(object : Callback<List<VentaDetalleDataCollectionItem>> {
            override fun onFailure(call: Call<List<VentaDetalleDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@ListarDetalleVenta, "Error", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                    call: Call<List<VentaDetalleDataCollectionItem>>,
                    response: Response<List<VentaDetalleDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        listac.add("  "+i.id.toString()+ "                            "+ i.idproducto+  "                                    "+ i.cantidad+ "                   "+i.precio)
                    }


                    iniciar2(listac)

                } catch (e: Exception) {
                    println("No hay datos del empleado")

                }

            }
        })

    }

    fun iniciar2(a: java.util.HashSet<String>){
        val list = findViewById<ListView>(R.id.list_detalleventas)
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
        val intent = Intent(this, DetalleVenta::class.java)
        startActivity(intent)
    }
}