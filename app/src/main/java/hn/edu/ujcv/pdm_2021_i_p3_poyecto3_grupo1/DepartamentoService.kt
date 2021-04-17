
package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.DepartamentoDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
interface DepartamentoService {
    @GET("departamentos")
    fun listDepartamentos(): Call<List<DepartamentoDataCollectionItem>>
    @GET("departamentos/id/{id}")
    fun getDepartamentoById(@Path("id") id: Long): Call<DepartamentoDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("departamentos/addDepartamento")
    fun addDepartamento(@Body personData: DepartamentoDataCollectionItem): Call<DepartamentoDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("departamentos")
    fun updateDepartamento(@Body personData: DepartamentoDataCollectionItem): Call<DepartamentoDataCollectionItem>
    @DELETE("departamentos/delete/{id}")
    fun deleteDepartamento(@Path("id") id: Long): Call<ResponseBody>
}

