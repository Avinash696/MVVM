package com.personal.mvvm.Repository;

import androidx.lifecycle.MutableLiveData;

import com.personal.mvvm.Model.WeatherModel;
import com.personal.mvvm.Network.APIService;
import com.personal.mvvm.Network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    // observer-when get hit will tell activity who is observing

    private MutableLiveData<WeatherModel> weatherList;
    public WeatherRepository(){
        weatherList=new MutableLiveData<>();
    }
//constructor
    public WeatherRepository(MutableLiveData<WeatherModel> weatherList) {
        this.weatherList = weatherList;
    }

    //return live data
    public MutableLiveData<WeatherModel> getWeatherListObserver(){
        return weatherList;
    }


    public void makeApiCall(){
        APIService apiService= RetrofitInstance.getClient().create(APIService.class);
//        //dummy data lat,long
//        Call<List<WeatherModel>> call=apiService.getWeatherList(26.8467,80.9462);
//        call.enqueue(new Callback<List<WeatherModel>>() {
//            @Override
//            public void onResponse(Call<List<WeatherModel>> call, Response<List<WeatherModel>> response) {
//                weatherList.postValue(response.body());
//                List<WeatherModel> ss=response.body();
//            }
//
//            @Override
//            public void onFailure(Call<List<WeatherModel>> call, Throwable t) {
//                //nothing display
//                weatherList.postValue(null);
//            }
//        });
//        APIService apiService= RetrofitInstance.getClient().create(APIService.class);
//////        dummy data lat,long
        Call<WeatherModel> call=apiService.getWeatherList(26.8467,80.9462);

       call.enqueue(new Callback<WeatherModel>() {
           @Override
           public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
//               WeatherModel ss=response.body();
//               binding.tvTempInCel.setText(ss.getMain().getTemp().toString());
               weatherList.postValue(response.body());
           }

           @Override
           public void onFailure(Call<WeatherModel> call, Throwable t) {

           }
       });
    }
}
