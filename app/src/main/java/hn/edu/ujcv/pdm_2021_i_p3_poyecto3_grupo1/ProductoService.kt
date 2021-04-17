
package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1


import hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1.entities.ProductoDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
interface ProductoService {
    @GET("productos")
    fun listProductos(): Call<List<ProductoDataCollectionItem>>
    @GET("productos/id/{id}")
    fun getProductoById(@Path("id") id: Long): Call<ProductoDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("productos/addProducto")
    fun addProducto(@Body personData: ProductoDataCollectionItem): Call<ProductoDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("productos")
    fun updateProducto(@Body personData: ProductoDataCollectionItem): Call<ProductoDataCollectionItem>
    @DELETE("productos/delete/{id}")
    fun deleteProducto(@Path("id") id: Long): Call<ResponseBody>
}

//metodos que va llamar para consumir un recurso