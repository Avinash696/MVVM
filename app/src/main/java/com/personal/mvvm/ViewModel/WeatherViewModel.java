package com.personal.mvvm.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.personal.mvvm.Model.WeatherModel;
import com.personal.mvvm.Repository.WeatherRepository;

import java.util.List;

public class WeatherViewModel extends ViewModel {

    private WeatherRepository weatherRepository;

    public WeatherViewModel() {
        weatherRepository = new WeatherRepository();
    }
//constructor
    public WeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }
//return
    public MutableLiveData<WeatherModel> getWeatherListsObservable(){
        return weatherRepository.getWeatherListObserver();
    }

    public void TakeRetrofitData(){
        weatherRepository.makeApiCall();
    }
}
