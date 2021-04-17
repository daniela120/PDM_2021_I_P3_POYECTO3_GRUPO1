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


    }


    private fun callServicePostCliente() {

        val clienteInfo = ClienteDataCollectionItem(
                nombrecompleto = txt_NombreCliente.text.toString(),
                telefono = txt_TelefonoCliente.text.toString().toLong(),
                dni = txt_dniCliente.text.toString().toLong(),
                rtn = txt_dniCliente.text.toString(),
                correo = txt_CorreoCliente2.text.toString(),
                direccion =txt_DireccionCliente.text.toString()
        )

        addPerson(personInfo) {
            if (it?.id != null) {
                Toast.makeText(this@MainActivity,"OK"+it?.id,Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_LONG).show()
            }
        }
    }


    fun addPerson(clienteData: ClienteDataCollectionItem, onResult: (ClienteDataCollectionItem?) -> Unit){
        val retrofit = RestEngine.buildService().create(Cliente::class.java)
        var result: Call<PersonDataCollectionItem> = retrofit.addPerson(personData)

        result.enqueue(object : Callback<PersonDataCollectionItem> {
            override fun onFailure(call: Call<PersonDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(call: Call<PersonDataCollectionItem>,
                                    response: Response<PersonDataCollectionItem>) {
                if (response.isSuccessful) {
                    val addedPerson = response.body()!!
                    onResult(addedPerson)
                }
                else if (response.code() == 401){
                    Toast.makeText(this@MainActivity,"Sesion expirada",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@MainActivity,"Fallo al traer el item",Toast.LENGTH_LONG).show()
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
