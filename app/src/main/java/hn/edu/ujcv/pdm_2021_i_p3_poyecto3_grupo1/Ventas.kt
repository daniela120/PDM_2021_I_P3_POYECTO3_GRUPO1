package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_compras.*
import kotlinx.android.synthetic.main.activity_departamento.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_ventas.*

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
        txt_FechaCompra.setText("$day / $month / $year")
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