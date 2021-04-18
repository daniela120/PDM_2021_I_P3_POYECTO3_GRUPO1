package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.CompraDetalleDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DetalleCompraService {

    @GET("CompraDetalle")
    fun listCompraDetalle(): Call<List<CompraDetalleDataCollectionItem>>
    @GET("CompraDetalle/id/{id}")
    fun getCompraDetalleById(@Path("id") id: Long): Call<CompraDetalleDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("CompraDetalle/addCompraDetalle")
    fun addCompraDetalle(@Body CompraDetalleData: CompraDetalleDataCollectionItem): Call<CompraDetalleDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("CompraDetalle")
    fun updateCompraDetalle(@Body CompraDetalleData: CompraDetalleDataCollectionItem): Call<CompraDetalleDataCollectionItem>
    @DELETE("CompraDetalle/delete/{id}")
    fun deleteCompraDetalle(@Path("id") id: Long): Call<ResponseBody>


}