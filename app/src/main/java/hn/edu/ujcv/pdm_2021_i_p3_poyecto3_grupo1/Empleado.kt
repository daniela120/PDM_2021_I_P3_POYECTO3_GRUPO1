package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_empleado.*
import kotlinx.android.synthetic.main.activity_productos.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.EmpleadoDataCollectionItem


class Empleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado)
        val botonGetId = findViewById<Button>(R.id.btn_BuscarEmpleado)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}
        btn_regresarEmpleado.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabListar_Empleado).setOnClickListener {
            Mostrar() }

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
    }*/

    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

    private fun Mostrar() {
        val intent = Intent(this, MostrarEmpleado::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val personService:PersonService = RestEngine.buildService().create(PersonService::class.java)
        var result: Call<EmpleadoDataCollectionItem> = personService.getPersonById(1)

        result.enqueue(object : Callback<EmpleadoDataCollectionItem> {
            override fun onFailure(call: Call<EmpleadoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@Empleado,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<EmpleadoDataCollectionItem>,
                response: Response<EmpleadoDataCollectionItem>
            ) {
                Toast.makeText(this@Empleado,"OK"+response.body()!!.nombrecompleto,Toast.LENGTH_LONG).show()
            }
        })
    }

}