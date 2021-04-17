package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities

import java.util.*
import kotlin.collections.ArrayList

class PagoDataCollection: ArrayList<PagoDataCollectionItem>()

data class PagoDataCollectionItem(
    val id:Long?,
    val descripcion:String,
    val estado: String
)