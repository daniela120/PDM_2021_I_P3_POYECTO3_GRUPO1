package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentasDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface VentasService {
    @GET("ventas")
    fun listVentas(): Call<List<VentasDataCollectionItem>>
    @GET("ventas/id/{id}")
    fun getVentasById(@Path("id") id: Long): Call<VentasDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("ventas/addVentas")
    fun addVentas(@Body personData: VentasDataCollectionItem): Call<VentasDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("ventas")
    fun updateVentas(@Body personData: VentasDataCollectionItem): Call<VentasDataCollectionItem>
    @DELETE("ventas/delete/{id}")
    fun deleteVentas(@Path("id") id: Long): Call<ResponseBody>
}