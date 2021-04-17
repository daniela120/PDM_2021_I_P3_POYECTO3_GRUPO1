package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProduccionDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ProduccionService {
    @GET("produccion")
    fun listProduccion(): Call<List<ProduccionDataCollectionItem>>
    @GET("produccion/id/{id}")
    fun getProduccionById(@Path("id") id: Long): Call<ProduccionDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("produccion/addProduccion")
    fun addProduccion(@Body personData: ProduccionDataCollectionItem): Call<ProduccionDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("produccion")
    fun updateProduccion(@Body personData: ProduccionDataCollectionItem): Call<ProduccionDataCollectionItem>
    @DELETE("produccion/delete/{id}")
    fun deleteProduccion(@Path("id") id: Long): Call<ResponseBody>
}