package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ClienteDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*

import kotlinx.android.synthetic.main.activity_compras.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date

class Compras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compras)
        btn_regresarCompras.setOnClickListener { Regresar() }

        findViewById<FloatingActionButton>(R.id.idFabListar_Compras).setOnClickListener {
            Mostrar() }


        findViewById<FloatingActionButton>(R.id.idFabConfirmar_Compras).setOnClickListener {
            callServicePostCompra() }

    }



    private fun callServicePostCompra() {


        val compraInfo = ComprasDataCollectionItem(id = null,
                cai = txt_CaiCompra.text.toString(),
                proveedores = txt_TelefonoCliente.text.toString().toLong(),
                numerotarjeta = txt_dniCliente.text.toString().toLong(),
                formapago = txt_FormaPagoCompra.text.toString().toLong(),
                fechaentrega = null,
                fechacompra =null ,
                insumos = 1
        )

        addCompra(compraInfo) {
            if (it?.id != null) {
                Toast.makeText(this@Compras,"COMPRA GUARDADA EXITOSAMENTE",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@Compras,"Error",Toast.LENGTH_LONG).show()
            }
        }
    }


    fun addCompra(compraData: ComprasDataCollectionItem, onResult: (ComprasDataCollectionItem?) -> Unit){
        val retrofit = RestEngine.buildService().create(ComprasService::class.java)
        var result: Call<ComprasDataCollectionItem> = retrofit.addCompras(compraData)

        result.enqueue(object : Callback<ComprasDataCollectionItem> {
            override fun onFailure(call: Call<ComprasDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(call: Call<ComprasDataCollectionItem>,
                                    response: Response<ComprasDataCollectionItem>) {
                if (response.isSuccessful) {
                    val addedPerson = response.body()!!
                    onResult(addedPerson)

                }
                else if (response.code() == 401){
                    Toast.makeText(this@Compras,"Sesion expirada",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@Compras,"Fallo al traer el item",Toast.LENGTH_LONG).show()
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
        val intent = Intent(this, MostrarCompras::class.java)
        startActivity(intent)
    }
}