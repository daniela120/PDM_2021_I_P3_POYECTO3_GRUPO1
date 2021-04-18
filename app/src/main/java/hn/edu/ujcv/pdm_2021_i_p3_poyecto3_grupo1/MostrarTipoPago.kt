
package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.EmpleadoDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.PagoDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_empleado.*
import kotlinx.android.synthetic.main.activity_mostrar_tipo_pago.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception

class MostrarTipoPago:  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_tipo_pago)
        btn_regresarPago2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarPago)
        botonGetId.setOnClickListener {v -> callServiceGetTipoPago()}
        findViewById<FloatingActionButton>(R.id.idFabActualizarago).setOnClickListener {
            callServicePutTipoPago()
        }

        findViewById<FloatingActionButton>(R.id.idFabEliminarPago).setOnClickListener {
            callServiceDeleteTipoPago()
        }
    }
    private fun Regresar() {
        val intent = Intent(this, TipoPago::class.java)
        startActivity(intent)
    }

    private fun callServiceGetTipoPago() {

        try {
            val pagoService:PagoService = RestEngine.buildService().create(PagoService::class.java)
            var result: Call<PagoDataCollectionItem> = pagoService.getPagoById(txt_IdPago2.text.toString().toLong())
            result.enqueue(object : Callback<PagoDataCollectionItem> {
                override fun onFailure(call: Call<PagoDataCollectionItem>, t: Throwable) {
                    Toast.makeText(this@MostrarTipoPago, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                        call: Call<PagoDataCollectionItem>,
                        response: Response<PagoDataCollectionItem>
                ) {
                    try {
                        var a = response.body()!!.descripcion
                        var b = response.body()!!.estado
                        txt_EstadoPago2.isEnabled = true
                        txt_DescripcionPago2.isEnabled = true
                        txt_EstadoPago2.setText(b)
                        txt_DescripcionPago2.setText(a)
                    } catch (e: Exception) {
                        Toast.makeText(this@MostrarTipoPago, "NO SE ENCONTRO EL TIPO DE PAGO CON EL ID: "+ txt_IdPago2.text.toString(), Toast.LENGTH_SHORT).show()
                    }


                }
            })
        }catch (e:Exception){
            Toast.makeText(this@MostrarTipoPago, "INGRESE UN ID VALIDO", Toast.LENGTH_SHORT).show()
        }


    }



    private fun callServiceDeleteTipoPago() {
        try {


            val pagoService: PagoService = RestEngine.buildService().create(PagoService::class.java)
            var result: Call<ResponseBody> = pagoService.deletePago(txt_IdPago2.text.toString().toLong())

            result.enqueue(object :  Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MostrarTipoPago,"Error",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MostrarTipoPago,"TIPO DE PAGO ELIMINADO",Toast.LENGTH_LONG).show()
                    }
                    else if (response.code() == 401){
                        Toast.makeText(this@MostrarTipoPago,"Sesion expirada",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this@MostrarTipoPago,"Fallo al traer el item",Toast.LENGTH_LONG).show()
                    }
                }
            })
        }catch (e: Exception){
            Toast.makeText(this@MostrarTipoPago,"NO SE PUEDO ELIMINAR EL TIPO DE PAGO CON EL ID: "+ txt_IdEmpleado2.text.toString(),Toast.LENGTH_LONG).show()

        }    }


    private fun callServicePutTipoPago() {

        val pagoInfo = PagoDataCollectionItem(  id = txt_IdPago2.text.toString().toLong(),
                descripcion = txt_DescripcionPago2.text.toString(),
                estado = txt_EstadoPago2.text.toString()

        )

        val retrofit = RestEngine.buildService().create(PagoService::class.java)
        var result: Call<PagoDataCollectionItem> = retrofit.updatePago(pagoInfo)

        result.enqueue(object : Callback<PagoDataCollectionItem> {
            override fun onFailure(call: Call<PagoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarTipoPago,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<PagoDataCollectionItem>,
                                    response: Response<PagoDataCollectionItem>) {
                if (response.isSuccessful) {
                    val updatedPerson = response.body()!!
                    Toast.makeText(this@MostrarTipoPago,"TIPO DE PAGO ACTUALIZADO",Toast.LENGTH_LONG).show()
                }
                else if (response.code() == 401){
                    Toast.makeText(this@MostrarTipoPago,"Sesion expirada",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@MostrarTipoPago,"Fallo al traer el item",Toast.LENGTH_LONG).show()
                }
            }

        })
    }




    fun reset(){
        txt_DescripcionPago2.isEnabled = false
        txt_EstadoPago2.isEnabled = false

    }
}