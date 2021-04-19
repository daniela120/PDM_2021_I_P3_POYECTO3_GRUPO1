package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.CLIENTES.Cliente
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.CLIENTES.ClienteService
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
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}
        btn_regresarCliente2.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabEliminar_Cli).setOnClickListener{
            callServiceDeletePerson()
        }



        findViewById<FloatingActionButton>(R.id.idFabActualizar_Cli).setOnClickListener{
            callServicePutCliente()
        }
        findViewById<FloatingActionButton>(R.id.idFabLimpiar_Cliente).setOnClickListener {
            resteo()
        }


    }
        private fun Regresar() {
            val intent = Intent(this,Cliente::class.java)
            startActivity(intent)
        }


    private fun callServiceGetPerson() {
        val clienteService: ClienteService = RestEngine.buildService().create(ClienteService::class.java)
        var result: Call<ClienteDataCollectionItem> = clienteService.getClienteById(txt_IdCliente2.text.toString().toLong())

        result.enqueue(object : Callback<ClienteDataCollectionItem> {
            override fun onFailure(call: Call<ClienteDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarCLiente,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ClienteDataCollectionItem>,
                    response: Response<ClienteDataCollectionItem>
            )

            {
                try {




                var a= response.body()!!.nombrecompleto
                var c = response.body()!!.telefono
                var d = response.body()!!.dni
                var e = response.body()!!.rtn
                var f = response.body()!!.correo
                var b = response.body()!!.direccion
                activar()

                txt_NombreCliente2.setText(a)
                txt_DireccionCliente2.setText(b)
                txt_TelefonoCliente2.setText(c.toString())
                txt_dniCliente2.setText(d.toString())
                txt_rtnCliente2.setText(e)
                txt_CorreoCliente2.setText(f)
                }catch (e:Exception){
                    Toast.makeText(this@MostrarCLiente, e.message+" No se encontro el cliente con ID: "+ txt_IdCliente2.text.toString(), Toast.LENGTH_SHORT).show()

                }

            }
        })
    }

    fun activar(){
        txt_NombreCliente2.isEnabled = true
        txt_DireccionCliente2.isEnabled = true
        txt_TelefonoCliente2.isEnabled = true
        txt_dniCliente2.isEnabled = true
        txt_rtnCliente2.isEnabled = true
        txt_CorreoCliente2.isEnabled = true
    }

    fun resteo(){
        txt_NombreCliente2.isEnabled = false
        txt_DireccionCliente2.isEnabled = false
        txt_TelefonoCliente2.isEnabled = false
        txt_dniCliente2.isEnabled = false
        txt_rtnCliente2.isEnabled = false
        txt_CorreoCliente2.isEnabled = false

        txt_IdCliente2.setText("")
        txt_NombreCliente2.setText("")
        txt_DireccionCliente2.setText("")
        txt_TelefonoCliente2.setText("")
        txt_dniCliente2.setText("")
        txt_rtnCliente2.setText("")
        txt_CorreoCliente2.setText("")
    }


    private fun callServiceDeletePerson() {
        try {


        val clienteService:ClienteService = RestEngine.buildService().create(ClienteService::class.java)
        var result: Call<ResponseBody> = clienteService.deleteCliente(txt_IdCliente2.text.toString().toLong())

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
    }catch (e:Exception){
            Toast.makeText(this@MostrarCLiente,"NO SE PUEDO ELIMINAR EL CLIENTE CON EL ID: "+ txt_IdCliente2.text.toString(),Toast.LENGTH_LONG).show()

        }    }


    private fun callServicePutCliente() {

        val clienteInfo = ClienteDataCollectionItem(  id = txt_IdCliente2.text.toString().toLong(),
                nombrecompleto = txt_NombreCliente2.text.toString(),
                telefono = txt_TelefonoCliente2.text.toString().toLong(),
                dni = txt_dniCliente2.text.toString().toLong(),
                rtn = txt_rtnCliente2.text.toString(),
                correo = txt_CorreoCliente2.text.toString(),
                direccion = txt_DireccionCliente2.text.toString()

        )

        val retrofit = RestEngine.buildService().create(ClienteService::class.java)
        var result: Call<ClienteDataCollectionItem> = retrofit.updateCliente(clienteInfo)

        result.enqueue(object : Callback<ClienteDataCollectionItem> {
            override fun onFailure(call: Call<ClienteDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarCLiente,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ClienteDataCollectionItem>,
                                    response: Response<ClienteDataCollectionItem>) {
                if (response.isSuccessful) {
                    val updatedPerson = response.body()!!
                    Toast.makeText(this@MostrarCLiente,"OK"+response.body()!!.nombrecompleto,Toast.LENGTH_LONG).show()
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