package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProveedoresDataCollectionItem
import kotlinx.android.synthetic.main.activity_insumos.*
import kotlinx.android.synthetic.main.activity_productos.*
import kotlinx.android.synthetic.main.activity_proveedores.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Proveedores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedores)
        btn_regresarProveedor.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabConfirmar_Proveedores).setOnClickListener {
            guardar()
        }
        findViewById<FloatingActionButton>(R.id.idFabListar_Proveedores).setOnClickListener {
           Mostrar()
        }
    }

    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

    private fun Mostrar() {
        val intent = Intent(this, MostrarProveedores::class.java)
        startActivity(intent)
    }

    private fun callServicePostProveedores() {
        try {


            val proveedoresInfo = ProveedoresDataCollectionItem(id = null,
                    nombre = txt_NombreProveedor.text.toString(),
                    compañia = txt_CompaProveedor.text.toString(),
                    rtn = txt_RtnProveedor.text.toString(),
                    direccion = txt_DireccionProveedor.text.toString()
            )



            addProveedores(proveedoresInfo) {
                if (it?.id != null) {
                    android.widget.Toast.makeText(this@Proveedores, "PROVEEDOR AGREGADO EXITOSAMENTE", android.widget.Toast.LENGTH_LONG).show()
                } else {
                    android.widget.Toast.makeText(this@Proveedores, "Error", android.widget.Toast.LENGTH_LONG).show()
                }
            }
        }catch (e:Exception){
            Toast.makeText(this, e.message+" POR FAVOR VERIFIQUE LOS DATOS", Toast.LENGTH_SHORT).show()
        }
    }

    fun addProveedores(proveedoresData: ProveedoresDataCollectionItem, onResult: (ProveedoresDataCollectionItem?) -> Unit) {
        val retrofit = RestEngine.buildService().create(ProveedoresService::class.java)
        var result: Call<ProveedoresDataCollectionItem> = retrofit.addProveedores(proveedoresData)

        result.enqueue(object : Callback<ProveedoresDataCollectionItem> {
            override fun onFailure(call: Call<ProveedoresDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(call: Call<ProveedoresDataCollectionItem>,
                                    response: Response<ProveedoresDataCollectionItem>) {
                if (response.isSuccessful) {
                    val addedPerson = response.body()!!
                    onResult(addedPerson)
                } else if (response.code() == 401) {
                    Toast.makeText(this@Proveedores, "Sesion expirada", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@Proveedores, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                }
            }

        }
        )
    }


    private fun guardar() {
        if (txt_NombreProveedor.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese un Nombre de un Proveedor", Toast.LENGTH_SHORT).show()
        } else {
            if (txt_CompaProveedor.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese la Compañia", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_RtnProveedor.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese el RTN", Toast.LENGTH_SHORT).show()
                } else {
                    if (txt_DireccionProveedor.text.toString().isEmpty()) {
                        Toast.makeText(this, "Ingrese la direccion", Toast.LENGTH_SHORT).show()
                    } else {
                        callServicePostProveedores()
                        reset()
                    }
                }
            }
        }
    }


    fun reset(){
        txt_NombreProveedor.setText("")
        txt_CompaProveedor.setText("")
        txt_RtnProveedor.setText("")
        txt_DireccionProveedor.setText("")
    }
}


