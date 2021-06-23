package com.personal.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.personal.mvvm.Model.WeatherModel;
import com.personal.mvvm.Network.APIService;
import com.personal.mvvm.Network.RetrofitInstance;
import com.personal.mvvm.Repository.WeatherRepository;
import com.personal.mvvm.ViewModel.WeatherViewModel;
import com.personal.mvvm.ViewModel.WeatherViewModelFactory;
import com.personal.mvvm.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    private WeatherModel weatherModelList;
    private WeatherViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//        customClass();
       viewModel= new ViewModelProvider(this).get(WeatherViewModel.class);
       viewModel.getWeatherListsObservable().observe(this, new Observer<WeatherModel>() {
           @Override
           public void onChanged(WeatherModel weatherModels) {
               if(weatherModels !=null){
                   weatherModelList=weatherModels;
                   binding.tvTempInCel.setText(weatherModelList.getMain().getTemp().toString());

               }
               else{
                   binding.btnGo.setText("Error");
               }
           }
       });
       viewModel.TakeRetrofitData();
    }
    
}