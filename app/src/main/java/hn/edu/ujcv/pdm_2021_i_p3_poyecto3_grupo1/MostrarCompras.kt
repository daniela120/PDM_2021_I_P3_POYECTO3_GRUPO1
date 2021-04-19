package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.InsumosDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.PagoDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProveedoresDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_compras.*
import kotlinx.android.synthetic.main.activity_mostrar_compras.*
import kotlinx.android.synthetic.main.activity_mostrar_compras.btn_regresarCompras
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.lang.StringBuilder

class MostrarCompras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_compras)
        btn_regresarCompras.setOnClickListener { Regresar()}
        val botonGetId = findViewById<Button>(R.id.btn_BuscarCompra)
        botonGetId.setOnClickListener {v -> callServiceGetPerson()}
    }
    private fun Regresar() {
        val intent = Intent(this, Compras::class.java)
        startActivity(intent)
    }
    private fun callServiceGetPerson() {
        val comprasService:ComprasService = RestEngine.buildService().create(ComprasService::class.java)
        var result: Call<ComprasDataCollectionItem> = comprasService.getComprasById(txt_CompraId.text.toString().toLong())

        result.enqueue(object : Callback<ComprasDataCollectionItem> {
            override fun onFailure(call: Call<ComprasDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarCompras,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ComprasDataCollectionItem>,
                    response: Response<ComprasDataCollectionItem>
            ) = try {


                var a = response.body()!!.cai
                var b = response.body()!!.numerotarjeta
                var c = response.body()!!.fechaentrega
                var d = response.body()!!.fechacompra
                var e = response.body()!!.formapago
                var f = response.body()!!.insumos
                var g = response.body()!!.proveedores
                ver()
                txt_CaiCompra2.setText(a)
                txt_TarjetaCompra2.setText(b.toString())
                txt_FechaE2.setText(c)
                txt_FechaCompra2.setText(d)
                txv_selecionF2.setText(e.toString())
                txv_selecionP4.setText(f.toString())
                txv_selecionP3.setText(g.toString())
            }catch (e:Exception){
                Toast.makeText(this@MostrarCompras, "No existe la compra con el id: "+txt_CompraId.text.toString(), Toast.LENGTH_SHORT ).show()
            }
        })
    }

    fun ver(){

        txt_CaiCompra2.isEnabled =  true
        txt_TarjetaCompra2.isEnabled = true
        txt_FechaE2.isEnabled =  true
        txt_FechaCompra2.isEnabled =  true
        txv_selecionF2.isEnabled =  true
        txv_selecionP4.isEnabled =  true
        txv_selecionP3.isEnabled =  true


    }

    private fun callServiceGetTipo() {
        var lista: HashSet<String> = hashSetOf()

        val tipoService:PagoService = RestEngine.buildService().create(PagoService::class.java)
        var result: Call<List<PagoDataCollectionItem>> = tipoService.listPagos()

        result.enqueue(object : Callback<List<PagoDataCollectionItem>> {
            override fun onFailure(call: Call<List<PagoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@MostrarCompras, "Error", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this@MostrarCompras, "Error", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this@MostrarCompras, "Error", Toast.LENGTH_LONG).show()
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
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerIdProvedorC)
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
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerFormaPagoC)
        var valor:String
        println("THIS IS"+a.toString())
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

    fun initial3(a:HashSet<String>){
        val spinner_Puestos = findViewById<Spinner>(R.id.spinnerInsumo2)
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

    private fun callServiceGetProveedorById(a:Long) {
        var nombrep=""
        val provedorService:ProveedoresService = RestEngine.buildService().create(ProveedoresService::class.java)
        var result: Call<ProveedoresDataCollectionItem> = provedorService.getProveedoresById(a)

        result.enqueue(object : Callback<ProveedoresDataCollectionItem> {
            override fun onFailure(call: Call<ProveedoresDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarCompras,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ProveedoresDataCollectionItem>,
                    response: Response<ProveedoresDataCollectionItem>
            ) {
                nombrep = response.body()!!.compañia.toString()
                txv_selecionP3.text = nombrep

            }
        })


    }
    private fun callServiceGetInsumobyId(a: Long){
        var nombrep=""
        val insumoServices:InsumosService = RestEngine.buildService().create(InsumosService::class.java)
        var result: Call<InsumosDataCollectionItem> = insumoServices.getInsumosById(a)

        result.enqueue(object : Callback<InsumosDataCollectionItem> {
            override fun onFailure(call: Call<InsumosDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarCompras,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<InsumosDataCollectionItem>,
                    response: Response<InsumosDataCollectionItem>
            ) {
                nombrep = response.body()!!.nombre.toString()
                txv_selecionP4.text = nombrep

            }
        })
    }


    private fun callServiceGetTipoById(a:Long) {
        var nombrep=""
        val pagoservice:PagoService = RestEngine.buildService().create(PagoService::class.java)
        var result: Call<PagoDataCollectionItem> = pagoservice.getPagoById(a)

        result.enqueue(object : Callback<PagoDataCollectionItem> {
            override fun onFailure(call: Call<PagoDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@MostrarCompras,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<PagoDataCollectionItem>,
                    response: Response<PagoDataCollectionItem>
            ) {
                nombrep = response.body()!!.descripcion.toString()
                txv_selecionF2.text = nombrep

            }
        })


    }




}