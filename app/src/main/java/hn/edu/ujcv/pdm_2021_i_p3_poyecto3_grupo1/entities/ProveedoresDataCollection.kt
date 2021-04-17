package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities

import java.util.*
import kotlin.collections.ArrayList

class ProveedoresDataCollection : ArrayList<ProveedoresDataCollection>()

data class  ProveedoresDataCollectionItem(
    val id:Long?,
    val nombre:String,
    val compañia:String,
    val rtn:String,
    val direccion:String
)