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
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.EmpleadoDataCollectionItem
import kotlinx.android.synthetic.main.activity_compras.*


class Empleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado)
        btn_regresarEmpleado.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabListar_Empleado).setOnClickListener {
            Mostrar() }

        findViewById<FloatingActionButton>(R.id.idFabConfirmar_Empleado).setOnClickListener {
            callServicePostEmpleado() }
    }


    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

    private fun Mostrar() {
        val intent = Intent(this, MostrarEmpleado::class.java)
        startActivity(intent)
    }


    private fun callServicePostEmpleado() {

        val empleadoInfo = EmpleadoDataCollectionItem(id = null,
              nombrecompleto = txt_NombreEmpleado.text.toString(),
                telefono = txt_TelefonoEmpleado.text.toString().toLong(),
                correo = txt_CorreoEmpleado.text.toString(),
                clave = txt_ClaveEmpleado.text.toString(),
                cargo = txt_CargoEmpleado.text.toString()
        )

        addEmpleado(empleadoInfo) {
            if (it?.id != null) {
                Toast.makeText(this@Empleado, "EMPLEADO AGREGADO", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@Empleado, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }


    fun addEmpleado(empleadoData: EmpleadoDataCollectionItem, onResult: (EmpleadoDataCollectionItem?) -> Unit){
        val retrofit = RestEngine.buildService().create(EmpleadoService::class.java)
        var result: Call<EmpleadoDataCollectionItem> = retrofit.addEmpleado(empleadoData)

        result.enqueue(object : Callback<EmpleadoDataCollectionItem> {
            override fun onFailure(call: Call<EmpleadoDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(call: Call<EmpleadoDataCollectionItem>,
                                    response: Response<EmpleadoDataCollectionItem>) {
                if (response.isSuccessful) {
                    val addedPerson = response.body()!!
                    onResult(addedPerson)
                } else if (response.code() == 401) {
                    Toast.makeText(this@Empleado, "Sesion expirada", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@Empleado, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                }
            }


        }
        )
    }




}