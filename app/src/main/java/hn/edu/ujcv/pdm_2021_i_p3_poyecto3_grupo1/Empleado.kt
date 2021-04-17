package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_cliente.*
import kotlinx.android.synthetic.main.activity_empleado.*
import kotlinx.android.synthetic.main.activity_productos.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.getbase.floatingactionbutton.FloatingActionButton
import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.EmpleadoDataCollectionItem


class Empleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado)
        btn_regresarEmpleado.setOnClickListener { Regresar() }
        findViewById<FloatingActionButton>(R.id.idFabListar_Empleado).setOnClickListener {
            Mostrar() }

    }


    private fun Regresar() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

    private fun Mostrar() {
        val intent = Intent(this, MostrarEmpleado::class.java)
        startActivity(intent)
    }



}