package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.CLIENTES.ClienteService
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.*
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_mostrar_empleado.*
import kotlinx.android.synthetic.main.activity_mostrar_insumos.*
import kotlinx.android.synthetic.main.activity_mostrar_ventas.*
import kotlinx.android.synthetic.main.activity_ventas.*
import okhttp3.ResponseBody
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
        findViewById<FloatingActionButton>(R.id.idFabActualizar_Ventas).setOnClickListener {
            callServicePutVentas()
        }

        findViewById<FloatingActionButton>(R.id.idFabEliminar_Ventas).setOnClickListener {
            callServiceDeleteVenta()
        }


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
        var result: Call<VentasDataCollectionItem> = ventasService.getVentasById(txt_VentaId2.text.toString().toLong())

        result.enqueue(object : Callback<VentasDataCollectionItem> {
            override fun onFailure(call: Call<VentasDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarVentas, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<VentasDataCollectionItem>,
                    response: Response<VentasDataCollectionItem>
            ) {
                try {
                    var a = response.body()!!.descripcion
                    var b = response.body()!!.idempleado
                    var c = response.body()!!.cai
                    var d = response.body()!!.idcliente
                    var e = response.body()!!.numerotarjeta
                    var f = response.body()!!.formadepago
                    var g = response.body()!!.fechaventa
                    var h = response.body()!!.fechaentrega

                    txt_DescripcionVenta2.setText(a)
                    txv_SeleccionEmp.setText(b.toString())
                    txt_CaiVenta2.setText(c.toString())
                    txv_SeleccionCliente.setText(d.toString())
                    txt_NoTarjetaVentaM.setText(e.toString())
                    txv_SeleccionTipo.setText(f.toString())
                    txt_FechaVentaM.setText(g)
                    txt_FechaEntre.setText(h)

                    txt_DescripcionVenta2.isEnabled = true
                    txv_SeleccionEmp.isEnabled = true
                    txt_CaiVenta2.isEnabled = true
                    txv_SeleccionCliente.isEnabled = true
                    txt_NoTarjetaVentaM.isEnabled = true
                    txv_SeleccionTipo.isEnabled = true
                    txt_FechaVentaM.isEnabled = true
                    txt_FechaEntre.isEnabled = true


                } catch (e: Exception) {
                    Toast.makeText(this@MostrarVentas, "ERROR VERIFIQUE LOS DATOS", Toast.LENGTH_SHORT).show()
                    reset()
                }
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

    fun reset(){
        txt_VentaId2.setText("")
        txt_DescripcionVenta2.setText("")
        txv_SeleccionEmp.setText("")
        txt_CaiVenta2.setText("")
        txv_SeleccionCliente.setText("")
        txt_NoTarjetaVentaM.setText("")
        txv_SeleccionTipo.setText("")
        txt_FechaVentaM.setText("")
        txt_FechaEntre.setText("")

        txt_DescripcionVenta2.isEnabled = false
        txv_SeleccionEmp.isEnabled = false
        txt_CaiVenta2.isEnabled = false
        txv_SeleccionCliente.isEnabled = false
        txt_NoTarjetaVentaM.isEnabled = false
        txv_SeleccionTipo.isEnabled = false
        txt_FechaVentaM.isEnabled = false
        txt_FechaEntre.isEnabled = false

    }



    //Actualizar
    private fun callServicePutVentas() {
        try {

            val ventainfo = VentasDataCollectionItem(id = txt_VentaId2.text.toString().toLong(),
                    descripcion = txt_DescripcionVenta2.text.toString(),
                    idempleado = spinnerIdEmpleadoM.selectedItem.toString().toLong(),
                    cai = txt_CaiVenta2.text.toString().toLong(),
                    idcliente = spinnercCLM.selectedItem.toString().toLong(),
                    numerotarjeta = txt_NoTarjetaVentaM.text.toString().toLong(),
                    formadepago = spinnerFormaPagoM.selectedItem.toString().toLong(),
                    fechaventa = txt_FechaVentaM.text.toString(),
                    fechaentrega = txt_FechaEntre.text.toString()
            )


            val retrofit = RestEngine.buildService().create(VentasService::class.java)
            var result: Call<VentasDataCollectionItem> = retrofit.updateVentas(ventainfo)


            result.enqueue(object : Callback<VentasDataCollectionItem> {
                override fun onFailure(call: Call<VentasDataCollectionItem>, t: Throwable) {
                    Toast.makeText(this@MostrarVentas, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<VentasDataCollectionItem>,
                                        response: Response<VentasDataCollectionItem>) {
                    if (response.isSuccessful) {
                        val updatedPerson = response.body()!!
                        Toast.makeText(this@MostrarVentas, " VENTA ACTUALIZADA", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@MostrarVentas, "Sesion expirada", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MostrarVentas, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                    }
                }

            })
        }catch (e:Exception){
            Toast.makeText(this@MostrarVentas, "ERROR, POR FAVOR VERIFIQUE LOS DATOS", Toast.LENGTH_SHORT).show()
        }

    }
    //Metodo Delete
    private fun callServiceDeleteVenta() {
        try {


            val ventaService: VentasService = RestEngine.buildService().create(VentasService::class.java)
            var result: Call<ResponseBody> = ventaService.deleteVentas(txt_VentaId2.text.toString().toLong())

            result.enqueue(object :  Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MostrarVentas,"Error",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        reset()
                        Toast.makeText(this@MostrarVentas,"VENTA ELIMINADA",Toast.LENGTH_LONG).show()
                    }
                    else if (response.code() == 401){
                        Toast.makeText(this@MostrarVentas,"Sesion expirada",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this@MostrarVentas,"Fallo al traer el item",Toast.LENGTH_LONG).show()
                    }
                }
            })
        }catch (e: java.lang.Exception){
            Toast.makeText(this@MostrarVentas,"NO SE PUEDO ELIMINAR LA VENTA CON EL ID: "+ txt_IdInsumo2.text.toString(),Toast.LENGTH_LONG).show()
            reset()
        }    }




}