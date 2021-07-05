package com.personal.mvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
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
import com.personal.mvvm.ViewModel.WeatherViewModel;
import com.personal.mvvm.databinding.ActivityMainBinding;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    private WeatherModel weatherModelList;
    private WeatherViewModel viewModel;
    FusedLocationProviderClient fusedLocationProviderClient;
    Double lat, lon;
    DecimalFormat df;
    ActionBar ab;
    String area;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCureentlocation();
        df = new DecimalFormat("#.##");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ab = getSupportActionBar();

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        viewModel.getWeatherListsObservable().observe(this, new Observer<WeatherModel>() {
            @Override
            public void onChanged(WeatherModel weatherModels) {
                if (weatherModels != null) {
                    weatherModelList = weatherModels;
                    ab.setTitle(weatherModels.getName() + "/" + area);
                    binding.tvWeatherDesc.setText("Weather Description=" + weatherModelList.getWeather().get(0).getDescription());
                    //img.
                    binding.tvTempInCel.setText("Temperature=" + df.format(weatherModelList.getMain().getTemp() - 273));
                    binding.tvFeelslike.setText("Feels Like temperature=" + df.format(weatherModelList.getMain().getFeelsLike() - 273));
                    binding.tvMin.setText("Temperature Minimum=" + df.format(weatherModelList.getMain().getTempMin() - 273));
                    binding.tvMax.setText("Temperature Maximum=" + df.format(weatherModelList.getMain().getTempMax() - 273));
                    Glide.with(getApplicationContext()).load(
                            "http://openweathermap.org/img/wn/" + weatherModelList.getWeather().get(0).getIcon() + "@2x.png"
                    ).into(binding.ivIcon);
                    binding.tvTime.setText( toDate(weatherModelList.getSys().getSunrise()+41600)+":"
                            +toDate((weatherModelList.getSys().getSunset()-44800)));
                } else {
                    Toast.makeText(MainActivity.this, "Restart app again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static String toDate(int time) {
        SimpleDateFormat HMM = new SimpleDateFormat("hh:mm a");
        HMM.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        final Date date = new Date(time*1000);
        return HMM.format(date);
    }
   

    private void getCureentlocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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
                            lat = addresses.get(0).getLatitude();
                            lon = addresses.get(0).getLongitude();
                            area = addresses.get(0).getAdminArea();
                            // locationChanged(lat,lon);
                            viewModel.TakeRetrofitData(lat, lon);
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

        super.onStart();

    }
}