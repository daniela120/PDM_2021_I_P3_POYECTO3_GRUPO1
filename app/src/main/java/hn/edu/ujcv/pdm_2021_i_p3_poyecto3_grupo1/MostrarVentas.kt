package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.CLIENTES.ClienteService
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.*
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_ventas.*
import kotlinx.android.synthetic.main.activity_ventas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import java.util.HashSet

class MostrarVentas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_ventas)
        btn_regresarVentas2.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarVenta)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}

        callServiceGetTipo()
        callServiceGetEmpleados()
        callServiceGetProductos()
        callServiceClientes()

    }
    private fun Regresar() {
        val intent = Intent(this, Ventas::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val ventasService:VentasService = RestEngine.buildService().create(VentasService::class.java)
        var result: Call<VentasDataCollectionItem> = ventasService.getVentasById(1)

        result.enqueue(object : Callback<VentasDataCollectionItem> {
            override fun onFailure(call: Call<VentasDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarVentas,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<VentasDataCollectionItem>,
                    response: Response<VentasDataCollectionItem>
            ) {
                Toast.makeText(this@MostrarVentas,"OK"+response.body()!!.cai, Toast.LENGTH_LONG).show()
            }
        })
    }


    /*FUNCIONES OBTENER DATOS EN SPINNERS*/
    /*SPINNER TIPO DE PAGO*/

    private fun callServiceGetTipo() {
        var lista: HashSet<String> = hashSetOf()

        val tipoService:PagoService = RestEngine.buildService().create(PagoService::class.java)
        var result: Call<List<PagoDataCollectionItem>> = tipoService.listPagos()

        result.enqueue(object : Callback<List<PagoDataCollectionItem>> {
            override fun onFailure(call: Call<List<PagoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@MostrarVentas, "Error", Toast.LENGTH_LONG).show()
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


    fun iniciar2(a: HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerFormaPagoM)
        var valor:String

        var A: ArrayList<String> = ArrayList()
        for(i in a){
            val data = i.toString().split("|")
            valor=data[0].toString()
            A.add(valor)

        }



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


    private fun callServiceGetTipoById(a:Long) {
        var nombrep=""
        val pagoservice:PagoService = RestEngine.buildService().create(PagoService::class.java)
        var result: Call<PagoDataCollectionItem> = pagoservice.getPagoById(a)

        result.enqueue(object : Callback<PagoDataCollectionItem> {
            override fun onFailure(call: Call<PagoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarVentas,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<PagoDataCollectionItem>,
                    response: Response<PagoDataCollectionItem>
            ) {
                nombrep = response.body()!!.descripcion.toString()
                txv_SeleccionTipo.text = nombrep

            }
        })


    }

    /*SPINNER EMPLEADO*/

    private fun callServiceGetEmpleados() {
        var lista: HashSet<String> = hashSetOf()

        val tipoService:EmpleadoService = RestEngine.buildService().create(EmpleadoService::class.java)
        var result: Call<List<EmpleadoDataCollectionItem>> = tipoService.listEmpleados()

        result.enqueue(object : Callback<List<EmpleadoDataCollectionItem>> {
            override fun onFailure(call: Call<List<EmpleadoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@MostrarVentas, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<EmpleadoDataCollectionItem>>,
                    response: Response<List<EmpleadoDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        lista.add(i.id.toString())
                    }

                    iniciar3(lista)

                } catch (e: Exception) {
                    println("No hay datos de tipo de pago")

                }

            }
        })

    }

    fun iniciar3(a: HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerIdEmpleadoM)
        var valor:String
        var A: ArrayList<String> = ArrayList()
        for(i in a){
            val data = i.toString().split("|")
            valor=data[0].toString()
            A.add(valor)

        }

        val adaptador = ArrayAdapter(this,android.R.layout.simple_spinner_item,A)

        spinner_Puestos.adapter =adaptador
        spinner_Puestos.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener { override fun onNothingSelected(parent: AdapterView<*>?) {
        }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                callServiceGetEmpleadoById(A[position].toString().toLong())


            }
        }

    }

    private fun callServiceGetEmpleadoById(a:Long) {
        var nombrep=""
        val pagoservice:EmpleadoService = RestEngine.buildService().create(EmpleadoService::class.java)
        var result: Call<EmpleadoDataCollectionItem> = pagoservice.getEmpleadoById(a)

        result.enqueue(object : Callback<EmpleadoDataCollectionItem> {
            override fun onFailure(call: Call<EmpleadoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarVentas,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<EmpleadoDataCollectionItem>,
                    response: Response<EmpleadoDataCollectionItem>
            ) {
                nombrep = response.body()!!.nombrecompleto.toString()
                txv_SeleccionEmp.text = nombrep

            }
        })


    }

    /*SPINNER PRODUCTO*/

    private fun callServiceGetProductos() {
        var lista: HashSet<String> = hashSetOf()

        val tipoService:ProductoService = RestEngine.buildService().create(ProductoService::class.java)
        var result: Call<List<ProductoDataCollectionItem>> = tipoService.listProductos()

        result.enqueue(object : Callback<List<ProductoDataCollectionItem>> {
            override fun onFailure(call: Call<List<ProductoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@MostrarVentas, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<ProductoDataCollectionItem>>,
                    response: Response<List<ProductoDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        lista.add(i.id.toString())
                    }

                    iniciar4(lista)

                } catch (e: Exception) {
                    println("No hay datos de tipo de pago")

                }

            }
        })

    }

    fun iniciar4(a: HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerIdProductoM)
        var valor:String
        var A: ArrayList<String> = ArrayList()
        for(i in a){
            val data = i.toString().split("|")
            valor=data[0].toString()
            A.add(valor)

        }

        val adaptador = ArrayAdapter(this,android.R.layout.simple_spinner_item,A)

        spinner_Puestos.adapter =adaptador
        spinner_Puestos.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener { override fun onNothingSelected(parent: AdapterView<*>?) {
        }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                callServiceGetProductoById(A[position].toString().toLong())


            }
        }

    }

    private fun callServiceGetProductoById(a:Long) {
        var nombrep=""
        val pagoservice:ProductoService = RestEngine.buildService().create(ProductoService::class.java)
        var result: Call<ProductoDataCollectionItem> = pagoservice.getProductoById(a)

        result.enqueue(object : Callback<ProductoDataCollectionItem> {
            override fun onFailure(call: Call<ProductoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarVentas,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProductoDataCollectionItem>,
                    response: Response<ProductoDataCollectionItem>
            ) {
                nombrep = response.body()!!.nombre.toString()
                txv_SeleccionProd.text = nombrep

            }
        })


    }

    /*SPINNER CLIENTEs*/

    private fun callServiceClientes() {
        var lista: HashSet<String> = hashSetOf()

        val tipoService: ClienteService = RestEngine.buildService().create(ClienteService::class.java)
        var result: Call<List<ClienteDataCollectionItem>> = tipoService.listClientes()

        result.enqueue(object : Callback<List<ClienteDataCollectionItem>> {
            override fun onFailure(call: Call<List<ClienteDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@MostrarVentas, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<ClienteDataCollectionItem>>,
                    response: Response<List<ClienteDataCollectionItem>>
            ) {
                try {
                    for (i in response.body()!!) {
                        lista.add(i.id.toString())
                    }

                    iniciar5(lista)

                } catch (e: Exception) {
                    println("No hay datos de tipo de pago")

                }

            }
        })

    }

    fun iniciar5(a: HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnercCLM)
        var valor:String
        var A: ArrayList<String> = ArrayList()
        for(i in a){
            val data = i.toString().split("|")
            valor=data[0].toString()
            A.add(valor)

        }

        val adaptador = ArrayAdapter(this,android.R.layout.simple_spinner_item,A)

        spinner_Puestos.adapter =adaptador
        spinner_Puestos.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener { override fun onNothingSelected(parent: AdapterView<*>?) {
        }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                callServiceGetClienteById(A[position].toString().toLong())


            }
        }

    }

    private fun callServiceGetClienteById(a:Long) {
        var nombrep=""
        val pagoservice: ClienteService = RestEngine.buildService().create(ClienteService::class.java)
        var result: Call<ClienteDataCollectionItem> = pagoservice.getClienteById(a)

        result.enqueue(object : Callback<ClienteDataCollectionItem> {
            override fun onFailure(call: Call<ClienteDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarVentas,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ClienteDataCollectionItem>,
                    response: Response<ClienteDataCollectionItem>
            ) {
                nombrep = response.body()!!.nombrecompleto.toString()
                txv_SeleccionCliente.text = nombrep

            }
        })


    }



}