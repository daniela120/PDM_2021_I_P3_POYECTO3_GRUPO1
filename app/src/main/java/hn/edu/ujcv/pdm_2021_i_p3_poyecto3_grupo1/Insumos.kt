package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_insumos.*
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.InsumosDataCollectionItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Insumos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insumos)
        btn_regresarInsumos.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabConfirmar_Produccion).setOnClickListener {
            guardar() }

        findViewById<FloatingActionButton>(R.id.idFabListar_Insumos).setOnClickListener {
            Mostrar()
        }
        findViewById<FloatingActionButton>(R.id.idListarIN).setOnClickListener {
            ir()
        }


    }

    private fun ir() {
        val intent = Intent(this, ListarInsumos::class.java)
        startActivity(intent)
    }

    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }
    private fun Mostrar() {
        val intent = Intent(this, MostrarInsumos::class.java)
        startActivity(intent)
    }

    private fun callServicePostInsumo() {
        val insumosinf = InsumosDataCollectionItem(id = null,
                nombre = txt_NombreInsumo.text.toString(),
                tipo = txt_Tipo.text.toString(),
                cantidad = txt_TipoCantidad.text.toString().toLong() ,
                 preciocompra = txt_PrecioCon.text.toString().toLong() ,
                precioventa = txt_PrecioVenta.text.toString().toLong())

        addInsumos(insumosinf) {
            if (it?.id != null) {
                android.widget.Toast.makeText(this@Insumos,"INSUMO AGREGADO", android.widget.Toast.LENGTH_LONG).show()
            } else {
                android.widget.Toast.makeText(this@Insumos,"Error", android.widget.Toast.LENGTH_LONG).show()
            }
        }
    }




    fun addInsumos(insumosData: InsumosDataCollectionItem, onResult: (InsumosDataCollectionItem?) -> Unit){
        val retrofit = RestEngine.buildService().create(InsumosService::class.java)
        var result: Call<InsumosDataCollectionItem> = retrofit.addInsumos(insumosData)

        result.enqueue(object : Callback<InsumosDataCollectionItem> {
            override fun onFailure(call: Call<InsumosDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(call: Call<InsumosDataCollectionItem>,
                                    response: Response<InsumosDataCollectionItem>) {
                if (response.isSuccessful) {
                    val addedPerson = response.body()!!
                    onResult(addedPerson)
                }
                else if (response.code() == 401){
                    Toast.makeText(this@Insumos,"Sesion expirada",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@Insumos,"Fallo al traer el item",Toast.LENGTH_LONG).show()
                }
            }

        }
        )
    }



    private  fun guardar() {

            if (txt_NombreInsumo.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un Nombre de Insumo", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_Tipo.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese una Tipo de Compra", Toast.LENGTH_SHORT).show()
                        } else{
                            callServicePostInsumo()
                    Toast.makeText(this, "DATO REGISTRADO!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


