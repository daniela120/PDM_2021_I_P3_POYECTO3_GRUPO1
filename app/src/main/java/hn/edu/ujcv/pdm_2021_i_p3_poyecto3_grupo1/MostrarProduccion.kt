package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.DepartamentoDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProduccionDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_departamento.*
import kotlinx.android.synthetic.main.activity_mostrar_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_compras.*
import kotlinx.android.synthetic.main.activity_mostrar_departamento.*
import kotlinx.android.synthetic.main.activity_mostrar_produccion.*
import kotlinx.android.synthetic.main.activity_produccion.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MostrarProduccion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_produccion)
        btn_regresarProduccion2.setOnClickListener { Regresar() }
        val botonGetId = findViewById<Button>(R.id.btn_BuscarProduccion)

        findViewById<FloatingActionButton>(R.id.idFabEliminar_Produccion).setOnClickListener {
            callServiceDeleteProduccion()
        }
        findViewById<FloatingActionButton>(R.id.idFabLimpiar_Produccion).setOnClickListener {
            resetear()
        }
        findViewById<FloatingActionButton>(R.id.idFabActualizar_Produccion).setOnClickListener {

            callServicePutProduccion()
        }
        botonGetId.setOnClickListener { v -> callServiceGetPerson() }
    }

    private fun resetear() {
        txt_MostrarDescripcion.setText("")
        txt_MostrarPrdTiempo.setText("")
        txt_MostrarDescripcion.isEnabled = false
        txt_MostrarPrdTiempo.isEnabled = false

    }

    private fun Regresar() {
        val intent = Intent(this, Produccion::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val produccionService: ProduccionService = RestEngine.buildService().create(ProduccionService::class.java)
        var result: Call<ProduccionDataCollectionItem> = produccionService.getProduccionById(1)

        result.enqueue(object : Callback<ProduccionDataCollectionItem> {
            override fun onFailure(call: Call<ProduccionDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarProduccion, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProduccionDataCollectionItem>,
                    response: Response<ProduccionDataCollectionItem>
            ) = try {


                var a = response.body()!!.idproducto
                var b = response.body()!!.idempleado
                var c = response.body()!!.iddepto
                var d = response.body()!!.descripcion
                var e = response.body()!!.tiempo
                ver()
                txv_selectProd1.setText(a.toString())
                txv_selectProd2.setText(b.toString())
                txv_selectProd3.setText(c.toString())
                txt_MostrarDescripcion.setText(d)
                txt_MostrarPrdTiempo.setText(e)

            } catch (e: Exception) {
                Toast.makeText(this@MostrarProduccion, "No existe la informacion con el id: " + txt_IdProduccion2.text.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        txt_MostrarDescripcion.isEnabled
        txt_MostrarPrdTiempo.isEnabled
    }

    fun ver() {
        txt_MostrarDescripcion.isEnabled = true
        txt_MostrarPrdTiempo.isEnabled = true
        txv_selectProd1.isEnabled = true
        txv_selectProd2.isEnabled = true
        txv_selectProd3.isEnabled = true
    }


    private fun callServiceDeleteProduccion() {
        try {


            val produccionService: ProduccionService = RestEngine.buildService().create(ProduccionService::class.java)
            var result: Call<ResponseBody> = produccionService.deleteProduccion(txt_IdProduccion2.text.toString().toLong())

            result.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MostrarProduccion, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MostrarProduccion, "ELIMINADO CON EXITO", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@MostrarProduccion, "Sesion expirada", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MostrarProduccion, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                    }
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@MostrarProduccion, "NO SE PUEDO ELIMINAR LA INFORMACION CON EL ID: " + txt_IdCliente2.text.toString(), Toast.LENGTH_LONG).show()

        }
    }

    private fun callServicePutProduccion() {
        try {
            val Info = ProduccionDataCollectionItem(id = txt_IdProduccion2.text.toString().toLong(),
                    idproducto = spinnerIdProducto.selectedItem.toString().toLong(),
                    idempleado = spinnerIdEmpleado.selectedItem.toString().toLong(),
                    iddepto = spinnerdepto.selectedItem.toString().toLong(),
                    descripcion = txt_DescripcionProduccion.text.toString(),
                    tiempo = txt_TiempoProduccion.text.toString()
            )


            val retrofit = RestEngine.buildService().create(ProduccionService::class.java)
            var result: Call<ProduccionDataCollectionItem> = retrofit.updateProduccion(Info)

            result.enqueue(object : Callback<ProduccionDataCollectionItem> {
                override fun onFailure(call: Call<ProduccionDataCollectionItem>, t: Throwable) {
                    Toast.makeText(this@MostrarProduccion, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<ProduccionDataCollectionItem>,
                                        response: Response<ProduccionDataCollectionItem>) {
                    if (response.isSuccessful) {
                        val updatedProduccion = response.body()!!
                        Toast.makeText(this@MostrarProduccion, "DEPT ACTUALIZADO", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@MostrarProduccion, "Sesion expirada", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MostrarProduccion, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                    }
                }

            })
        } catch (e: Exception) {
            Toast.makeText(this@MostrarProduccion, "NO SE PUEDO ACTUALIZAR LA INFORMACION CON EL ID: " + txt_IdProduccion2.text.toString(), Toast.LENGTH_LONG).show()

        }
    }
}

