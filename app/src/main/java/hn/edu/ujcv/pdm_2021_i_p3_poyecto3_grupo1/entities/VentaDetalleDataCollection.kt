package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities

import java.util.*
import kotlin.collections.ArrayList

class VentaDetalleDataCollection : ArrayList<VentaDetalleDataCollectionItem>()

data class VentaDetalleDataCollectionItem(
    val id:Long?,
    val cantidad:Long?,
    val precio:Long?,
    val idventa:Long?,
    val idproducto:Long?
)