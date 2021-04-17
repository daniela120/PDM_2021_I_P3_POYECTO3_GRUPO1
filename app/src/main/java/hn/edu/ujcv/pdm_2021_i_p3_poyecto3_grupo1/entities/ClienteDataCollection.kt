package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities

import java.util.*
import kotlin.collections.ArrayList

class ClienteDataCollection : ArrayList<ClienteDataCollectionItem>()

data class ClienteDataCollectionItem(
    val id:Long?,
    val nombrecompleto:String,
    val telefono: String,
    val correo: String,
    val direccion: String,
    val dni:String,
    val rtn: String
)