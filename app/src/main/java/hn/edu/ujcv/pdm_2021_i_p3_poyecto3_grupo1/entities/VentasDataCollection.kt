package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities

import java.util.*
import kotlin.collections.ArrayList

class VentasDataCollection : ArrayList<VentasDataCollectionItem>()

data class  VentasDataCollectionItem(
    val id:Long?,
    val descripcion:String,
    val idempleado:String,
    val cai:String,
    val idcliente:String,
    val numerotarjeta:String,
    val formadepago:String,
    val fechaventa:String,
    val fechaentrega:String
)