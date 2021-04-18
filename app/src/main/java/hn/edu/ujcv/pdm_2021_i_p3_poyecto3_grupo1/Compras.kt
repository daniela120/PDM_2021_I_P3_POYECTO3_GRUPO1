package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.*
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_compras.*
import kotlinx.android.synthetic.main.activity_compras.btn_regresarCompras
import kotlinx.android.synthetic.main.activity_mostrar_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_compras.*
import kotlinx.android.synthetic.main.activity_mostrar_empleado.*
import kotlinx.android.synthetic.main.activity_ventas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class Compras : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compras)
        btn_regresarCompras.setOnClickListener { Regresar() }

        findViewById<FloatingActionButton>(R.id.idFabListar_Compras).setOnClickListener {
            Mostrar() }
        txt_FechaCompra.setOnClickListener{showDatePickerDialog()}
        txt_FechaE.setOnClickListener{showDatePickerDialog1()}
        findViewById<FloatingActionButton>(R.id.idFabConfirmar_Compras).setOnClickListener {
            callServicePostCompra() }
        callServiceGetProveedores()
        callServiceGetTipo()
        callServiceGetInsumo()
    }

    fun showDatePickerDialog() {
        val datePicker = DatePickerFragment{ day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDateSelected(day: Int, month: Int, year: Int) {
        txt_FechaCompra.setText("$day / $month / $year")

    }


    fun showDatePickerDialog1() {
        val datePicker = DatePickerFragment{ day, month, year -> onDateSelected1(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDateSelected1(day: Int, month: Int, year: Int) {
        txt_FechaE.setText("$day / $month / $year")
    }

    private fun callServicePostCompra() {

        val compraInfo = ComprasDataCollectionItem(id = null,
                cai = txt_CaiCompra.text.toString(),
                proveedores = spinnerIdProvedor.selectedItem.toString().toLong(),
                numerotarjeta = txt_TarjetaCompra.text.toString().toLong(),
                formapago = spinnerFormaPago.selectedItem.toString().toLong(),
                fechaentrega = txt_FechaE.text.toString(),
                fechacompra = txt_FechaCompra.text.toString(),
                insumos = spinnerInsumo.selectedItem.toString().toLong(),
        )

            addCompra(compraInfo) {
                if (it?.id != null) {
                    Toast.makeText(this@Compras, "OK" + it?.id, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@Compras, "Error", Toast.LENGTH_LONG).show()
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
                } else if (response.code() == 401) {
                    Toast.makeText(this@Compras, "Sesion expirada", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@Compras, "Fallo al traer el item", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this@Compras, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<PagoDataCollectionItem>>,
                    response: Response<List<PagoDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        lista.add(i.id.toString())
                    }

                    iniciar2(lista)

                } catch (e: Exception) {
                    println("No hay datos de tipo de pago")

                }

            }
        })

    }

    private fun callServiceGetProveedores() {
        var lista: HashSet<String> = hashSetOf()

        val proveedoresService:ProveedoresService = RestEngine.buildService().create(ProveedoresService::class.java)
        var result: Call<List<ProveedoresDataCollectionItem>> = proveedoresService.listProveedores()

        result.enqueue(object : Callback<List<ProveedoresDataCollectionItem>> {
            override fun onFailure(call: Call<List<ProveedoresDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@Compras, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<ProveedoresDataCollectionItem>>,
                    response: Response<List<ProveedoresDataCollectionItem>>
            ) {
                try {
                    val parametro = StringBuilder()
                    var num =0
                    for (i in response.body()!!) {
                        lista.add(i.id.toString())
                    }





                    println("LA LISTA ES:"+lista.toString())
                    initial(lista)

                } catch (e: Exception) {
                    println("No hay datos de tipo de pago")

                }

            }
        })

    }
    private fun callServiceGetInsumo() {
        var lista: HashSet<String> = hashSetOf()

        val tipoService:InsumosService= RestEngine.buildService().create(InsumosService::class.java)
        var result: Call<List<InsumosDataCollectionItem>> = tipoService.listInsumos()

        result.enqueue(object : Callback<List<InsumosDataCollectionItem>> {
            override fun onFailure(call: Call<List<InsumosDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@Compras, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<InsumosDataCollectionItem>>,
                    response: Response<List<InsumosDataCollectionItem>>
            ) {
                try {
                    val parametro = StringBuilder()
                    var num =0
                    for (i in response.body()!!) {
                        lista.add(i.id.toString())
                    }





                    println("LA LISTA ES:"+lista.toString())
                    initial3(lista)

                } catch (e: Exception) {
                    println("No hay datos de tipo de pago")

                }

            }
        })

    }

    fun initial(a:HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerIdProvedor)
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
                callServiceGetProveedorById(A[position].toString().toLong())
            }
        }


    }

    fun iniciar2(a:HashSet<String>){
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
                callServiceGetTipoById(A[position].toString().toLong())


            }
        }

    }

    fun initial3(a:HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerInsumo)
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
                callServiceGetInsumobyId(A[position].toString().toLong())
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


    private fun callServiceGetProveedorById(a:Long) {
        var nombrep=""
        val provedorService:ProveedoresService = RestEngine.buildService().create(ProveedoresService::class.java)
        var result: Call<ProveedoresDataCollectionItem> = provedorService.getProveedoresById(a)

        result.enqueue(object : Callback<ProveedoresDataCollectionItem> {
            override fun onFailure(call: Call<ProveedoresDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@Compras,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProveedoresDataCollectionItem>,
                    response: Response<ProveedoresDataCollectionItem>
            ) {
                nombrep = response.body()!!.compañia.toString()
                txv_selecionP.text = nombrep

            }
        })


    }
    private fun callServiceGetInsumobyId(a: Long){
        var nombrep=""
        val insumoServices:InsumosService = RestEngine.buildService().create(InsumosService::class.java)
        var result: Call<InsumosDataCollectionItem> = insumoServices.getInsumosById(a)

        result.enqueue(object : Callback<InsumosDataCollectionItem> {
            override fun onFailure(call: Call<InsumosDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@Compras,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<InsumosDataCollectionItem>,
                    response: Response<InsumosDataCollectionItem>
            ) {
                nombrep = response.body()!!.nombre.toString()
                txv_selecionP2.text = nombrep

            }
        })
    }


    private fun callServiceGetTipoById(a:Long) {
        var nombrep=""
        val pagoservice:PagoService = RestEngine.buildService().create(PagoService::class.java)
        var result: Call<PagoDataCollectionItem> = pagoservice.getPagoById(a)

        result.enqueue(object : Callback<PagoDataCollectionItem> {
            override fun onFailure(call: Call<PagoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@Compras,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<PagoDataCollectionItem>,
                    response: Response<PagoDataCollectionItem>
            ) {
                nombrep = response.body()!!.descripcion.toString()
                txv_selecionF.text = nombrep

            }
        })


    }


}