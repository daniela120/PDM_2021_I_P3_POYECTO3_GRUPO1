package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.CompraDetalleDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem

import kotlinx.android.synthetic.main.activity_mostrar_detalle_compra.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception

class MostrarDetalleCompra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_detalle_compra)
        btn_regresarMDetalleCompra.setOnClickListener { Regresar() }
        val botonGetId = findViewById<Button>(R.id.btn_BuscarDetalleCompra)
        findViewById<FloatingActionButton>(R.id.idFabActualizar_DetalleCompra).setOnClickListener{
           }
        findViewById<FloatingActionButton>(R.id.idFabLimpiar_DetalleCompra).setOnClickListener{
            reset() }
        findViewById<FloatingActionButton>(R.id.idFabEliminar_DetalleCompra).setOnClickListener{
            }
        botonGetId.setOnClickListener { v -> }

    }





    fun reset(){
        txt_DCCantidad2.setText("")
        txt_DCPrecio2.setText("")
        txt_DCTotal2.setText("")
        txt_DCCantidad2.isEnabled = false
        txt_DCPrecio2.isEnabled = false
        txt_DCTotal2.isEnabled = false

    }

    private fun Regresar() {
        val intent = Intent(this, DetalleCompra::class.java)
        startActivity(intent)
    }
}