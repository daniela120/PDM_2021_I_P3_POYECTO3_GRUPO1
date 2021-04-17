package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_cliente.*

import kotlinx.android.synthetic.main.activity_departamento.*

class Departamento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamento)
        btn_regresarDepartamento.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabListar_Departamento).setOnClickListener {
            Mostrar() }

    }

    /*private  fun guardar() {

        if (txt_DepartamentoID.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID del Departamento", Toast.LENGTH_SHORT).show()
        }else {
            if (txtNombreDepartamento.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un Nombre", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_DescripcionDepartamento.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese una descripcion", Toast.LENGTH_SHORT).show()
                } else {
                    if (txt_EncargadoD.text.toString().isEmpty()) {
                        Toast.makeText(this, "Ingrese un encargado", Toast.LENGTH_SHORT).show()
                    }else{
                        if(txt_IdEmpleadoD.text.toString().isEmpty()){
                            Toast.makeText(this, "Ingrese ID del Empleado", Toast.LENGTH_SHORT).show()
                        } else{
                                Toast.makeText(this, "Realizada con exito!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }*/



    private fun Mostrar() {
        val intent = Intent(this, MostrarDepartamento::class.java)
        startActivity(intent)}

    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

}