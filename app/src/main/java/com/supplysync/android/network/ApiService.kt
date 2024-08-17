package com.storeway.android.network

import com.supplysync.android.ui.home.VizResponse
import com.supplysync.android.ui.login.LoginRequest
import com.supplysync.android.ui.login.LoginResponse
import com.supplysync.android.ui.predict.PredictionRequest
import com.supplysync.android.ui.predict.PredictionResponse
import com.supplysync.android.ui.predictlp.PredictionLRequest
import com.supplysync.android.ui.predictlp.PredictionLResponse
import com.supplysync.android.ui.racksadder.RackAddRequest
import com.supplysync.android.ui.racksadder.RackAddResponse
import com.supplysync.android.ui.sectionadder.SectionAddRequest
import com.supplysync.android.ui.sectionadder.SectionAddResponse
import com.supplysync.android.ui.signUp.SignUpRequest
import com.supplysync.android.ui.signUp.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    //for login
    @POST("login/")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    //to register new user
    @Headers("Content-Type: application/json")
    @POST("signUp/")
    suspend fun signUp(@Body request: SignUpRequest): SignUpResponse

    //post section
    @Headers("Content-Type: application/json")
    @POST("addSection/")
    suspend fun addSection(@Body post: SectionAddRequest) : SectionAddResponse

    //post rack
    @Headers("Content-Type: application/json")
    @POST("addRack/")
    suspend fun addRack(@Body post: RackAddRequest) : RackAddResponse

    @Headers("Content-Type: application/json")
    @PATCH("updateRack/{rack_identifier}")
    suspend fun updateRack(@Path("rack_identifier") rackIdentifier:String ,
                           @Body post: RackAddRequest) : RackAddResponse


    //get section_identifier vs total racks
    @GET("getSectionIDTRViz/")
    suspend fun getViz1(): VizResponse

    //get rack_identifier vs quantity
    @GET("getRackIDQViz/")
    suspend fun getViz2(): VizResponse

    //get rack_identifier vs Date
    @GET("getRackIDDViz")
    suspend fun getViz3(): VizResponse

    //get rack_identifier vs size
    @GET("getRackIDSViz/")
    suspend fun getViz4(): VizResponse

    //get rack_identifier vs is filled?
    @GET("getRackIDFViz/")
    suspend fun getViz5(): VizResponse

    //get qty vs is time
    @GET("getQTYvsT/")
    suspend fun getViz6(): VizResponse

    //get prediction
    @POST("predictDemand/")
    suspend fun getPrediction(@Body data : PredictionRequest) : PredictionResponse

    //get prediction for logistics
    @POST("predictDemand/")
    suspend fun getPrediction(@Body data : PredictionLRequest) : PredictionLResponse

    //todo complete for get = order calls

}