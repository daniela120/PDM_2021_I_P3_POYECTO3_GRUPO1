package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.CLIENTES.ClienteService
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.*
import kotlinx.android.synthetic.main.activity_compras.*
import kotlinx.android.synthetic.main.activity_departamento.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_mostrar_ventas.*
import kotlinx.android.synthetic.main.activity_ventas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import java.util.*

class Ventas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventas)
        btn_regresarVentas.setOnClickListener { Regresar()}

        findViewById<FloatingActionButton>(R.id.idFabListar_Ventas).setOnClickListener {
            Mostrar() }

        findViewById<FloatingActionButton>(R.id.idFabConfirmar_Ventas).setOnClickListener {
            validacion() }

        txt_FechaVenta.setOnClickListener{showDatePickerDialog()}
        txt_FechaEntrga2.setOnClickListener{showDatePickerDialog1()}
        callServiceGetTipo()
        callServiceGetEmpleados()
        callServiceGetProductos()
        callServiceClientes()
    }

    fun showDatePickerDialog() {
        val datePicker = DatePickerFragment{ day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDateSelected(day: Int, month: Int, year: Int) {
        if(month<10 && day<10){
            var a="$year-0$month-0$day"+"T06:00:00.000+00:00"
            txt_FechaVenta.setText(a.toString())
        }else{
            if(month<10 && day>9){


                var a="$year-0$month-$day"+"T06:00:00.000+00:00"
                txt_FechaVenta.setText(a.toString())}else{
                if(month>9 && day<10){

                    var a="$year-$month-0$day"+"T06:00:00.000+00:00"
                    txt_FechaVenta.setText(a.toString())
                }else{
                    var a="$year-$month-$day"+"T06:00:00.000+00:00"
                    txt_FechaVenta.setText(a.toString())
                }
            }
        }



    }


    fun showDatePickerDialog1() {
        val datePicker = DatePickerFragment{ day, month, year -> onDateSelected1(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDateSelected1(day: Int, month: Int, year: Int) {
        if(month<10 && day<10){
            var a="$year-0$month-0$day"+"T06:00:00.000+00:00"
            txt_FechaEntrga2.setText(a.toString())
        }else{
            if(month<10 && day>9){


                var a="$year-0$month-$day"+"T06:00:00.000+00:00"
                txt_FechaEntrga2.setText(a.toString())}else{
                if(month>9 && day<10){

                    var a="$year-$month-0$day"+"T06:00:00.000+00:00"
                    txt_FechaEntrga2.setText(a.toString())
                }else{
                    var a="$year-$month-$day"+"T06:00:00.000+00:00"
                    txt_FechaEntrga2.setText(a.toString())
                }
            }
        }
    }

    private fun callServicePostVenta() {
        try {


            val ventainfo = VentasDataCollectionItem(id = null,
                    descripcion = txt_DescripcionVenta.text.toString(),
                    idempleado = spinnerIdEmpleado2.selectedItem.toString().toLong(),
                    cai = txt_CaiVenta.text.toString().toLong(),
                    idcliente = spinnercCL.selectedItem.toString().toLong(),
                    numerotarjeta = txt_NoTarjetaVenta.text.toString().toLong(),
                    formadepago = spinnerFormaPago2.selectedItem.toString().toLong(),
                    fechaventa = txt_FechaVenta.text.toString(),
                    fechaentrega = txt_FechaEntrga2.text.toString()


            )
            addVenta(ventainfo) {
                if (it?.id != null) {
                    Toast.makeText(this@Ventas, "VENTA REGISTRADA", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@Ventas, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }catch (e:Exception){
            Toast.makeText(this@Ventas, "REVISAR LOS DATOS HA OCURRIDO UN ERROR", Toast.LENGTH_SHORT).show()
        }
    }

    fun  addVenta(ventasData: VentasDataCollectionItem, onResult: (VentasDataCollectionItem?) -> Unit){
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


    private  fun guardar():Boolean {
        var a=true


            if (txt_CaiVenta.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese CAI de la venta", Toast.LENGTH_SHORT).show()
                a=false
            } else {
                if (txt_DescripcionVenta.text.toString().isEmpty()) {
                    Toast.makeText(this, "Ingrese una descripcion", Toast.LENGTH_SHORT).show()
                    a = false
                } else {
                    if (spinnerIdEmpleado2.isSelected.toString().isEmpty()) {


                        a = false
                        Toast.makeText(this, "Ingrese ID de Empleado que realizo la venta", Toast.LENGTH_SHORT).show()
                        if (spinnerIdProducto2.isSelected.toString().isEmpty()) {
                            a = false
                            Toast.makeText(this, "Seleccione el producto", Toast.LENGTH_SHORT).show()
                        } else {
                            if (spinnerFormaPago2.isSelected.toString().isEmpty()) {
                                a = false
                                Toast.makeText(this, "Seleccione la forma de pago", Toast.LENGTH_SHORT).show()
                            } else {
                                if (spinnercCL.isSelected.toString().isEmpty()) {
                                    a = false
                                    Toast.makeText(this, "Seleccion el cliente", Toast.LENGTH_SHORT).show()
                                } else {
                                    if (txt_NoTarjetaVenta.text.isEmpty()) {
                                        a = false
                                        Toast.makeText(this, "Ingrese el numero de la tarjeta", Toast.LENGTH_SHORT).show()

                                    } else {
                                        if (txt_FechaVenta.text.isEmpty()) {
                                            a = false
                                            Toast.makeText(this, "Seleccione la fehca de venta", Toast.LENGTH_SHORT).show()

                                        } else {
                                            if (txt_FechaEntrga2.text.isEmpty()) {
                                                a = false
                                                Toast.makeText(this, "Seleccione la fehca de entrega", Toast.LENGTH_SHORT).show()

                                            } else {
                                                a = true

                                            }
                                        }
                                    }
                                }
                            }
                        }


                    }
                }
            }
        return a
    }

    fun validacion(){
        if(guardar().equals(true)){
            callServicePostVenta()
            Toast.makeText(this, "VENTA REGISTRADA", Toast.LENGTH_SHORT).show()
        }

    }

    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

    private fun Mostrar() {
        val intent = Intent(this, MostrarVentas::class.java)
        startActivity(intent)
    }


/*FUNCIONES OBTENER DATOS EN SPINNERS*/
    /*SPINNER TIPO DE PAGO*/

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

                    iniciar2(lista)

                } catch (e: Exception) {
                    println("No hay datos de tipo de pago")

                }

            }
        })

    }


    fun iniciar2(a:HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerFormaPago2)
        var valor:String

        var A:ArrayList<String> = ArrayList()
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
                Toast.makeText(this@Ventas,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<PagoDataCollectionItem>,
                    response: Response<PagoDataCollectionItem>
            ) {
                nombrep = response.body()!!.descripcion.toString()
                txv_SeleccionT2.text = nombrep

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
                Toast.makeText(this@Ventas, "Error", Toast.LENGTH_LONG).show()
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

    fun iniciar3(a:HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerIdEmpleado2)
        var valor:String
        var A:ArrayList<String> = ArrayList()
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
                Toast.makeText(this@Ventas,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<EmpleadoDataCollectionItem>,
                    response: Response<EmpleadoDataCollectionItem>
            ) {
                nombrep = response.body()!!.nombrecompleto.toString()
                txv_SeleccionE.text = nombrep

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
                Toast.makeText(this@Ventas, "Error", Toast.LENGTH_LONG).show()
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

    fun iniciar4(a:HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerIdProducto2)
        var valor:String
        var A:ArrayList<String> = ArrayList()
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
                Toast.makeText(this@Ventas,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProductoDataCollectionItem>,
                    response: Response<ProductoDataCollectionItem>
            ) {
                nombrep = response.body()!!.nombre.toString()
                txv_SeleccionP2.text = nombrep

            }
        })


    }

    /*SPINNER CLIENTEs*/

    private fun callServiceClientes() {
        var lista: HashSet<String> = hashSetOf()

        val tipoService:ClienteService = RestEngine.buildService().create(ClienteService::class.java)
        var result: Call<List<ClienteDataCollectionItem>> = tipoService.listClientes()

        result.enqueue(object : Callback<List<ClienteDataCollectionItem>> {
            override fun onFailure(call: Call<List<ClienteDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@Ventas, "Error", Toast.LENGTH_LONG).show()
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

    fun iniciar5(a:HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnercCL)
        var valor:String
        var A:ArrayList<String> = ArrayList()
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
        val pagoservice:ClienteService = RestEngine.buildService().create(ClienteService::class.java)
        var result: Call<ClienteDataCollectionItem> = pagoservice.getClienteById(a)

        result.enqueue(object : Callback<ClienteDataCollectionItem> {
            override fun onFailure(call: Call<ClienteDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@Ventas,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ClienteDataCollectionItem>,
                    response: Response<ClienteDataCollectionItem>
            ) {
                nombrep = response.body()!!.nombrecompleto.toString()
                txv_SeleccionC.text = nombrep

            }
        })


    }





}