package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.EmpleadoDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_empleado.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MostrarEmpleado:  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_empleado)
        btn_regresarEmpleado2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarEmpleado)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}
    }
    private fun Regresar() {
        val intent = Intent(this, Empleado::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val personService:EmpleadoService = RestEngine.buildService().create(EmpleadoService::class.java)
        var result: Call<EmpleadoDataCollectionItem> = personService.getEmpleadoById(1)

        result.enqueue(object : Callback<EmpleadoDataCollectionItem> {
            override fun onFailure(call: Call<EmpleadoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarEmpleado,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<EmpleadoDataCollectionItem>,
                    response: Response<EmpleadoDataCollectionItem>
            ) {
                Toast.makeText(this@MostrarEmpleado,"OK"+response.body()!!.nombrecompleto,Toast.LENGTH_LONG).show()
            }
        })
        txt_CargoEmpleado2.isEnabled
        txt_ClaveEmpleado2.isEnabled
        txt_CorreoEmpleado2.isEnabled
        txt_NombreEmpleado2.isEnabled
        txt_TelefonoEmpleado2.isEnabled

    }

}