package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.*
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
        callServiceGetDepartamentos()
        callServiceGetProductos()
        callServiceEmpleados()
    }

    private fun resetear() {
        txt_MostrarDescripcion.setText("")
        txt_MostrarPrdTiempo.setText("")
        txt_IdProduccion2.setText("")
        txv_selectProd1.setText("")
        txv_selectProd2.setText("")
        txv_selectProd3.setText("")
        txt_MostrarDescripcion.isEnabled = false
        txt_MostrarPrdTiempo.isEnabled = false
        txv_selectProd1.isEnabled = false
        txv_selectProd2.isEnabled = false
        txv_selectProd3.isEnabled = false

    }

    private fun Regresar() {
        val intent = Intent(this, Produccion::class.java)
        startActivity(intent)
    }

    private fun callServiceGetPerson() {
        val produccionService: ProduccionService = RestEngine.buildService().create(ProduccionService::class.java)
        var result: Call<ProduccionDataCollectionItem> = produccionService.getProduccionById(txt_IdProduccion2.text.toString().toLong())

        result.enqueue(object : Callback<ProduccionDataCollectionItem> {
            override fun onFailure(call: Call<ProduccionDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarProduccion, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProduccionDataCollectionItem>,
                    response: Response<ProduccionDataCollectionItem>
            ) = try {
                ver()

                var a = response.body()!!.idproducto
                var b = response.body()!!.idempleado
                var c = response.body()!!.iddepto
                var d = response.body()!!.descripcion
                var e = response.body()!!.tiempo

                txv_selectProd1.setText(a.toString())
                txv_selectProd2.setText(b.toString())
                txv_selectProd3.setText(c.toString())
                txt_MostrarDescripcion.setText(d)
                txt_MostrarPrdTiempo.setText(e)

            } catch (e: Exception) {

                Toast.makeText(this@MostrarProduccion, "No existe la informacion con el id: " + txt_IdProduccion2.text.toString(), Toast.LENGTH_SHORT).show()
           resetear()
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
            Toast.makeText(this@MostrarProduccion, "NO SE PUEDO ELIMINAR LA INFORMACION CON EL ID: " + txt_IdProduccion2.toString(), Toast.LENGTH_LONG).show()

        }
    }

    private fun callServicePutProduccion() {
        try{

            val Info = ProduccionDataCollectionItem(id = txt_IdProduccion2.text.toString().toLong(),
                    idproducto = spinnerIdProducto3.selectedItem.toString().toLong(),
                    idempleado = spinnerIdEmpleado3.selectedItem.toString().toLong(),
                    iddepto =   spinnerMostrardepto.selectedItem.toString().toLong(),
                    descripcion = txt_MostrarDescripcion.text.toString(),
                    tiempo = txt_MostrarPrdTiempo.text.toString()
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
                        Toast.makeText(this@MostrarProduccion, " ACTUALIZADO", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@MostrarProduccion, "Sesion expirada", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MostrarProduccion, "Fallo al traer el item", Toast.LENGTH_LONG).show()
                    }
                }

            })
        } catch (e: Exception) {
            Toast.makeText(this@MostrarProduccion, e.message+" NO SE PUEDO ACTUALIZAR LA INFORMACION CON EL ID: " + txt_IdProduccion2.text.toString(), Toast.LENGTH_LONG).show()

        }
    }
/*SPINNER EMPLEADOS*/

    private fun callServiceEmpleados() {
        var lista: java.util.HashSet<String> = hashSetOf()

        val tipoService: EmpleadoService = RestEngine.buildService().create(EmpleadoService::class.java)
        var result: Call<List<EmpleadoDataCollectionItem>> = tipoService.listEmpleados()

        result.enqueue(object : Callback<List<EmpleadoDataCollectionItem>> {
            override fun onFailure(call: Call<List<EmpleadoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@MostrarProduccion, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<EmpleadoDataCollectionItem>>,
                    response: Response<List<EmpleadoDataCollectionItem>>
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

    fun iniciar5(a: java.util.HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerIdEmpleado3)
        var valor:String
        var A: java.util.ArrayList<String> = java.util.ArrayList()
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
        val pagoservice: EmpleadoService = RestEngine.buildService().create(EmpleadoService::class.java)
        var result: Call<EmpleadoDataCollectionItem> = pagoservice.getEmpleadoById(a)

        result.enqueue(object : Callback<EmpleadoDataCollectionItem> {
            override fun onFailure(call: Call<EmpleadoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarProduccion,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<EmpleadoDataCollectionItem>,
                    response: Response<EmpleadoDataCollectionItem>
            ) {
                nombrep = response.body()!!.nombrecompleto.toString()
                txv_selectProd2.text = nombrep

            }
        })


    }
    /*SPINNER PRODUCTO*/
    private fun callServiceGetProductos() {
        var lista: java.util.HashSet<String> = hashSetOf()

        val tipoService:ProductoService = RestEngine.buildService().create(ProductoService::class.java)
        var result: Call<List<ProductoDataCollectionItem>> = tipoService.listProductos()

        result.enqueue(object : Callback<List<ProductoDataCollectionItem>> {
            override fun onFailure(call: Call<List<ProductoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@MostrarProduccion,"Error", Toast.LENGTH_LONG).show()
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

    fun iniciar4(a: java.util.HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerIdProducto3)
        var valor:String
        var A: java.util.ArrayList<String> = java.util.ArrayList()
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
                Toast.makeText(this@MostrarProduccion,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProductoDataCollectionItem>,
                    response: Response<ProductoDataCollectionItem>
            ) {
                nombrep = response.body()!!.nombre.toString()
                txv_selectProd1.text = nombrep

            }
        })


    }
    /*SPINNER DEPTO*/
    private fun callServiceGetDepartamentos() {
        var lista: HashSet<String> = hashSetOf()
        val departamentoService:DepartamentoService = RestEngine.buildService().create(DepartamentoService::class.java)
        var result: Call<List<DepartamentoDataCollectionItem>> = departamentoService.listDepartamentos()

        result.enqueue(object : Callback<List<DepartamentoDataCollectionItem>> {
            override fun onFailure(call: Call<List<DepartamentoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@MostrarProduccion,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<DepartamentoDataCollectionItem>>,
                    response: Response<List<DepartamentoDataCollectionItem>>
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
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerMostrardepto)
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
                callServiceGetDepartamentoById(A[position].toString().toLong())
            }
        }


    }
    private  fun  callServiceGetDepartamentoById(a:Long) {
        var nombrep=""
        val departamentoService:DepartamentoService =  RestEngine.buildService().create(DepartamentoService::class.java)
        var result: Call<DepartamentoDataCollectionItem> =departamentoService.getDepartamentoById(a)

        result.enqueue(object : Callback<DepartamentoDataCollectionItem> {
            override fun onFailure(call: Call<DepartamentoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarProduccion,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<DepartamentoDataCollectionItem>,
                    response: Response<DepartamentoDataCollectionItem>
            ) {
                nombrep = response.body()!!.nombre
                txv_selectProd3.text = nombrep.toString()

            }
        })


    }

}

