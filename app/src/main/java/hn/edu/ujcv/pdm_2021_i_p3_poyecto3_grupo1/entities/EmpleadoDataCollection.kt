package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities

class EmpleadoDataCollection : ArrayList<EmpleadoDataCollectionItem>()

data class EmpleadoDataCollectionItem(
    val id:Long?,
    val nombrecompleto:String,
    val telefono: String,
    val correo: String,
    val direccion: String,
    val dni:String,
    val rtn: String
)