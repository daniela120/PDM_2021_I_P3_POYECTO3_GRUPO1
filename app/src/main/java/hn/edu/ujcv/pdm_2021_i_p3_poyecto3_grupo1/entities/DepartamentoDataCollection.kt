package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities

import java.util.*
import kotlin.collections.ArrayList

class DepartamentoDataCollection : ArrayList<DepartamentoDataCollectionItem>()

data class DepartamentoDataCollectionItem(
    val id:Long?,
    val nombre:String,
    val descripcion: String
)