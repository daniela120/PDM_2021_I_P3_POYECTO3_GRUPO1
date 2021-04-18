package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tipo_pago.*
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.PagoDataCollectionItem
import kotlinx.android.synthetic.main.activity_mostrar_tipo_pago.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TipoPago : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipo_pago)
        btn_regresarPago.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabConfirmarago).setOnClickListener {
            callServicePostTipoPago() }

        findViewById<FloatingActionButton>(R.id.idFabListarPago).setOnClickListener {
            Mostrar()
        }
    }



    private fun callServicePostTipoPago() {

        val tipopagoInfo = PagoDataCollectionItem(id = null,
                descripcion = txt_DescripcionPago.text.toString(),
                estado = txt_EstadoPago.text.toString()
        )



        addTipoPago(tipopagoInfo) {
            if (it?.id != null) {
                Toast.makeText(this@TipoPago, "TIPO DE PAGO AGREGADO", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@TipoPago, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun addTipoPago(Data: PagoDataCollectionItem, onResult: (PagoDataCollectionItem?) -> Unit) {
        val retrofit = RestEngine.buildService().create(PagoService::class.java)
        var result: Call<PagoDataCollectionItem> = retrofit.addPago(Data)

        result.enqueue(object : Callback<PagoDataCollectionItem> {
            override fun onFailure(call: Call<PagoDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(call: Call<PagoDataCollectionItem>,
                                    response: Response<PagoDataCollectionItem>) {
                if (response.isSuccessful) {
                    val addedPago= response.body()!!
                    onResult(addedPago)
                } else if (response.code() == 401) {
                    Toast.makeText(this@TipoPago, "Sesion expirada", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@TipoPago, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                }
            }

        }
        )
    }


    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

    private fun Mostrar() {
        val intent = Intent(this, MostrarTipoPago::class.java)
        startActivity(intent)
    }


}