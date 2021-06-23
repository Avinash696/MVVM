package com.personal.mvvm.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.personal.mvvm.Repository.WeatherRepository;

//since used var in viewMoel
public class WeatherViewModelFactory implements ViewModelProvider.Factory {
    //var
    private WeatherRepository weatherRepository;
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WeatherViewModel(weatherRepository);
    }
}
