package com.example.login22_01_19_h1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Confirmation_Appointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_appointment);

        String Day = getIntent().getStringExtra("Day");
        String Date = getIntent().getStringExtra("Date");
        String Time = getIntent().getStringExtra("Time");

        TextView txDay = (TextView) findViewById(R.id.DayInfo);
        TextView txDate = (TextView) findViewById(R.id.DateInfo);
        TextView txTime = (TextView) findViewById(R.id.TimeInfo);

        txDay.setText(Day);
        txDate.setText(Date);
        txTime.setText(Time);


    }
}