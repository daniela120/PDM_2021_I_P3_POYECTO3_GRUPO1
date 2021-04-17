package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1


import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.InsumosDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface InsumosService {
    @GET("insumos")
    fun listInsumos(): Call<List<InsumosDataCollectionItem>>
    @GET("insumos/id/{id}")
    fun getInsumosById(@Path("id") id: Long): Call<InsumosDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("insumos/addInsumos")
    fun addInsumos(@Body personData: InsumosDataCollectionItem): Call<InsumosDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("insumos")
    fun updateInsumos(@Body personData: InsumosDataCollectionItem): Call<InsumosDataCollectionItem>
    @DELETE("insumos/delete/{id}")
    fun deleteInsumos(@Path("id") id: Long): Call<ResponseBody>
}