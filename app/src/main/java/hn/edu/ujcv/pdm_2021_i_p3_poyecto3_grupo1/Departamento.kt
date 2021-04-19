package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.DepartamentoDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*

import kotlinx.android.synthetic.main.activity_departamento.*
import kotlinx.android.synthetic.main.activity_proveedores.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Departamento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamento)
        btn_regresarDepartamento.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabListar_Departamento).setOnClickListener {
            Mostrar() }
        findViewById<FloatingActionButton>(R.id.idFabConfirmar_Departamento).setOnClickListener {
            guardar() }

        findViewById<FloatingActionButton>(R.id.idFabListar_Departamento).setOnClickListener {
            ir() }



    }
    private fun ir() {
        val intent = Intent(this,ListarDepartamento::class.java)
        startActivity(intent)
    }

    private fun callServicePostDepartamento() {

        val departamentosInfo = DepartamentoDataCollectionItem(id = null,
                nombre = txt_NombreDepartamento.text.toString(),
                descripcion = txt_DescripcionDepartamento.text.toString()
        )



        addDepartamento(departamentosInfo) {
            if (it?.id != null) {
                android.widget.Toast.makeText(this@Departamento, "OK" + it?.id, android.widget.Toast.LENGTH_LONG).show()
            } else {
                android.widget.Toast.makeText(this@Departamento, "Error", android.widget.Toast.LENGTH_LONG).show()
            }
        }
    }

    fun addDepartamento(departamentoData: DepartamentoDataCollectionItem, onResult: (DepartamentoDataCollectionItem?) -> Unit) {
        val retrofit = RestEngine.buildService().create(DepartamentoService::class.java)
        var result: Call<DepartamentoDataCollectionItem> = retrofit.addDepartamento(departamentoData)

        result.enqueue(object : Callback<DepartamentoDataCollectionItem> {
            override fun onFailure(call: Call<DepartamentoDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(call: Call<DepartamentoDataCollectionItem>,
                                    response: Response<DepartamentoDataCollectionItem>) {
                if (response.isSuccessful) {
                    val addedDepartamento= response.body()!!
                    onResult(addedDepartamento)
                } else if (response.code() == 401) {
                    Toast.makeText(this@Departamento, "Sesion expirada", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@Departamento, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                }
            }

        }
        )
    }

    private  fun guardar() {

        if (txt_DescripcionDepartamento.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese la descripcion del Departamento", Toast.LENGTH_SHORT).show()
        }else {
            if (txt_NombreDepartamento.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese el Nombre del Departamento", Toast.LENGTH_SHORT).show()
            } else{
                callServicePostDepartamento()
                Toast.makeText(this, "Realizada con exito!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun Mostrar() {
        val intent = Intent(this, MostrarDepartamento::class.java)
        startActivity(intent)}

    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

}