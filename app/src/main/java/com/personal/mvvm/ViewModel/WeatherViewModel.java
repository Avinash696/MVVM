package com.personal.mvvm.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.personal.mvvm.Model.WeatherModel;
import com.personal.mvvm.Repository.WeatherRepository;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class WeatherViewModel extends ViewModel {

    private WeatherRepository weatherRepository,timeRepositary;
    private Double d1,d2;


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

    public void TakeRetrofitData(Double d1,Double d2) {
        weatherRepository.makeApiCall(d1,d2);
    }
}
