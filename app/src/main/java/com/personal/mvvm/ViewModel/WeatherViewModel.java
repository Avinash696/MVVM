package com.personal.mvvm.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.personal.mvvm.Model.WeatherModel;
import com.personal.mvvm.Repository.WeatherRepository;

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

    public void TakeRetrofitData(Double d1,Double d2) {
        weatherRepository.makeApiCall(d1,d2);
    }
    private void TimeHMSViewModel() {
       long time1 = weatherRepository.getWeatherListObserver().getValue().getSys().getSunrise();
       long time2 = weatherRepository.getWeatherListObserver().getValue().getSys().getSunrise();
//       long time2 = weatherModelList.getSys().getSunset();
//        Date date=new Date(millis);
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("h:mm a");
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//       simpleDateFormat.format(date);
    }
}
