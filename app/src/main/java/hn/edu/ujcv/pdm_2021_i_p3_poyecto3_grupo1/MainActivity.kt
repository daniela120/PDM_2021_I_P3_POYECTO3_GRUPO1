package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_login.setOnClickListener { Pruebaregistrar() }
    }
    private fun Pruebaregistrar() {
        /*if (txt_Usuario.text.toString()=="Helen Galo" && txt_Password.text.toString()=="helen" ) {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        } else {
            if (txt_Usuario.text.toString()=="Edwin Espino" && txt_Password.text.toString()=="edwin" ) {
                val intent = Intent(this, Menu::class.java)
                startActivity(intent)
            } else {
                if (txt_Usuario.text.toString()=="Miguel Torres" && txt_Password.text.toString()=="miguel" ) {
                    val intent = Intent(this, Menu::class.java)
                    startActivity(intent)
                } else {
                    if (txt_Usuario.text.toString() == "Daniela Herrera" && txt_Password.text.toString() == "daniela") {
                        val intent = Intent(this, Menu::class.java)
                        startActivity(intent)
                    } else {

                        Toast.makeText(this, "Ingrese un usuario valido ", Toast.LENGTH_SHORT).show()
                    }
                }

         } }*/
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
}

}