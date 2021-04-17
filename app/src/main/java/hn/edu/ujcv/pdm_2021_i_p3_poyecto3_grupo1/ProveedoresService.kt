package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProveedoresDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ProveedoresService {
    @GET("proveedores")
    fun listProveedores(): Call<List<ProveedoresDataCollectionItem>>
    @GET("proveedores/id/{id}")
    fun getProveedoresById(@Path("id") id: Long): Call<ProveedoresDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("proveedores/addProveedores")
    fun addProveedores(@Body proveedoresData: ProveedoresDataCollectionItem): Call<ProveedoresDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("proveedores")
    fun updateProveedores(@Body proveedoresData: ProveedoresDataCollectionItem): Call<ProveedoresDataCollectionItem>
    @DELETE("proveedores/delete/{id}")
    fun deleteProveedores(@Path("id") id: Long): Call<ResponseBody>
}