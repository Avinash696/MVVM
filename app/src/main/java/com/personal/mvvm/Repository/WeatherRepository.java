package com.personal.mvvm.Repository;

import androidx.lifecycle.MutableLiveData;

import com.personal.mvvm.Model.WeatherModel;
import com.personal.mvvm.Network.APIService;
import com.personal.mvvm.Network.RetrofitInstance;

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    // observer-when get hit will tell activity who is observing

    private MutableLiveData<WeatherModel> weatherList;

    public WeatherRepository(){
        weatherList=new MutableLiveData<>();
    }

    //return live data
    public MutableLiveData<WeatherModel> getWeatherListObserver(){
        return weatherList;
    }
    public void makeApiCall(Double mlat,Double mlon){
        APIService apiService= RetrofitInstance.getClient().create(APIService.class);

        Call<WeatherModel> call=apiService.getWeatherList(mlat,mlon);

       call.enqueue(new Callback<WeatherModel>() {
           @Override
           public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
               weatherList.postValue(response.body());
           }

           @Override
           public void onFailure(Call<WeatherModel> call, Throwable t) {

           }
       });
    }

}
