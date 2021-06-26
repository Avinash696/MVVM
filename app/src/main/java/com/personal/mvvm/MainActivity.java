package com.personal.mvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.personal.mvvm.Model.WeatherModel;
import com.personal.mvvm.Network.APIService;
import com.personal.mvvm.Network.RetrofitInstance;
import com.personal.mvvm.Repository.WeatherRepository;
import com.personal.mvvm.ViewModel.WeatherViewModel;
import com.personal.mvvm.ViewModel.WeatherViewModelFactory;
import com.personal.mvvm.databinding.ActivityMainBinding;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    private WeatherModel weatherModelList;
    private WeatherViewModel viewModel;
    FusedLocationProviderClient fusedLocationProviderClient;
    Double lat,lon;
    DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        viewModel.getWeatherListsObservable().observe(this, new Observer<WeatherModel>() {
            @Override
            public void onChanged(WeatherModel weatherModels) {
                if (weatherModels != null) {
                    weatherModelList = weatherModels;

                    binding.tvWeatherDesc.setText(weatherModelList.getWeather().get(0).getDescription());
                    //img.
                    binding.tvTempInCel.setText(df.format(weatherModelList.getMain().getTemp() - 273));
                    binding.tvFeelslike.setText(df.format(weatherModelList.getMain().getFeelsLike() - 273));
                    binding.tvMin.setText(df.format(weatherModelList.getMain().getTempMin() - 273));
                    binding.tvMax.setText(df.format(weatherModelList.getMain().getTempMax() - 273));
                    Glide.with(getApplicationContext()).load(
                            "http://openweathermap.org/img/wn/" + weatherModelList.getWeather().get(0).getIcon() + "@2x.png"
                    ).into(binding.ivIcon);
                    cc();

                } else {
                    binding.btnGo.setText("Error");
                }
            }
        });


    }

    private void cc() {
        long time1 = weatherModelList.getSys().getSunrise();
        long time2 = weatherModelList.getSys().getSunset();
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        String mTime1 = simpleTimeFormat.format(time1);
        String mTime2 = simpleTimeFormat.format(time2);
        binding.tvTime.setText(mTime1 + "/" + mTime2);
    }

    private void gg() {
        //permission check
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //since granted
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //initiate location
                    Location location = task.getResult();
                    if (location != null) {
                        //initi geocoder
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        //address list
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                    location.getLongitude(), 1);
                            //here u have lat long now
                            lat=addresses.get(0).getLatitude();
                            lon=addresses.get(0).getLongitude();
                           // locationChanged(lat,lon);
                            viewModel.TakeRetrofitData(lat,lon);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            //when denied
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    @Override
    protected void onStart() {
        gg();
        super.onStart();
         df=new DecimalFormat("#.##");
    }
}