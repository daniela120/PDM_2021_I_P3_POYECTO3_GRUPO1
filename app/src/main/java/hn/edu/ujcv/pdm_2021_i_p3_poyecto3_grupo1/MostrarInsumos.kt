package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton

import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.InsumosDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_insumos.*
import kotlinx.android.synthetic.main.activity_mostrar_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_departamento.*
import kotlinx.android.synthetic.main.activity_mostrar_insumos.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception

class MostrarInsumos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_insumos)
        btn_regresarInsumos2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarInsumos)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}
        findViewById<FloatingActionButton>(R.id.idFabLimpiar_Insumo).setOnClickListener {
            reset()

        }
        findViewById<FloatingActionButton>(R.id.idFabActualizar_Insumos).setOnClickListener {
            callServicePutInsumos()
        }
        findViewById<FloatingActionButton>(R.id.idFabEliminar_Insumos).setOnClickListener {
            callServiceDeleteInsumos()
        }
    }

    private fun Regresar() {
        val intent = Intent(this, Insumos::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        try {
            val insumosService:InsumosService = RestEngine.buildService().create(InsumosService::class.java)
            var result: Call<InsumosDataCollectionItem> = insumosService.getInsumosById(txt_IdInsumo2.text.toString().toLong())

            result.enqueue(object : Callback<InsumosDataCollectionItem> {
                override fun onFailure(call: Call<InsumosDataCollectionItem>, t: Throwable) {
                    Toast.makeText(this@MostrarInsumos, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                        call: Call<InsumosDataCollectionItem>,
                        response: Response<InsumosDataCollectionItem>
                ) {
                    try {


                        var a = response.body()!!.nombre
                        var b = response.body()!!.tipo
                        var c = response.body()!!.cantidad
                        var e = response.body()!!.preciocompra
                        var f = response.body()!!.precioventa

                        txt_MostrarInsNombre.setText(a)
                        txt_MostrarInsTipo.setText(b)
                        txt_MostrarInsCantidad.setText(c.toString())
                        txt_MostrarInsPCompra.setText(e.toString())
                        txt_MostrarInsPVenta.setText(f.toString())

                        txt_MostrarInsNombre.isEnabled = true
                        txt_MostrarInsTipo.isEnabled = true
                        txt_MostrarInsCantidad.isEnabled = true
                        txt_MostrarInsPCompra.isEnabled = true
                        txt_MostrarInsPVenta.isEnabled = true
                    } catch (e: Exception) {
                        Toast.makeText(this@MostrarInsumos, "NO SE ENCONTRO EL INSUMO CON ID: "+txt_IdInsumo2.text.toString(), Toast.LENGTH_SHORT).show()

                    }

                }
            })
        }catch (e:Exception){
        Toast.makeText(this@MostrarInsumos, "ERROR VERIFIQUE LOS DATOS INGRESADOS", Toast.LENGTH_SHORT).show()
        }
        }

    fun reset(){
        txt_IdInsumo2.setText("")
        txt_MostrarInsNombre.setText("")
        txt_MostrarInsTipo.setText("")
        txt_MostrarInsCantidad.setText("")
        txt_MostrarInsPCompra.setText("")
        txt_MostrarInsPVenta.setText("")

        txt_MostrarInsNombre.isEnabled = true
        txt_MostrarInsTipo.isEnabled = true
        txt_MostrarInsCantidad.isEnabled = true
        txt_MostrarInsPCompra.isEnabled = true
        txt_MostrarInsPVenta.isEnabled = true
    }
//Actualizar
private fun callServicePutInsumos() {
    try {


        val insumosinfo = InsumosDataCollectionItem(id = txt_IdInsumo2.text.toString().toLong(),
                nombre = txt_MostrarInsNombre.text.toString(),
                tipo = txt_MostrarInsTipo.text.toString(),
                cantidad = txt_MostrarInsCantidad.text.toString().toLong(),
                preciocompra = txt_MostrarInsPCompra.text.toString().toLong(),
                precioventa = txt_MostrarInsPVenta.text.toString().toLong()
        )


        val retrofit = RestEngine.buildService().create(InsumosService::class.java)
        var result: Call<InsumosDataCollectionItem> = retrofit.updateInsumos(insumosinfo)


        result.enqueue(object : Callback<InsumosDataCollectionItem> {
            override fun onFailure(call: Call<InsumosDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarInsumos, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<InsumosDataCollectionItem>,
                                    response: Response<InsumosDataCollectionItem>) {
                if (response.isSuccessful) {
                    val updatedPerson = response.body()!!
                    Toast.makeText(this@MostrarInsumos, " INSUMO ACTUALIZADO", Toast.LENGTH_LONG).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@MostrarInsumos, "Sesion expirada", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@MostrarInsumos, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                }
            }

        })
    }catch (e:Exception){
        Toast.makeText(this@MostrarInsumos, "ERROR, POR FAVOR VERIFIQUE LOS DATOS", Toast.LENGTH_SHORT).show()
    }

}
    //Metodo Delete
    private fun callServiceDeleteInsumos() {
        try {


            val insumosService: InsumosService = RestEngine.buildService().create(InsumosService::class.java)
            var result: Call<ResponseBody> = insumosService.deleteInsumos(txt_IdInsumo2.text.toString().toLong())

            result.enqueue(object :  Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MostrarInsumos,"Error",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MostrarInsumos,"DEPTO. ELIMINADO",Toast.LENGTH_LONG).show()
                    }
                    else if (response.code() == 401){
                        Toast.makeText(this@MostrarInsumos,"Sesion expirada",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this@MostrarInsumos,"Fallo al traer el item",Toast.LENGTH_LONG).show()
                    }
                }
            })
        }catch (e: java.lang.Exception){
            Toast.makeText(this@MostrarInsumos,"NO SE PUEDO ELIMINAR EL CLIENTE CON EL ID: "+ txt_IdInsumo2.text.toString(),Toast.LENGTH_LONG).show()

        }    }
}