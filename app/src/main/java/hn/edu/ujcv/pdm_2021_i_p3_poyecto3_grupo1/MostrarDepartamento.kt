
package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.DepartamentoDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_departamento.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MostrarDepartamento:  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_departamento)
        btn_regresarDepartamento2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarDepartamento)
        findViewById<FloatingActionButton>(R.id.idFabEliminar_Departamento).setOnClickListener {
            callServiceDeleteDepto()
        }
        findViewById<FloatingActionButton>(R.id.idFabLimpiar_departamento).setOnClickListener {
         resetear()
        }
        findViewById<FloatingActionButton>(R.id.idFabActualizar_Departamento).setOnClickListener {
            callServicePutDepto()
        }
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}

    }
    private fun Regresar() {
        val intent = Intent(this, Departamento::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val departamentoService:DepartamentoService = RestEngine.buildService().create(DepartamentoService::class.java)
        var result: Call<DepartamentoDataCollectionItem> = departamentoService.getDepartamentoById(txt_IdFactura.text.toString().toLong())

        result.enqueue(object : Callback<DepartamentoDataCollectionItem> {
            override fun onFailure(call: Call<DepartamentoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarDepartamento,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<DepartamentoDataCollectionItem>,
                    response: Response<DepartamentoDataCollectionItem>
            ) {
                try {


                    var a = response.body()!!.descripcion
                    var b = response.body()!!.nombre

                    txt_NombreDepartamento2.setText(b)
                    txt_DescripcionDepartamento2.setText(a)
                    txt_DescripcionDepartamento2.isEnabled =  true
                    txt_NombreDepartamento2.isEnabled = true
                }catch (e:Exception){
                    Toast.makeText(this@MostrarDepartamento, "No se ha encontrado el departamento con ID: "+ txt_IdFactura.text.toString(), Toast.LENGTH_SHORT).show()
                }


            }
        })


    }


    fun resetear(){
        txt_NombreDepartamento2.setText("")
        txt_DescripcionDepartamento2.setText("")
        txt_DescripcionDepartamento2.isEnabled = false
        txt_NombreDepartamento2.isEnabled = false

    }

    private fun callServiceDeleteDepto() {
        try {


            val deptoService: DepartamentoService = RestEngine.buildService().create(DepartamentoService::class.java)
            var result: Call<ResponseBody> = deptoService.deleteDepartamento(txt_IdFactura.text.toString().toLong())

            result.enqueue(object :  Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MostrarDepartamento,"Error",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MostrarDepartamento,"DEPTO. ELIMINADO",Toast.LENGTH_LONG).show()
                    }
                    else if (response.code() == 401){
                        Toast.makeText(this@MostrarDepartamento,"Sesion expirada",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this@MostrarDepartamento,"Fallo al traer el item",Toast.LENGTH_LONG).show()
                    }
                }
            })
        }catch (e:Exception){
            Toast.makeText(this@MostrarDepartamento,"NO SE PUEDO ELIMINAR EL CLIENTE CON EL ID: "+ txt_IdCliente2.text.toString(),Toast.LENGTH_LONG).show()

        }    }


    private fun callServicePutDepto() {

        val deptoInfo = DepartamentoDataCollectionItem(  id =txt_IdFactura.text.toString().toLong(),
                nombre = txt_NombreDepartamento2.text.toString(),
                descripcion = txt_DescripcionDepartamento2.text.toString()

        )

        val retrofit = RestEngine.buildService().create(DepartamentoService::class.java)
        var result: Call<DepartamentoDataCollectionItem> = retrofit.updateDepartamento(deptoInfo)

        result.enqueue(object : Callback<DepartamentoDataCollectionItem> {
            override fun onFailure(call: Call<DepartamentoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarDepartamento,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<DepartamentoDataCollectionItem>,
                                    response: Response<DepartamentoDataCollectionItem>) {
                if (response.isSuccessful) {
                    val updatedPerson = response.body()!!
                    Toast.makeText(this@MostrarDepartamento,"DEPT ACTUALIZADO",Toast.LENGTH_LONG).show()
                }
                else if (response.code() == 401){
                    Toast.makeText(this@MostrarDepartamento,"Sesion expirada",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@MostrarDepartamento,"Fallo al traer el item",Toast.LENGTH_LONG).show()
                }
            }

        })
    }



}