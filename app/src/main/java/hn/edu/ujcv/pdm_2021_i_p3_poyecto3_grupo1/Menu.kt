package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu.*

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        btn_regresar.setOnClickListener { regresarMain() }
        /*btn_Usuario.setOnClickListener { irUsuario() }*/
        btn_Empleado.setOnClickListener { irEmpleado() }
        btn_Departamento.setOnClickListener { irDepartamento() }
        btn_Ventas.setOnClickListener { irVentas() }
        btn_Productos.setOnClickListener { irProductos() }
        btn_Produccion.setOnClickListener { irProduccion() }
        btn_Inventario.setOnClickListener { irInventario() }
        btn_Almacen.setOnClickListener { irAlmacen() }
        btn_Compras.setOnClickListener { irCompras() }
        btn_Costos.setOnClickListener { irCostos() }
        btn_insumos.setOnClickListener { irInsumos() }
        btn_Tela.setOnClickListener { irTela() }
    }
    private fun regresarMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun irUsuario() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun irEmpleado() {
        val intent = Intent(this, Empleado::class.java)
        startActivity(intent)
    }
    private fun irDepartamento() {
        val intent = Intent(this, Departamento::class.java)
        startActivity(intent)
    }
    private fun irVentas() {
        val intent = Intent(this, Ventas::class.java)
        startActivity(intent)
    }
    private fun irProductos() {
        val intent = Intent(this, Productos::class.java)
        startActivity(intent)
    }
    private fun irProduccion() {
        val intent = Intent(this, Produccion::class.java)
        startActivity(intent)
    }
    private fun irInventario() {
        val intent = Intent(this, Inventario::class.java)
        startActivity(intent)
    }
    private fun irAlmacen() {
        val intent = Intent(this, Almacen::class.java)
        startActivity(intent)
    }
    private fun irCompras() {
        val intent = Intent(this, Compras::class.java)
        startActivity(intent)
    }
    private fun irCostos() {
        val intent = Intent(this, Costos::class.java)
        startActivity(intent)
    }

    private fun irInsumos() {
        val intent = Intent(this, Insumos::class.java)
        startActivity(intent)
    }
    private fun irTela() {
        val intent = Intent(this, Tela::class.java)
        startActivity(intent)
    }

}