package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities

import java.util.*
import kotlin.collections.ArrayList

class InsumosDataCollection : ArrayList<InsumosDataCollectionItem>()

data class  InsumosDataCollectionItem(
    val id:Long?,
    val nombre:String,
    val tipo:String,
    val cantidad:String,
    val preciocompra:String,
    val precioventa:String,
)
