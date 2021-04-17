
package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.DepartamentoDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_departamento.*

class MostrarDepartamento:  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_departamento)
        btn_regresarDepartamento2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarDepartamento)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}

    }
    private fun Regresar() {
        val intent = Intent(this, Departamento::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val departamentoService:DepartamentoService = RestEngine.buildService().create(DepartamentoService::class.java)
        var result: Call<DepartamentoDataCollectionItem> = departamentoService.getDepartamentoById(1)

        result.enqueue(object : Callback<DepartamentoDataCollectionItem> {
            override fun onFailure(call: Call<DepartamentoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarDepartamento,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<DepartamentoDataCollectionItem>,
                    response: Response<DepartamentoDataCollectionItem>
            ) {
                Toast.makeText(this@MostrarDepartamento,"OK"+response.body()!!.nombrecompleto,Toast.LENGTH_LONG).show()
            }
        })
        txt_DescripcionDepartamento2.isEnabled
        txt_NombreDepartamento2.isEnabled

    }
}