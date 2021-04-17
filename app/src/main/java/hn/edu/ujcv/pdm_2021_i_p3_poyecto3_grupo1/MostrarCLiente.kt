package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ClienteDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_cliente.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MostrarCLiente:  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_cliente)
        btn_regresarCliente2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarrCliente)
        findViewById<FloatingActionButton>(R.id.idFabEliminar_Cli).setOnClickListener {
            callServiceDeleteCliente() }
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}
        btn_regresarCliente2.setOnClickListener { Regresar() }
    }
    private fun Regresar() {
            val intent = Intent(this, Cliente::class.java)
            startActivity(intent)
    }
    private fun callServiceGetPerson() {
        val clienteService:ClienteService = RestEngine.buildService().create(ClienteService::class.java)
        var result: Call<ClienteDataCollectionItem> = clienteService.getClienteById(txt_IdCliente2.text.toString().toLong())

        result.enqueue(object : Callback<ClienteDataCollectionItem> {
            override fun onFailure(call: Call<ClienteDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarCLiente,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ClienteDataCollectionItem>,
                    response: Response<ClienteDataCollectionItem>
            ) {
                try {
                        Toast.makeText(this@MostrarCLiente,"OK"+response.body()!!.nombrecompleto,Toast.LENGTH_LONG).show()
                        var nombrecompleto = response.body()!!.nombrecompleto.toString()
                        var telefono = response.body()!!.telefono.toString()
                        var dni = response.body()!!.dni.toString()
                        var rtn = response.body()!!.rtn.toString()
                        var correo = response.body()!!.correo.toString()
                        var direccion = response.body()!!.direccion.toString()

                        txv_NombreCliente.text = nombrecompleto
                        txv_TelefonoCliente.text = telefono
                        txv_DniCliente4.text  = dni
                        txv_RtnCliente.text = rtn
                        txv_CorreoCliente.text = correo
                        txv_DireccionCliente.text = direccion


                }catch (e:Exception){
                    Toast.makeText(this@MostrarCLiente,"NO EXISTE EL CLIENTE CON EL ID" + e.message.toString(), Toast.LENGTH_SHORT).show()

                }





            }
        })
    }

    fun limpiar(){

    }


    private fun callServiceDeleteCliente() {
        val personService:ClienteService = RestEngine.buildService().create(ClienteService::class.java)
        var result: Call<ResponseBody> = personService.deleteCliente(txt_IdCliente2.text.toString().toLong())

        result.enqueue(object :  Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@MostrarCLiente,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MostrarCLiente,"DELETE",Toast.LENGTH_LONG).show()
                }
                else if (response.code() == 401){
                    Toast.makeText(this@MostrarCLiente,"Sesion expirada",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@MostrarCLiente,"Fallo al traer el item",Toast.LENGTH_LONG).show()
                }
            }
        })
    }



}