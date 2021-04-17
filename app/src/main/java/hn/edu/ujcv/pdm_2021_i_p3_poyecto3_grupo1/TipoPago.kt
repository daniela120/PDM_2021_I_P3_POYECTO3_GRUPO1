package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_tipo_pago.*
import kotlinx.android.synthetic.main.activity_departamento.*
import com.getbase.floatingactionbutton.FloatingActionButton

class TipoPago : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipo_pago)
        btn_regresarPago.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabListarPago).setOnClickListener {
            Mostrar() }
    }

    /*private  fun guardar() {

        if (txtI_CostoID.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID del costo", Toast.LENGTH_SHORT).show()
        }else {
            if (txt_IdCompraC.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un Nombre", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_ManoObra.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese mano de obra", Toast.LENGTH_SHORT).show()
                } else {
                    if (txt_CantidadC.text.toString().isEmpty()) {
                        Toast.makeText(this, "Ingrese una cantidad", Toast.LENGTH_SHORT).show()
                        if(txt_PrecioC.text.toString().isEmpty()){
                            Toast.makeText(this, "Ingrese un precio", Toast.LENGTH_SHORT).show()
                        } else {
                            if (txt_TotalC.text.toString().isEmpty()) {
                                Toast.makeText(this, "Inreges el total", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this, "Realizada con exito!", Toast.LENGTH_SHORT).show()
                            }
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
        val intent = Intent(this, MostrarTipoPago::class.java)
        startActivity(intent)
    }


}