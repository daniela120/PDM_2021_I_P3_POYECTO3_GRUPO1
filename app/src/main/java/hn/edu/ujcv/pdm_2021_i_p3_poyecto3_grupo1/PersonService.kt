package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.EmpleadoDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface PersonService {
        @GET("empleados")
        fun listPersons():Call<List<EmpleadoDataCollectionItem>>
        @GET("empleados/id/{id}")
        fun getPersonById(@Path("id") id: Long): Call<EmpleadoDataCollectionItem>
        @Headers("Content-Type: application/json")
        @POST("empleados/addempleado")
        fun addPerson(@Body personData: EmpleadoDataCollectionItem): Call<EmpleadoDataCollectionItem>
        @Headers("Content-Type: application/json")
        @PUT("empleados")
        fun updatePerson(@Body personData:EmpleadoDataCollectionItem): Call<EmpleadoDataCollectionItem>
        @DELETE("empleados/delete/{id}")
        fun deletePerson(@Path("id") id: Long): Call<ResponseBody>
}
