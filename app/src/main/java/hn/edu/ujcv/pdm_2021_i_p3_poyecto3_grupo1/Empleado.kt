package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_empleado.*
import kotlinx.android.synthetic.main.activity_productos.*

class Empleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado)
        btn_RegresarE.setOnClickListener { Regresar() }
        btn_GuardarE.setOnClickListener { guardar() }
    }

    private  fun guardar() {

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
    }
}