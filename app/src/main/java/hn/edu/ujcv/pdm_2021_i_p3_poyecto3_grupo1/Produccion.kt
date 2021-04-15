package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_produccion.*

class Produccion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produccion)

    }

    private  fun guardar() {

        if (txt_IdProduccion.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID produccion", Toast.LENGTH_SHORT).show()
        } else {
            if (txt_ProductoId.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese ID de Producto", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_IdEmpleadoP.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese un Empleado", Toast.LENGTH_SHORT).show()
                } else {
                    if (txt_DescripcionProduccion.text.toString().isEmpty()) {
                        Toast.makeText(this, "Ingrese la Descripcion", Toast.LENGTH_SHORT).show()
                    } else {
                        if (txt_TiempoProduccion.text.toString().isEmpty()) {
                            Toast.makeText(this, "Ingrese el Tiempo", Toast.LENGTH_SHORT).show()
                        } else {
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
