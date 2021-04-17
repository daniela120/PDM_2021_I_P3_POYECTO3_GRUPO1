package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ClienteDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Cliente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)
        btn_regresarCliente.setOnClickListener { Regresar() }

        findViewById<FloatingActionButton>(R.id.idFabListar_Cli).setOnClickListener {
            Mostrar() }


        findViewById<FloatingActionButton>(R.id.idFabConfirmar_Cli).setOnClickListener {
            callServicePostCliente() }


    }


    private fun callServicePostCliente() {

        val clienteInfo = ClienteDataCollectionItem(id = null,
                nombrecompleto = txt_NombreCliente.text.toString(),
                telefono = txt_TelefonoCliente.text.toString().toLong(),
                dni = txt_dniCliente.text.toString().toLong(),
                rtn = txt_dniCliente.text.toString(),
                correo = txt_CorreoCliente2.text.toString(),
                direccion =txt_DireccionCliente.text.toString()
        )

        addCliente(clienteInfo) {
            if (it?.id != null) {
                Toast.makeText(this@Cliente,"CLIENTE GUARDADO EXITOSAMENTE",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@Cliente,"Error",Toast.LENGTH_LONG).show()
            }
        }
    }


    fun addCliente(clienteData: ClienteDataCollectionItem, onResult: (ClienteDataCollectionItem?) -> Unit){
        val retrofit = RestEngine.buildService().create(ClienteService::class.java)
        var result: Call<ClienteDataCollectionItem> = retrofit.addClientes(clienteData)

        result.enqueue(object : Callback<ClienteDataCollectionItem> {
            override fun onFailure(call: Call<ClienteDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(call: Call<ClienteDataCollectionItem>,
                                    response: Response<ClienteDataCollectionItem>) {
                if (response.isSuccessful) {
                    val addedPerson = response.body()!!
                    onResult(addedPerson)
                }
                else if (response.code() == 401){
                    Toast.makeText(this@Cliente,"Sesion expirada",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@Cliente,"Fallo al traer el item",Toast.LENGTH_LONG).show()
                }
            }

        }
        )
    }





    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

    private fun Mostrar() {
        val intent = Intent(this, MostrarCLiente::class.java)
        startActivity(intent)
    }
}
