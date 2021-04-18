package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.DepartamentoDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.EmpleadoDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_departamento.*
import kotlinx.android.synthetic.main.activity_mostrar_empleado.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MostrarEmpleado:  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_empleado)
        btn_regresarEmpleado2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarEmpleado)
        findViewById<FloatingActionButton>(R.id.idFabActualizar_Empleado).setOnClickListener {
            callServicePutDepto()
        }
        findViewById<FloatingActionButton>(R.id.idFabEliminar_Empleado).setOnClickListener {
            callServiceDeleteDepto()
        }
        botonGetId.setOnClickListener {v -> callServiceGetEmpleado()}
    }
    private fun Regresar() {
        val intent = Intent(this, Empleado::class.java)
        startActivity(intent)
    }

    private fun callServiceGetEmpleado() {
        val personService:EmpleadoService = RestEngine.buildService().create(EmpleadoService::class.java)
        var result: Call<EmpleadoDataCollectionItem> = personService.getEmpleadoById(txt_IdEmpleado2.text.toString().toLong())

        result.enqueue(object : Callback<EmpleadoDataCollectionItem> {
            override fun onFailure(call: Call<EmpleadoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarEmpleado,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<EmpleadoDataCollectionItem>,
                    response: Response<EmpleadoDataCollectionItem>
            ) {
                try {
                    var a = response.body()!!.nombrecompleto
                    var b = response.body()!!.telefono
                    var c = response.body()!!.correo
                    var d = response.body()!!.clave
                    var e = response.body()!!.cargo


                    txt_CargoEmpleado2.isEnabled = true
                    txt_ClaveEmpleado2.isEnabled = true
                    txt_CorreoEmpleado2.isEnabled = true
                    txt_NombreEmpleado2.isEnabled = true
                    txt_TelefonoEmpleado2.isEnabled = true

                    txt_CargoEmpleado2.setText(e)
                    txt_ClaveEmpleado2.setText(d)
                    txt_CorreoEmpleado2.setText(c)
                    txt_NombreEmpleado2.setText(a)
                    txt_TelefonoEmpleado2.setText(b.toString())




                }catch (e:Exception){
                    Toast.makeText(this@MostrarEmpleado, "No se encontro el empleado con el ID: "+ txt_IdEmpleado2.text.toString(), Toast.LENGTH_SHORT).show()

                }


            }
        })


    }



    private fun callServiceDeleteDepto() {
        try {


            val EmpleadoService: EmpleadoService = RestEngine.buildService().create(EmpleadoService::class.java)
            var result: Call<ResponseBody> = EmpleadoService.deleteEmpleado(txt_IdEmpleado2.text.toString().toLong())

            result.enqueue(object :  Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MostrarEmpleado,"Error",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MostrarEmpleado,"EMPLEADO. ELIMINADO",Toast.LENGTH_LONG).show()
                    }
                    else if (response.code() == 401){
                        Toast.makeText(this@MostrarEmpleado,"Sesion expirada",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this@MostrarEmpleado,"Fallo al traer el item",Toast.LENGTH_LONG).show()
                    }
                }
            })
        }catch (e:Exception){
            Toast.makeText(this@MostrarEmpleado,"NO SE PUEDO ELIMINAR EL EMPLEADO CON EL ID: "+ txt_IdEmpleado2.text.toString(),Toast.LENGTH_LONG).show()

        }    }


    private fun callServicePutDepto() {

        val empleadoInfo = EmpleadoDataCollectionItem(  id = txt_IdEmpleado2.text.toString().toLong(),
               nombrecompleto = txt_NombreEmpleado2.text.toString(),
                telefono = txt_TelefonoEmpleado2.text.toString().toLong(),
                correo = txt_CorreoEmpleado2.text.toString(),
                clave = txt_ClaveEmpleado2.text.toString(),
                cargo = txt_CargoEmpleado2.text.toString()

        )

        val retrofit = RestEngine.buildService().create(EmpleadoService::class.java)
        var result: Call<EmpleadoDataCollectionItem> = retrofit.updateEmpleado(empleadoInfo)

        result.enqueue(object : Callback<EmpleadoDataCollectionItem> {
            override fun onFailure(call: Call<EmpleadoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarEmpleado,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<EmpleadoDataCollectionItem>,
                                    response: Response<EmpleadoDataCollectionItem>) {
                if (response.isSuccessful) {
                    val updatedPerson = response.body()!!
                    Toast.makeText(this@MostrarEmpleado,"EMPLEADO ACTUALIZADO",Toast.LENGTH_LONG).show()
                }
                else if (response.code() == 401){
                    Toast.makeText(this@MostrarEmpleado,"Sesion expirada",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@MostrarEmpleado,"Fallo al traer el item",Toast.LENGTH_LONG).show()
                }
            }

        })
    }


    fun reset(){
        txt_CargoEmpleado2.isEnabled = false
        txt_ClaveEmpleado2.isEnabled = false
        txt_CorreoEmpleado2.isEnabled = false
        txt_NombreEmpleado2.isEnabled = false
        txt_TelefonoEmpleado2.isEnabled = false

        txt_CargoEmpleado2.setText("")
        txt_ClaveEmpleado2.setText("")
        txt_CorreoEmpleado2.setText("")
        txt_NombreEmpleado2.setText("")
        txt_TelefonoEmpleado2.setText("")
    }

}