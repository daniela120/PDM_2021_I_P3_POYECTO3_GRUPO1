package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.PagoDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProveedoresDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*

import kotlinx.android.synthetic.main.activity_compras.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.time.Month
import java.time.MonthDay
import java.time.Year

class Compras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compras)
        btn_regresarCompras.setOnClickListener { Regresar() }

        findViewById<FloatingActionButton>(R.id.idFabListar_Compras).setOnClickListener {
            Mostrar() }
        txt_FechaCompra.setOnClickListener{showDatePickerDialog()}
        initial()
    }

    fun showDatePickerDialog() {
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected( day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDateSelected(day: Int, month: Int, year: Int) {
        txt_FechaCompra.setText("$day / $month / $year")
    }

    private fun callServicePostCompra() {

        val compraInfo = ComprasDataCollectionItem(id = null,
                cai = txt_CaiCompra.text.toString(),
                proveedores = txt_TelefonoCliente.text.toString().toLong(),
                numerotarjeta = txt_dniCliente.text.toString().toLong(),
                formapago = spinnerFormaPago.selectedItem.toString().toLong(),
                fechaentrega = null,
                fechacompra = null,
                insumos = null
        )

            addCompra(compraInfo) {
                if (it?.id != null) {
                    Toast.makeText(this@Compras,"OK"+it?.id,Toast.LENGTH_LONG).show()
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



    private fun callServiceGetTipo():List<String> {
        var A:ArrayList<String> = ArrayList()
        val pagoService:PagoService = RestEngine.buildService().create(PagoService::class.java)
        var result: Call<List<PagoDataCollectionItem>> = pagoService.listPagos()

        result.enqueue(object :  Callback<List<PagoDataCollectionItem>> {
            override fun onFailure(call: Call<List<PagoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@Compras,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<PagoDataCollectionItem>>,
                    response: Response<List<PagoDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!){
                        A.add(i.id.toString())

                    }
                    Toast.makeText(this@Compras,"OK"+response.body()!!.get(0).descripcion,Toast.LENGTH_LONG).show()

                }catch (e:Exception){

                    println("No hay datos de tipo de pago")

                }

            }
        })
        return A
    }

    private fun callServiceGetProveedores():List<String> {
        var A:ArrayList<String> = ArrayList()
        val proveedoresService:ProveedoresService = RestEngine.buildService().create(ProveedoresService::class.java)
        var result: Call<List<ProveedoresDataCollectionItem>> = proveedoresService.listProveedores()

        result.enqueue(object :  Callback<List<ProveedoresDataCollectionItem>> {
            override fun onFailure(call: Call<List<ProveedoresDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@Compras,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<ProveedoresDataCollectionItem>>,
                    response: Response<List<ProveedoresDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!){
                        A.add(i.id.toString())

                    }
                    Toast.makeText(this@Compras,"OK"+response.body()!!.get(0).compañia,Toast.LENGTH_LONG).show()

                }catch (e:Exception){
                    println("No hay datos de tipo de pago")

                }

            }
        })
        return A
    }

    fun initial(){
        var A = callServiceGetTipo()
        val adaptador = ArrayAdapter(this,android.R.layout.simple_spinner_item, A)
        spinnerFormaPago.adapter =adaptador
        spinnerFormaPago.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                println(A[position].toString())

            }

        }


        var B = callServiceGetProveedores()
        val adaptador1 = ArrayAdapter(this,android.R.layout.simple_spinner_item, B)
        spinnerIdProvedor.adapter =adaptador1
        spinnerIdProvedor.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                println(B[position].toString())

            }

        }

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