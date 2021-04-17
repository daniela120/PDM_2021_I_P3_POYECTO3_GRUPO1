package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities

class EmpleadoDataCollection : ArrayList<EmpleadoDataCollectionItem>()

data class EmpleadoDataCollectionItem(
    val id:Long?,
    val nombrecompleto:String,
    val telefono: Long?,
    val correo: String,
    val clave: String,
    val cargo:String,

)