package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ClienteDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ClienteService {
    @GET("clientes")
    fun listPersons(): Call<List<ClienteDataCollectionItem>>
    @GET("clientes/id/{id}")
    fun getPersonById(@Path("id") id: Long): Call<ClienteDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("clientes/addCliente")
    fun addPerson(@Body personData: ClienteDataCollectionItem): Call<ClienteDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("clientes")
    fun updatePerson(@Body personData: ClienteDataCollectionItem): Call<ClienteDataCollectionItem>
    @DELETE("clientes/delete/{id}")
    fun deletePerson(@Path("id") id: Long): Call<ResponseBody>
}