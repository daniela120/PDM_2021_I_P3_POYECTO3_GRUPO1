package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.EmpleadoDataCollectionItem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_login.setOnClickListener { callServiceGetPerson() }
    }



        private fun callServiceGetPerson() {
            try {
                val personService:EmpleadoService = RestEngine.buildService().create(EmpleadoService::class.java)
                var usuario:String = txt_Usuario.text.toString()
                var result: Call<EmpleadoDataCollectionItem> = personService.getEmpleadoById(usuario.toLong())

                result.enqueue(object : Callback<EmpleadoDataCollectionItem> {
                    override fun onFailure(call: Call<EmpleadoDataCollectionItem>, t: Throwable) {
                        Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                            call: Call<EmpleadoDataCollectionItem>,
                            response: Response<EmpleadoDataCollectionItem>
                    ) {


                        var contrasena:String = txt_Password.text.toString()

                        if(response.body()!!.id!!.equals(usuario.toLong()) && response.body()!!.clave.equals(contrasena)){
                            Toast.makeText(this@MainActivity,"BIENVENIDO "+response.body()!!.nombrecompleto,Toast.LENGTH_LONG).show()
                            goahead()

                        }else{
                            println("CONTRA O USUARIO MALOS")
                            Toast.makeText(this@MainActivity, "El usuario o la contrasena esta incorrecta",Toast.LENGTH_SHORT).show()
                        }
                    }
                })


            }catch (e:Exception){
                Toast.makeText(this@MainActivity, "EL USUARIO ES INVALIDO", Toast.LENGTH_SHORT).show()
            }





}
    fun goahead(){
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

}