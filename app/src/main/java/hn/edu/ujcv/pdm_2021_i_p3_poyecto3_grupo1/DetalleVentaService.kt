package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.VentaDetalleDataCollectionItem

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DetalleVentaService {

    @GET("VentaDetalle")
    fun listVentaDetalle(): Call<List<VentaDetalleDataCollectionItem>>
    @GET("VentaDetalle/id/{id}")
    fun getVentaDetalleById(@Path("id") id: Long): Call<VentaDetalleDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("VentaDetalle/addCompra")
    fun addVentaDetalle(@Body VentaDetalleData: VentaDetalleDataCollectionItem): Call<VentaDetalleDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("VentaDetalle")
    fun updateVentaDetalle(@Body VentaDetalleData: VentaDetalleDataCollectionItem): Call<VentaDetalleDataCollectionItem>
    @DELETE("VentaDetalle/delete/{id}")
    fun deleteVentaDetalle(@Path("id") id: Long): Call<ResponseBody>

}
