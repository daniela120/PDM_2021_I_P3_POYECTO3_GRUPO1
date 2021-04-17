package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities

import android.text.style.TtsSpan
import java.util.*
import kotlin.collections.ArrayList

class CompraDetalleDataCollection : ArrayList<CompraDetalleDataCollectionItem>()

data class CompraDetalleDataCollectionItem(
    val id:Long?,
    val idcompra:Long?,
    val cantidad:Long?,
    val precio:Long?,
    val total: Long?

)