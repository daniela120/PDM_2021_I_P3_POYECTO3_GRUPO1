package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.PagoDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
interface PagoService {
    @GET("pagos")
    fun listPagos(): Call<List<PagoDataCollectionItem>>
    @GET("pagos/id/{id}")
    fun getPagoById(@Path("id") id: Long): Call<PagoDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("pagos/addPersona")
    fun addPago(@Body personData: PagoDataCollectionItem): Call<PagoDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("pagos")
    fun updatePago(@Body personData: PagoDataCollectionItem): Call<PagoDataCollectionItem>
    @DELETE("pagos/delete/{id}")
    fun deletePago(@Path("id") id: Long): Call<ResponseBody>
}

//metodos que va llamar para consumir un recurso