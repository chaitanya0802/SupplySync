package com.supplysync.android.network

import com.supplysync.android.ui.home.FilledsizeSectionidResponse
import com.supplysync.android.ui.home.WarehouseDetails
import com.supplysync.android.ui.home.SectionDetails
import com.supplysync.android.ui.login.LoginRequest
import com.supplysync.android.ui.login.LoginResponse
import com.supplysync.android.ui.predict.PredictionResponse
import com.supplysync.android.ui.product.AddProductLotRequest
import com.supplysync.android.ui.product.AddProductLotResponse
import com.supplysync.android.ui.racksadder.RackAddRequest
import com.supplysync.android.ui.racksadder.RackAddResponse
import com.supplysync.android.ui.sectionadder.SectionAddRequest
import com.supplysync.android.ui.sectionadder.SectionAddResponse
import com.supplysync.android.ui.signUp.SignUpRequest
import com.supplysync.android.ui.signUp.SignUpResponse
import com.supplysync.android.ui.subordinatesignup.SubordinateSignUpRequest
import com.supplysync.android.ui.subordinatesignup.SubordinateSignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //for login
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    //to register new user
    @Headers("Content-Type: application/json")
    @POST("signup")
    suspend fun signUp(@Body request: SignUpRequest): SignUpResponse

    //to register new user
    @Headers("Content-Type: application/json")
    @POST("signup")
    suspend fun subordinatesignUp(@Body request: SubordinateSignUpRequest): SubordinateSignUpResponse

    //post section
    @Headers("Content-Type: application/json")
    @POST("add-section")
    suspend fun addSection(@Body post: SectionAddRequest) : Response<SectionAddResponse>

    //post rack
    @Headers("Content-Type: application/json")
    @POST("add-rack")
    suspend fun addRack(@Body post: RackAddRequest) : Response<RackAddResponse>

    //post product lot
    @Headers("Content-Type: application/json")
    @POST("add-productlot")
    suspend fun addProductLot(@Body post: AddProductLotRequest) : Response<AddProductLotResponse>

    @Headers("Content-Type: application/json")
    @PATCH("updateRack/{rack_identifier}")
    suspend fun updateRack(@Path("rack_identifier") rackIdentifier:String ,
                           @Body post: RackAddRequest) : RackAddResponse


    //get prediction
    @GET("predict/")
    suspend fun getPrediction(@Query("date") date: String,
                              @Query("product_id") productId: Int): Response<PredictionResponse>

    //get warehouse details
    @GET("get-warehouse-details")
    suspend fun getWarehouseDetails(@Query("warehouse_id") warehouse_id: String): Response<WarehouseDetails>

    @GET("get-section-details")
    suspend fun getSectionDetails(@Query("warehouse_id") warehouse_id: String): Response<SectionDetails>

    @GET("get-filledsize-sectionid")
    suspend fun getfilledsizesectionid(@Query("warehouse_id") warehouse_id: String): Response<List<FilledsizeSectionidResponse>>

}