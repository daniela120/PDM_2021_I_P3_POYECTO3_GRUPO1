package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_cliente.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_produccion.*

class Produccion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produccion)
        btn_regresarProduccion.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabListar_Produccion).setOnClickListener {
            Mostrar() }
    }

    private  fun guardar() {



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



        private fun Regresar() {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
    private fun Mostrar() {
        val intent = Intent(this, MostrarProduccion::class.java)
        startActivity(intent)
    }

    }
