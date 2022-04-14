package com.example.login22_01_19_h1.Menu;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.login22_01_19_h1.Constants;
import com.example.login22_01_19_h1.Dates;
import com.example.login22_01_19_h1.DatesAdapter;
import com.example.login22_01_19_h1.MainActivity;
import com.example.login22_01_19_h1.R;
import com.example.login22_01_19_h1.RequestHandlerSingleton;
import com.example.login22_01_19_h1.SharedPrefManger;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookingFragment extends Fragment implements DatesAdapter.OnDateListener {

    RecyclerView recyclerView;
    ArrayList<Dates> dates = new ArrayList<>();
    DatesAdapter datesadapter;
    ArrayList<Button> times = new ArrayList<>();
    ViewGroup layout;
    int dayposition;
    private ProgressDialog progressDialog;
    String[][] calender = new String[50][20];
    TextView feedback;
    Button confirm;
    String CompanyID,MCenterID,CarID;
    int skipday =0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            CompanyID = bundle.getString("CompanyID", "");
            MCenterID = bundle.getString("MCenterID", "");
            CarID = bundle.getString("CarID", "");
        }
        return inflater.inflate(R.layout.activity_main_card_date, container, false);

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Initializing variables
        recyclerView = getView().findViewById(R.id.dates);
        progressDialog = new ProgressDialog(getActivity());

        stepone();
        DateRecycler();

    }

    private void stepone() {
        //Onclick listener for all buttons ( 16 )
        Button b1 = getView().findViewById(R.id.b1);
        times.add(b1);
        Button b2 = getView().findViewById(R.id.b2);
        times.add(b2);
        Button b3 = getView().findViewById(R.id.b3);
        times.add(b3);
        Button b4 = getView().findViewById(R.id.b4);
        times.add(b4);
        Button b5 = getView().findViewById(R.id.b5);
        times.add(b5);
        Button b6 = getView().findViewById(R.id.b6);
        times.add(b6);
        Button b7 = getView().findViewById(R.id.b7);
        times.add(b7);
        Button b8 = getView().findViewById(R.id.b8);
        times.add(b8);
        Button b9 = getView().findViewById(R.id.b9);
        times.add(b9);
        Button b10 = getView().findViewById(R.id.b10);
        times.add(b10);
        Button b11 = getView().findViewById(R.id.b11);
        times.add(b11);
        Button b12 = getView().findViewById(R.id.b12);
        times.add(b12);
        Button b13 = getView().findViewById(R.id.b13);
        times.add(b13);
        Button b14 = getView().findViewById(R.id.b14);
        times.add(b14);
        Button b15 = getView().findViewById(R.id.b15);
        times.add(b15);
        Button b16 = getView().findViewById(R.id.b16);
        times.add(b16);

        //loop throug all buttons and set on click for them and View.GONE
        for (int i = 0; i < times.size(); i++) {
            Log.println(Log.ASSERT, "ARRAY SSS", times.get(i) + " ");
            times.get(i).setText("");
            times.get(i).setBackgroundTintList(getActivity().getColorStateList(R.color.buttoncolors));
            times.get(i).setVisibility(View.GONE);
            times.get(i).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    for (int i = 0; i < times.size(); i++) {
                        if (view.getId() == times.get(i).getId()) {
                            appointmentInfo(times.get(i));
                        }
                    }
                }
            });
        }

        feedback = getView().findViewById(R.id.feedback);
        confirm = getView().findViewById(R.id.confirm);
        feedback.setText("");
        confirm.setVisibility(View.GONE);
        confirm.setTextColor(Color.parseColor("#ffffff"));
        confirm.setBackground(getActivity().getResources().getDrawable(R.drawable.buttondesign));
    }

    private void DateRecycler() {


        //Get SCHEDULE from the database based on the center user select
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SCHEDULE_RETRIVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String json_re) {
                progressDialog.dismiss();
                JSONArray jsonArray = null;
                //Essam
                try {
                    jsonArray = new JSONArray(json_re);
                    JSONObject jsonResponse = jsonArray.getJSONObject(0);
                    JSONArray jsonArray_carS = jsonResponse.getJSONArray("schedInfo");
                    for (int i = 0; i < jsonArray_carS.length() && i < 160; i++) {
                        JSONObject responsS = jsonArray_carS.getJSONObject(i);
                        String DateString = responsS.getString("date").trim();
                        String TimeString = responsS.getString("Time").trim();
                        String DayString = responsS.getString("Day").trim();
                        String Avaliable = responsS.getString("Avilibality").trim();
                        System.out.println("is avil - : "+Avaliable);
                        // get only Avaliable time slot
                        if (Avaliable.equalsIgnoreCase("Avaliable")) {
                            if (calender[0][0] == null) {
                                calender[0][0] = DayString;
                                calender[0][1] = DateString;
                                calender[0][2] = TimeString;
                            } else {
                                boolean check = false;
                                int k = 0;
                                for (k = 0; k < calender.length; k++) {
                                    if (calender[k][1] == null) {
                                        check = false;
                                        break;
                                    } else if (calender[k][1].equalsIgnoreCase(DateString)) {
                                        check = true;
                                        break;
                                    }
                                }

                                if (check == true) {
                                    int len2;
                                    for (len2 = 0; len2 < calender[k].length; len2++) {
                                        if (calender[k][len2] == null) {
                                            break;
                                        }
                                    }
                                    calender[k][len2] = TimeString;
                                } else {
                                    calender[k][0] = DayString;
                                    calender[k][1] = DateString;
                                    calender[k][2] = TimeString;
                                }
                            }
                        } else {
                            System.out.println("Testing ---------------------  not Avaliable");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //for testin :
                if (calender == null) {
                    System.out.println("Error ----------- Attempt to read from null array");
                } else {
                    for (int i = 0; i < calender.length; i++) { //this equals to the row in our matrix.
                        for (int j = 0; j < calender[i].length; j++) { //this equals to the column in each row.
                            System.out.print(calender[i][j] + " ");
                        }
                        System.out.println(); //change line on console as row comes to end in the matrix.
                    }
                }
                laststep();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
//                        Toast.makeText(getContext().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }

        }) {
            @androidx.annotation.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // prams the Mcenter ID
                params.put("MCenterID",MCenterID);
                return params;
            }
        };
        //Activate Request
        RequestHandlerSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    public void laststep() {

        // to add the dates we got from the database to recyclerview ( only today and after )
        for (int i = 0; i < calender.length && calender[i][0] != null; i++) {
            if (i==0){
                int result = fortmatDate(calender[i][1]);
                if (result >=0){
                    Dates date = new Dates(calender[i][1], calender[i][0]);
                    dates.add(date);

                }else {
                    skipday=1;
                }
            }else {
                Dates date = new Dates(calender[i][1], calender[i][0]);
                dates.add(date);
            }

        }
        // design herozintal layout
        // Initialize dateAdapter
        datesadapter = new DatesAdapter(dates, this);
        // set dateadapter to recyclerView
        recyclerView.setAdapter(datesadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // hide all time button
        layout = getView().findViewById(R.id.layout_container_buttons);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                button.setText("");
                button.setBackgroundTintList(getActivity().getColorStateList(R.color.buttoncolors));
                button.setVisibility(View.GONE);
            }
        }

    }
    // method get a string date and convert it to DATE then compare it with Today Date
    public int fortmatDate(String datein) {



        String DateForConvert=datein;
        String TimeForConvert = "15:30";

        //TimeForConvert =TimeForConvert.split(":",-2)[0];

        DateForConvert = DateForConvert +" 15:30:00";

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt;
        int result =-2 ;
        try {
            dt = sdf.parse(DateForConvert);
            result = dt.compareTo(date);
            System.out.println("sent "+dt.toString());
            System.out.println("now "+ date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    // method to compare the time with current time
    public int fortmatTime(String time) {
        String DateForConvert;
        String TimeForConvert = time;
        switch(TimeForConvert) {
            case "1:00":
                TimeForConvert = "13:00";
                break;
            case "1:30":
                TimeForConvert = "13:30";
                break;
            case "2:00":
                TimeForConvert = "14:00";
                break;
            case "2:30":
                TimeForConvert = "14:30";
                break;
            case "3:00":
                TimeForConvert = "15:00";
                break;
            case "3:30":
                TimeForConvert = "15:30";
                break;
            default:
                // code block
        }
        Date datetemp = new Date();
        SimpleDateFormat dateonly = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateonlynow = dateonly.format(datetemp);
        DateForConvert = dateonlynow +" " +TimeForConvert+":00";
        Date date = new Date();

        String offtime = dateonlynow +" 15:30:00";
        int istoday=-1;
        Date today;
        try {
            today = sdf.parse(offtime);
            istoday = today.compareTo(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dt;
        int result =-2 ;
        if (istoday<0){
            result=1;
        }else{
            try {
                dt = sdf.parse(DateForConvert);
                result = dt.compareTo(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    @Override
    public void onDateClick(int position) { // When a date selected
        dayposition = position;
        clearbuttons();
        String [] time = new String[16];
        // get the time and put it in Time array
        for (int i=2,t=0;i<calender[position+skipday].length && calender[position+skipday][i] != null; i++){
            if (position==0){
                int result = fortmatTime(calender[position+skipday][i]);
                if (result>=0){
                    time[t]=calender[position+skipday][i];
                    t++;
                }
            }else{
                time[t]=calender[position+skipday][i];
                t++;

            }

        }
        // show the user the time
        for (int i = 0; i < time.length && time[i] != null ; i++) {
            View child = layout.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                button.setText(time[i]);
                button.setVisibility(View.VISIBLE);

            }
        }
    }

    public void clearbuttons() { // to reset and hide all buttons
        confirm.setVisibility(View.GONE);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                button.setText("");
                button.setSelected(false);
                button.setTextColor(Color.parseColor("#000000"));
                button.setVisibility(View.GONE);
            }
        }
    }

    public void appointmentInfo(Button button) {
        resetcolors();
        feedback.setText("");
        button.setSelected(true);
        button.setTextColor(Color.parseColor("#ffffff"));
        // when the user select a time confirm button will be shown
        confirm.setVisibility(View.VISIBLE);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // check aviliblity first usnig php requset with lock
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SCHEDULE_Update, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.contains("Done")) {
                            System.out.println("--------------------Done -----------");
                            Toast.makeText(getContext(), "Booked successfully!", Toast.LENGTH_LONG).show();
                            //take him to next page
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.fragment_container,
                                    new HomeFragment()).commit();
                        } else {
                            System.out.println("--------------------Full sorry -----------");
                            Toast.makeText(getContext(), "Sorry! The requested time has already been filled, the available times have been updated, please rebook", Toast.LENGTH_LONG).show();
                            refresh();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                    }

                }) {
                    @androidx.annotation.Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("UserID", String.valueOf(SharedPrefManger.getInstance(MainActivity.getAppContext()).getUserId()));
                        params.put("MCenterID",MCenterID);
                        params.put("CarID",CarID);
                        params.put("date", calender[dayposition+skipday][1]);
                        params.put("Time", button.getText().toString());
                        return params;
                    }
                };
                RequestHandlerSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

            }
        });

    }

    public void resetcolors() {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                button.setSelected(false);
                button.setTextColor(Color.parseColor("#000000"));
            }
        }
    }

    public void refresh() {
        calender = null;
        calender = new String[50][20];
        DateRecycler();
    }


}


