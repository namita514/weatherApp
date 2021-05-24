package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
EditText e1;
ImageView b1;
TextView t1,t2,t3,t4,t5;

String url="api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
String ApiKey="6ff5ae6298b0645756d82e7f55a790ee";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=(TextView) findViewById(R.id.t1);   t2=(TextView) findViewById(R.id.t2);
        t3=(TextView) findViewById(R.id.t3);
        t4=(TextView) findViewById(R.id.t4);
        t5=(TextView) findViewById(R.id.t5);

        e1=(EditText)findViewById(R.id.e1);
        b1=(ImageView)findViewById(R.id.b1);


    }

    public void getweather(View v) {
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create()).build();
        weatherapi myapi=retrofit.create(weatherapi.class);
        Call<example> Example=myapi.getweather(e1.getText().toString().trim(),ApiKey);
        Example.enqueue(new Callback<example>() {
            @Override
            public void onResponse(Call<example> call, Response<example> response) {
                if(response.code()==404){
                    Toast.makeText(MainActivity.this,"please enter valid city name",Toast.LENGTH_SHORT).show();
                }
                else if(!(response.isSuccessful()))
                {
                    Toast.makeText(MainActivity.this,"no",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    example mydata=response.body();
                    Main main=mydata.getMain();
                    Double temp=main.getTemp();
                    Integer temprature=(int)(temp-273.15);
                    t1.setText(String.valueOf(temprature)+"C");
                    Integer hum=main.getHumidity();
                    t4.setText(String.valueOf(hum+"%"));
                    Integer pressure=main.getPressure();
                    t3.setText(String.valueOf(pressure+"hpa"));
                    Double tempmax=main.getTempMax();
                    Integer tempraturemax=(int)(tempmax-273.15);
                    t5.setText(String.valueOf(tempraturemax)+"C");
                    Double tempmin=main.getTempMin();
                    Integer tempraturemin=(int)(tempmin-273.15);
                    t2.setText(String.valueOf(tempraturemin)+"C");



                }
            }

            @Override
            public void onFailure(Call<example> call, Throwable t) {

                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}