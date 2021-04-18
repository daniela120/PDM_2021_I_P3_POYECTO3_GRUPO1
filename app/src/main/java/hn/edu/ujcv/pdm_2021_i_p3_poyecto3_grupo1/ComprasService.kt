package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ComprasDataCollectionItem

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ComprasService {

    @GET("compras")
    fun listCompras(): Call<List<ComprasDataCollectionItem>>
    @GET("compras/id/{id}")
    fun getComprasById(@Path("id") id: Long): Call<ComprasDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("compras/addCompra")
    fun addCompras(@Body comprasData: ComprasDataCollectionItem): Call<ComprasDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("compras")
    fun updateCompras(@Body personData: ComprasDataCollectionItem): Call<ComprasDataCollectionItem>
    @DELETE("compras/delete/{id}")
    fun deleteCompras(@Path("id") id: Long): Call<ResponseBody>

}
