package com.functrco.sail.api.services

import com.functrco.sail.api.ApiEndPoints
import com.functrco.sail.models.BannerModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface BannerService {
    @GET(ApiEndPoints.BANNERS)
    suspend fun getBanners(): Response<List<BannerModel>>
}