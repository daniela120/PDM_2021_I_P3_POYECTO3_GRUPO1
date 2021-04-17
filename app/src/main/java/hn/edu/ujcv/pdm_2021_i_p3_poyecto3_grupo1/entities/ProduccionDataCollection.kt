package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities

import java.util.*
import kotlin.collections.ArrayList

class ProduccionDataCollection : ArrayList<ProduccionDataCollection>()

data class  ProduccionDataCollectionItem(
    val id:Long?,
    val idproducto:Long?,
    val idempleado:Long?,
    val iddepto:Long?,
    val descripcion:String,
    val tiempo:String
)