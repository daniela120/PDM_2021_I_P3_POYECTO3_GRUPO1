package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_listar_detalle_venta.*

class ListarDetalleVenta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_detalle_venta)
        setSupportActionBar(findViewById(R.id.toolbar))
        btn_regresarListarDVenta.setOnClickListener { Regresar() }

        /*findViewById<FloatingActionButton>().setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }
    private fun Regresar() {
        val intent = Intent(this, DetalleVenta::class.java)
        startActivity(intent)
    }
}