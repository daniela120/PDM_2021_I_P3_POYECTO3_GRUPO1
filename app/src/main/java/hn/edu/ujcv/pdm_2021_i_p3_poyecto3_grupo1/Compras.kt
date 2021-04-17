package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ClienteDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.PagoDataCollectionItem
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProveedoresDataCollectionItem
import kotlinx.android.synthetic.main.activity_cliente.*

import kotlinx.android.synthetic.main.activity_compras.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.sql.Date

class Compras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compras)

        btn_regresarCompras.setOnClickListener { Regresar() }

        findViewById<FloatingActionButton>(R.id.idFabListar_Compras).setOnClickListener {
            Mostrar() }


        findViewById<FloatingActionButton>(R.id.idFabConfirmar_Compras).setOnClickListener {
            callServicePostCompra() }
        inicalizar()

    }



    private fun callServicePostCompra() {


        val compraInfo = ComprasDataCollectionItem(id = null,
                cai = txt_CaiCompra.text.toString(),
                proveedores = txt_TelefonoCliente.text.toString().toLong(),
                numerotarjeta = txt_dniCliente.text.toString().toLong(),
                formapago = spinnerFormaPago.selectedItemId.toString().toLong(),
                fechaentrega = null,
                fechacompra =null ,
                insumos = 1
        )

        addCompra(compraInfo) {
            if (it?.id != null) {
                Toast.makeText(this@Compras,"COMPRA GUARDADA EXITOSAMENTE",Toast.LENGTH_LONG).show()
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




    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }
    private fun Mostrar() {
        val intent = Intent(this, MostrarCompras::class.java)
        startActivity(intent)
    }

    private fun inicalizar(){
        /*INICIALIZAR EL SPINNER TIPO DE PAGO*/
        var A:ArrayList<String> = ArrayList()
        A = callServiceGetTipoPago()

        val adaptador = ArrayAdapter(this,android.R.layout.simple_spinner_item, A)
        spinnerFormaPago.adapter =adaptador
        spinnerFormaPago.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                Toast.makeText(this@Compras,A[position].toString(), Toast.LENGTH_SHORT)

            }

        }

        /*INICIALIZAR EL SPINNER PROVEEDORES*/
        var B:ArrayList<String> = ArrayList()
        B = callServiceGetProveedor()

        val adaptador1 = ArrayAdapter(this,android.R.layout.simple_spinner_item, B)
        spinnerIdProvedor.adapter =adaptador1
        spinnerIdProvedor.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                Toast.makeText(this@Compras,B[position].toString(), Toast.LENGTH_SHORT)

            }

        }


    }

    private fun callServiceGetTipoPago():ArrayList<String> {
        var A:ArrayList<String> = ArrayList()

        val tipoService:PagoService = RestEngine.buildService().create(PagoService::class.java)
        var result: Call<List<PagoDataCollectionItem>> = tipoService.listPagos()

        result.enqueue(object :  Callback<List<PagoDataCollectionItem>> {
            override fun onFailure(call: Call<List<PagoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@Compras,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<PagoDataCollectionItem>>,
                    response: Response<List<PagoDataCollectionItem>>
            ) {
                try {
                    for(i in response.body()!!){
                        A.add(i.id.toString())
                    }

                    Toast.makeText(this@Compras,"OK"+response.body()!!.get(0).descripcion,Toast.LENGTH_LONG).show()
                }catch (e:Exception){
                    println(e.message)
                }

            }
        })
        return A
    }


    private fun callServiceGetProveedor():ArrayList<String> {
        var A:ArrayList<String> = ArrayList()

        val proveedorService:ProveedoresService = RestEngine.buildService().create(ProveedoresService::class.java)
        var result: Call<List<ProveedoresDataCollectionItem>> = proveedorService.listProveedores()

        result.enqueue(object :  Callback<List<ProveedoresDataCollectionItem>> {
            override fun onFailure(call: Call<List<ProveedoresDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@Compras,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<List<ProveedoresDataCollectionItem>>,
                    response: Response<List<ProveedoresDataCollectionItem>>
            ) {
                try {
                    for(i in response.body()!!){
                        A.add(i.id.toString())
                    }

                    Toast.makeText(this@Compras,"OK"+response.body()!!.get(0).compañia,Toast.LENGTH_LONG).show()
                }catch (e:Exception){
                    println(e.message)
                }

            }
        })
        return A
    }



    /*fun obtener():List<String>{


    }*/

}