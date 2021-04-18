package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.PagoDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentasDataCollectionItem
import kotlinx.android.synthetic.main.activity_compras.*
import kotlinx.android.synthetic.main.activity_departamento.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_ventas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Ventas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventas)
        btn_regresarVentas.setOnClickListener { Regresar()}

        findViewById<FloatingActionButton>(R.id.idFabListar_Ventas).setOnClickListener {
            Mostrar() }
        txt_FechaVenta.setOnClickListener{showDatePickerDialog()}
    }

    fun showDatePickerDialog() {
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected( day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDateSelected(day: Int, month: Int, year: Int) {
        txt_FechaVenta.setText("$day / $month / $year")
        txt_FechaEntrga2.setText("$day / $month / $year")
    }

    private fun callServicePostVenta() {
        val ventainfo = VentasDataCollectionItem(id = null ,
                descripcion = txt_DescripcionVenta.text.toString(),
                idempleado = spinnerIdEmpleado2.selectedItem.toString().toLong(),
                cai = txt_CaiVenta.text.toString().toLong(),
                idcliente = spinnercCL.selectedItem.toString().toLong(),
                numerotarjeta = txt_NoTarjetaVenta.text.toString().toLong(),
                formadepago = spinnerFormaPago2.selectedItem.toString().toLong(),
                fechaventa = txt_FechaVenta.text.toString(),
                fechaentrega  = txt_FechaVenta.text.toString()


        )
        addVentas(ventainfo) {
            if (it?.id != null) {
                Toast.makeText(this@Ventas, "OK" + it?.id, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@Ventas,  "Error", Toast.LENGTH_LONG).show()
            }
        }
    }
    }



    fun  addVentas(ventasData: VentasDataCollectionItem, onResult: (VentasDataCollectionItem?) -> Unit){
    val retrofit = RestEngine.buildService().create(VentasService::class.java)
    var result: Call<VentasDataCollectionItem> = retrofit.addVentas(ventasData)

    result.enqueue(object : Callback<VentasDataCollectionItem> {
        override fun onFailure(call: Call<VentasDataCollectionItem>, t: Throwable) {
            onResult(null)
        }

        override fun onResponse(call: Call<VentasDataCollectionItem>,
                                response: Response<VentasDataCollectionItem>) {
            if (response.isSuccessful) {
                val addedPerson = response.body()!!
                onResult(addedPerson)
            } else if (response.code() == 401) {
                Toast.makeText(this@Ventas, "Sesion expirada", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(  this@Ventas, "Fallo al traer el item", Toast.LENGTH_LONG).show()
            }
        }


    }
    )
}
    private fun callServiceGetTipo() {
    var lista: HashSet<String> = hashSetOf()

    val tipoService:PagoService = RestEngine.buildService().create(PagoService::class.java)
    var result: Call<List<PagoDataCollectionItem>> = tipoService.listPagos()

    result.enqueue(object : Callback<List<PagoDataCollectionItem>> {
        override fun onFailure(call: Call<List<PagoDataCollectionItem>>, t: Throwable) {
            Toast.makeText(this@Ventas, "Error", Toast.LENGTH_LONG).show()
        }

        override fun onResponse(
                call: Call<List<PagoDataCollectionItem>>,
                response: Response<List<PagoDataCollectionItem>>
        ) {
            try {
                for (i in response.body()!!) {
                    lista.add(i.id.toString())
                }

                iniciar(lista)

            } catch (e: Exception) {
                println("No hay datos de tipo de pago")

            }

        }
    })

}

fun iniciar(a:HashSet<String>){
    val spinner_Puestos = findViewById<Spinner>(R.id.spinnerFormaPago)
    var valor:String
    println("THIS IS"+a.toString())
    var A:ArrayList<String> = ArrayList()
    for(i in a){
        val data = i.toString().split("|")
        valor=data[0].toString()
        A.add(valor)

    }

    println("ESTO ES LO QUE SE VA A GUARDAR" + A.toString())

    val adaptador = ArrayAdapter(this,android.R.layout.simple_spinner_item,A)

    spinner_Puestos.adapter =adaptador
    spinner_Puestos.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener { override fun onNothingSelected(parent: AdapterView<*>?) {
    }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
        {
            callServiceGetTipo()(A[position].toString().toLong())


        }
    }

}
private fun callServiceGetTipoById(a:Long) {
    var nombrep=""
    val pagoservice:PagoService = RestEngine.buildService().create(PagoService::class.java)
    var result: Call<PagoDataCollectionItem> = pagoservice.getPagoById(a)

    result.enqueue(object : Callback<PagoDataCollectionItem> {
        override fun onFailure(call: Call<PagoDataCollectionItem>, t: Throwable) {
            Toast.makeText(this@Ventas,"Error",Toast.LENGTH_LONG).show()
        }

        override fun onResponse(
                call: Call<PagoDataCollectionItem>,
                response: Response<PagoDataCollectionItem>
        ) {
            nombrep = response.body()!!.descripcion.toString()
            txv_SeleccionT2.text.toString = nombrep

        }
    })


}





/* private  fun guardar() {

        if (txt_VentaId.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID de Venta", Toast.LENGTH_SHORT).show()
        } else {
            if (txt_IDProductoV.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese ID de Producto", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_IDEmpleadoV.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese ID de Empleado", Toast.LENGTH_SHORT).show()
                } else {
                    if (txt_CaiVenta.text.toString().isEmpty()) {
                        Toast.makeText(this, "Ingrese ID de Cliente", Toast.LENGTH_SHORT).show()
                        if (txt_DescripcionVenta.text.toString().isEmpty()) {
                            Toast.makeText(this, "Ingrese Nombre de Cliente", Toast.LENGTH_SHORT).show()
                        } else {
                            if (txt_NoTarjetaVenta.text.toString().isEmpty()) {
                                Toast.makeText(this, "Ingrese Una descripcion",Toast.LENGTH_SHORT).show()
                            } else {
                                if (txt_CantidadV.text.toString().isEmpty()) {
                                    Toast.makeText(this, "Ingrese una Cantidad", Toast.LENGTH_SHORT).show()
                                } else {
                                    if (txt_PrecioV.text.toString().isEmpty()) {
                                        Toast.makeText(this, "Ingrese un Precio", Toast.LENGTH_SHORT).show()
                                    } else {
                                        if (txt_SubtotalV.text.toString().isEmpty()) {
                                            Toast.makeText(this, "Ingrese un Subtotal", Toast.LENGTH_SHORT).show()
                                            if (txt_FormaPagoVenta.text.toString().isEmpty()) {
                                                Toast.makeText(this, "Ingrese ISV", Toast.LENGTH_SHORT).show()
                                            }else{
                                                if(txt_TotaVenta.text.toString().isEmpty()){
                                                    Toast.makeText(this, "Ingrese EL Total a Pagar", Toast.LENGTH_SHORT).show()
                                                }
                                            Toast.makeText(this, "Realizada con exito!", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }
                            }
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

    private fun Mostrar() {
        val intent = Intent(this, MostrarVentas::class.java)
        startActivity(intent)
    }
}
}