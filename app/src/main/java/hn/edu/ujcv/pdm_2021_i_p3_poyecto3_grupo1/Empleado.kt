package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.EmpleadoDataCollectionItem
import kotlinx.android.synthetic.main.activity_empleado.*
import kotlinx.android.synthetic.main.activity_productos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Empleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado)
        val botonguardar = findViewById<Button>(R.id.btn_BuscarEmpleado)
        botonguardar.setOnClickListener {v -> callServicePostPerson()}



    }


    private fun callServicePostPerson() {
        val fecha = "2021-04-10"
        val personInfo = EmpleadoDataCollectionItem(id = null,
            nombrecompleto = txt_NombreEmpleado.text.toString(),
            telefono = txt_TelefonoEmpleado.text.toString(),
            correo = txt_CorreoCliente.text.toString(),
            direccion = "EL SAUCE",
            dni = "1234",
            rtn = "10332"


        )

        addPerson(personInfo) {
            if (it?.id != null) {
                Toast.makeText(this@Empleado,"OK"+it?.id,Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@Empleado,"Error",Toast.LENGTH_LONG).show()
            }
        }
    }


    fun addPerson(personData: EmpleadoDataCollectionItem, onResult: (EmpleadoDataCollectionItem?) -> Unit){
        val retrofit = RestEngine.buildService().create(PersonService::class.java)
        var result: Call<EmpleadoDataCollectionItem> = retrofit.addPerson(personData)

        result.enqueue(object : Callback<EmpleadoDataCollectionItem> {
            override fun onFailure(call: Call<EmpleadoDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(call: Call<EmpleadoDataCollectionItem>,
                                    response: Response<EmpleadoDataCollectionItem>) {
                if (response.isSuccessful) {
                    val addedPerson = response.body()!!
                    onResult(addedPerson)
                }
                else if (response.code() == 401){
                    Toast.makeText(this@Empleado,"Sesion expirada",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@Empleado,"Fallo al traer el item",Toast.LENGTH_LONG).show()
                }
            }

        }
        )
    }


    /*private  fun guardar() {

        if (txtId.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID del Empleado", Toast.LENGTH_SHORT).show()
        }else {
            if (txtNombre.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un Nombre", Toast.LENGTH_SHORT).show()
            } else {
                if (txtApellido.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese un Apellido", Toast.LENGTH_SHORT).show()
                } else {
                    if (txtTel.text.toString().isEmpty()) {
                        Toast.makeText(this, "Ingrese un telefono", Toast.LENGTH_SHORT).show()
                    } else {
                        if (txtCorreo.text.toString().isEmpty()) {
                            Toast.makeText(this, "Inreges un Correo", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "Realizada con exito!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }*/



}