package com.personal.mvvm.Network;

import com.personal.mvvm.Model.WeatherModel;
import com.personal.mvvm.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {


//    http://openweathermap.org/img/wn/50d@2x.png
    //http://api.openweathermap.org/data/2.5/weather?lat=26.8467&lon=80.9462&appid=7cc03f1c69e5a31f837468766f3ab4b2
//?&&appid=7cc03f1c69e5a31f837468766f3ab4b2
    @GET("?&&appid=7cc03f1c69e5a31f837468766f3ab4b2")
    Call<WeatherModel> getWeatherList(@Query("lat") Double lat, @Query("lon") Double lon);
//    @GET()
//    Call

}
