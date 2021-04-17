package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_cliente.*

class Cliente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)
        btn_regresarCliente.setOnClickListener { Regresar() }

        findViewById<FloatingActionButton>(R.id.idFabActualizar_Cli).setOnClickListener {
            Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabEliminar_Cli).setOnClickListener { view ->
            Snackbar.make(view, "registro eliminado", Snackbar.LENGTH_LONG) }

    }

    /*private  fun guardar() {

        if (txt_IdCliente.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID de la Cliente", Toast.LENGTH_SHORT).show()
        }else {
            if (txt_NombreCliente.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un Nombre", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_DireccionCliente.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese el Material", Toast.LENGTH_SHORT).show()
                } else {
                    if (txt_ColorTela.text.toString().isEmpty()) {
                        Toast.makeText(this, "Ingrese una cantidad", Toast.LENGTH_SHORT).show()
                    } else {
                        if (txt_dniCliente.text.toString().isEmpty()) {
                            Toast.makeText(this, "Inreges el tipo de compra", Toast.LENGTH_SHORT).show()
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
}