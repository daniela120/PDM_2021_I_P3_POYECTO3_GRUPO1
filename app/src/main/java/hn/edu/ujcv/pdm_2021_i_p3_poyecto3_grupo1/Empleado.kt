package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.PersonDataCollectionItem
import kotlinx.android.synthetic.main.activity_empleado.*
import kotlinx.android.synthetic.main.activity_productos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Empleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado)
        val botonGetId = findViewById<Button>(R.id.btn_BuscarEmpleado)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}

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

    private fun callServiceGetPerson() {
        val personService:PersonService = RestEngine.buildService().create(PersonService::class.java)
        var result: Call<PersonDataCollectionItem> = personService.getPersonById(1)

        result.enqueue(object : Callback<PersonDataCollectionItem> {
            override fun onFailure(call: Call<PersonDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@Empleado,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<PersonDataCollectionItem>,
                response: Response<PersonDataCollectionItem>
            ) {
                Toast.makeText(this@Empleado,"OK"+response.body()!!.nombrecompleto,Toast.LENGTH_LONG).show()
            }
        })
    }

}