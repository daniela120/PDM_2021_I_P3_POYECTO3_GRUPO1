package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities

import java.util.*
import kotlin.collections.ArrayList

class ComprasDataCollection : ArrayList<ComprasDataCollectionItem>()

data class  ComprasDataCollectionItem(
    val id:Long?,
    val cai:String,
    val proveedores:Long?,
    val numerotarjeta:Long?,
    val formapago:Long?,
    val fechaentrega:Date?,
    val fechacompra:Date?,
    val insumos:Long?
)