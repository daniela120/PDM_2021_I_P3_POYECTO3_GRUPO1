package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_departamento.*
import kotlinx.android.synthetic.main.activity_mostrar_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_detalle_ventas.*
import kotlinx.android.synthetic.main.activity_mostrar_produccion.*
import kotlinx.android.synthetic.main.activity_produccion.*
import okhttp3.ResponseBody
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentaDetalleDataCollectionItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MostrarDetalleVenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_detalle_ventas)

        val botonGetId = findViewById<Button>(R.id.btn_BuscarFactura)

    }

}