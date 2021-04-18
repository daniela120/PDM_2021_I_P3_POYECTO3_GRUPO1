package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProveedoresDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_proveedores.*
import okhttp3.ResponseBody

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MostrarProveedores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_proveedores)
        btn_regresarProveedor2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarProveedor)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}

        findViewById<FloatingActionButton>(R.id.idFabActualizar_Proveedores).setOnClickListener {
            callServicePutProveedores()
        }

        findViewById<FloatingActionButton>(R.id.idFabEliminar_Proveedores).setOnClickListener {
            callServiceDeleteProveedores()
        }

        findViewById<FloatingActionButton>(R.id.idFabLimpiar_Proveedores).setOnClickListener {
            resetearall()
        }
    }

    private fun Regresar() {
        val intent = Intent(this, Proveedores::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val ProveedoresService: ProveedoresService = RestEngine.buildService().create(ProveedoresService::class.java)
        var result: Call<ProveedoresDataCollectionItem> = ProveedoresService.getProveedoresById(txt_IdProveedor2.text.toString().toLong())

        result.enqueue(object : Callback<ProveedoresDataCollectionItem> {
            override fun onFailure(call: Call<ProveedoresDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarProveedores, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProveedoresDataCollectionItem>,
                    response: Response<ProveedoresDataCollectionItem>
            ) = try {
                ver()

                var a = response.body()!!.nombre
                var b = response.body()!!.direccion
                var c = response.body()!!.rtn
                var d = response.body()!!.compañia


                txt_MotrarPrvNombre.setText(a)
                txt_MotrarPrvDireccion.setText(b)
                txt_MotrarPrvRTN.setText(c)
                txt_MotrarPrvCompañia.setText(d)
                

            } catch (e: Exception) {

                Toast.makeText(this@MostrarProveedores, "No existe la informacion con el id: " +  txt_IdProveedor2.text.toString(), Toast.LENGTH_SHORT).show()
                resetearall()
            }

        })
        txt_MotrarPrvNombre.isEnabled
        txt_MotrarPrvDireccion.isEnabled
        txt_MotrarPrvRTN.isEnabled
        txt_MotrarPrvCompañia.isEnabled
    }
    fun ver() {
        txt_MotrarPrvNombre.isEnabled = true
        txt_MotrarPrvDireccion.isEnabled = true
        txt_MotrarPrvRTN.isEnabled = true
        txt_MotrarPrvCompañia.isEnabled = true
        
    }
    private fun callServiceDeleteProveedores() {
        try {


            val ProveedoresService: ProveedoresService = RestEngine.buildService().create(ProveedoresService::class.java)
            var result: Call<ResponseBody> = ProveedoresService.deleteProveedores(txt_IdProveedor2.text.toString().toLong())

            result.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MostrarProveedores, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        resetearall()
                        Toast.makeText(this@MostrarProveedores, "ELIMINADO CON EXITO", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@MostrarProveedores, "Sesion expirada", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MostrarProveedores, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                    }
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@MostrarProveedores, "NO SE PUEDO ELIMINAR LA INFORMACION CON EL ID: " + txt_IdProveedor2.toString(), Toast.LENGTH_LONG).show()

        }
    }

    private fun callServicePutProveedores() {
        try{

            val Info = ProveedoresDataCollectionItem(id = txt_IdProveedor2.text.toString().toLong(),
                    nombre = txt_MotrarPrvNombre.text.toString(),
                    direccion = txt_MotrarPrvDireccion.text.toString(),
                    rtn = txt_MotrarPrvRTN.text.toString(),
                    compañia = txt_MotrarPrvCompañia.text.toString()
            )


            val retrofit = RestEngine.buildService().create(ProveedoresService::class.java)
            var result: Call<ProveedoresDataCollectionItem> = retrofit.updateProveedores(Info)

            result.enqueue(object : Callback<ProveedoresDataCollectionItem> {
                override fun onFailure(call: Call<ProveedoresDataCollectionItem>, t: Throwable) {
                    Toast.makeText(this@MostrarProveedores, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<ProveedoresDataCollectionItem>,
                                        response: Response<ProveedoresDataCollectionItem>) {
                    if (response.isSuccessful) {
                        val updatedProveedores = response.body()!!
                        Toast.makeText(this@MostrarProveedores, " ACTUALIZADO", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@MostrarProveedores, "Sesion expirada", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MostrarProveedores, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                    }
                }

            })
        } catch (e: Exception) {
            Toast.makeText(this@MostrarProveedores, e.message+" NO SE PUEDO ACTUALIZAR LA INFORMACION CON EL ID: " + txt_IdProveedor2.text.toString(), Toast.LENGTH_LONG).show()

        }
    }
    private fun resetearall() {
        txt_IdProveedor2.setText("")
        txt_MotrarPrvNombre.setText("")
        txt_MotrarPrvDireccion.setText("")
        txt_MotrarPrvRTN.setText("")
        txt_MotrarPrvCompañia.setText("")
        txt_MotrarPrvNombre.isEnabled = false
        txt_MotrarPrvDireccion.isEnabled = false
        txt_MotrarPrvRTN.isEnabled = false
        txt_MotrarPrvCompañia.isEnabled = false


    }
}