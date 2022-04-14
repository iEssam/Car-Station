package com.example.login22_01_19_h1.Menu;

//import android.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.login22_01_19_h1.Constants;
import com.example.login22_01_19_h1.LoginSingup.Login_MySql;
import com.example.login22_01_19_h1.LoginSingup.signUp_mysqlphp;
import com.example.login22_01_19_h1.MainActivity;
import com.example.login22_01_19_h1.R;
import com.example.login22_01_19_h1.RequestHandlerSingleton;
import com.example.login22_01_19_h1.SharedPrefManger;
import com.example.login22_01_19_h1.SliderUpcoming_Resrv.UpcomingCardHelper;
import com.example.login22_01_19_h1.SliderUpcoming_Resrv.adapterCardUpcoming;
import com.example.login22_01_19_h1.sliderhome.CardHelper;
import com.example.login22_01_19_h1.sliderhome.adapterCard;

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

public class HomeFragment extends Fragment implements adapterCard.ListItemClickListener, adapterCardUpcoming.ListItemClickListenerReserv {

    @Nullable
    // Decleration Component And Variables
    Button addnewcar;
    Button MakeAppintment;
    Button RemoveCar;
    ArrayList<CardHelper> cards;
    TextView addcarmes, upcomingtext;

    RecyclerView carRecycler;
    RecyclerView.Adapter adapter;

    private ProgressDialog progressDialog;


    // Upcoming stuff
    RecyclerView UpcomingReserviation;
    RecyclerView.Adapter adapter2;
    ArrayList<UpcomingCardHelper> cardsUpcoming;

    ImageView imgtypeview;
    ArrayList<String> BookingID = new ArrayList<>();
    ArrayList<String> CarName = new ArrayList<>();
    ArrayList<String> CarImg = new ArrayList<>();
    ArrayList<String> MCenterName = new ArrayList<>();
    ArrayList<String> MCenterAddress = new ArrayList<>();
    ArrayList<String> MCenterMaps = new ArrayList<>();
    ArrayList<String> Date = new ArrayList<>();
    ArrayList<String> formatedDate = new ArrayList<>();
    ArrayList<Date> allDate = new ArrayList<>();
    ArrayList<String> Time = new ArrayList<>();

    ProgressDialog progress;


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //Decleration  For Component And link

        carRecycler = getView().findViewById(R.id.my_recycler);
        UpcomingReserviation = getView().findViewById(R.id.upcoming_recycler);
        progressDialog = new ProgressDialog(getActivity());
        addcarmes = getView().findViewById(R.id.addcarmes);
        MakeAppintment = getView().findViewById(R.id.Next);
        RemoveCar = getView().findViewById(R.id.remove);
        MakeAppintment.setVisibility(View.GONE);
        RemoveCar.setVisibility(View.GONE);
        upcomingtext = getView().findViewById(R.id.upcomingtext);
        getcars();
        getUpcomingReservation();
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Wait while Loading History...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void getcars() {
        //Make The Button UnVisible
        MakeAppintment.setVisibility(View.GONE);
        RemoveCar.setVisibility(View.GONE);

        //Request To PHP File For Read And Write In Database
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UserCARS_RETRIVE, new Response.Listener<String>() {

            @Override
            public void onResponse(String json_re) {
                progressDialog.dismiss();
                try {
                    JSONArray array = new JSONArray(json_re);
                    //cards.clear();
                    cards = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        //getting product object from json array
                        JSONObject jsonData = array.getJSONObject(i);
                        // Add The Data From Database To The Card Of RecyclerView
                        cards.add(new CardHelper(
                                jsonData.getString("CarID"),
                                jsonData.getString("OurCarID"),
                                jsonData.getString("CompanyID"),
                                jsonData.getString("CarType"),
                                jsonData.getString("CarName"),
                                jsonData.optString("CarImg")
                        ));
                    }
                    if (cards.isEmpty()) {
                        // you dont have any cars
                        addcarmes.setText("You Dont have any cars yet, you can add car by clicking on Add car");

                    } else {
                        // Activate The RecyclerView
                        adapter = new adapterCard(cards, HomeFragment.this);
                        carRecycler.setAdapter(adapter);
                        carRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                    }


                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                    //you dont have any card, add new crad
                    TextView addcarmes = getView().findViewById(R.id.addcarmes);
                    //Button add new car
                    addcarmes.setText("Oh it seems you didn't add any car yet , you can add new car from the plus sign");
                }
                progress.dismiss();
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
                System.out.println("Test Mes -------- edit database");
                Map<String, String> params = new HashMap<>();
                // Pass The User Id To Php File To Get Info  Related To Him
                params.put("UserID", String.valueOf(SharedPrefManger.getInstance(MainActivity.getAppContext()).getUserId()));
                return params;
            }
        };
        // - Put the Request in a RequestQueue usning singelton
        RequestHandlerSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        //Second requset for Our Car Data


        //if AddNew Car Clicked So Move The User To Related Fragment(Page)
        addnewcar = getView().findViewById(R.id.addnewcar);
        addnewcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCarFragment fragment = new AddCarFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }


    // Method For Showing Upcoming Reservations For The User
    private void getUpcomingReservation() {
        cardsUpcoming = new ArrayList<>();
        MCenterMaps.clear();
        allDate.clear();
        Date.clear();


        //Request To PHP File For Read And Write In Database
        StringRequest stringRequestUp = new StringRequest(Request.Method.POST, Constants.URL_HistoryFAST, new Response.Listener<String>() {

            public void onResponse(String json_result) {
                progressDialog.dismiss();
                try {
                    System.out.println(json_result.toString());
                    JSONArray array = new JSONArray(json_result);
                    for (int i = 0; i < array.length(); i++) {
                        //getting product object from json array
                        JSONObject jsonData = array.getJSONObject(i);
                        BookingID.add(jsonData.getString("BookingID"));
                        CarName.add(jsonData.getString("CarName"));
                        CarImg.add(jsonData.getString("CarImg"));
                        MCenterName.add(jsonData.getString("MCenterName"));
                        MCenterAddress.add(jsonData.getString("MCenterAddress"));
                        MCenterMaps.add(jsonData.getString("MCenterMaps"));
                        Date.add(jsonData.getString("Date"));
                        Time.add(jsonData.getString("Time"));
                    }


                    if (CarName.isEmpty()) {

                    } else {
                        // method to format the date
                        fortmatDate(Date, Time);

                        //formatedDate
                        Date date = new Date();
                        for (int i = 0; i < allDate.size(); i++) {
                            int result = allDate.get(i).compareTo(date);
                            System.out.println(" - " + allDate.get(i) + " - " + date + " - " + result);
                            if (result >= 0) {
                                // For User Navigate To Center Location Button
                                String link = "http://www.google.com/maps/place/";
                                link = link + MCenterMaps.get(i).replaceAll("\\s+", "");
                                // Add The Data From Database To The Card Of RecyclerView
                                cardsUpcoming.add(new UpcomingCardHelper(CarImg.get(i), Date.get(i), Time.get(i), MCenterAddress.get(i), CarName.get(i), MCenterName.get(i), link, R.drawable.ic_directions));
                            }
                        }
                        if (cardsUpcoming.isEmpty()) {
                            System.out.println("it is empty");
                        } else {
                            upcomingtext.setText("Upcoming Appintment");
                            // Activate The RecyclerView
                            adapter2 = new adapterCardUpcoming(getContext(), cardsUpcoming, HomeFragment.this);
                            UpcomingReserviation.setAdapter(adapter2);
                            UpcomingReserviation.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.println(Log.ASSERT, "HOME_URL_GET_USER_BOOKING_INFO_CENTERS :", "Error : " + e.toString());

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
//                        Toast.makeText(getContext().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Log.println(Log.ASSERT, "HOME_URL_GET_USER_BOOKING_INFO :", "onErrorResponser : " + error.toString());

            }

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // Pass The User Id To Php File To Get Info  Related To Him
                params.put("UserID", String.valueOf(SharedPrefManger.getInstance(getActivity()).getUserId()));
                return params;
            }
        };

        RequestHandlerSingleton.getInstance(getActivity()).addToRequestQueue(stringRequestUp);

    }


    @Override
    public void onphoneListClick(int clickedItemIndex, String s, ArrayList<CardHelper> Cardinfo) {

        // Getting The Info About The Card User Click on it
        String CompanyID = Cardinfo.get(clickedItemIndex).getmCompanyID();
        String CarID = Cardinfo.get(clickedItemIndex).getCarID();

        // To Pass Info And Move To Centers Fragment
        MakeAppintment.setVisibility(View.VISIBLE);
        MakeAppintment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                CentersFragment fragment = new CentersFragment();
                Bundle bundle = new Bundle();
                bundle.putString("CompanyID", CompanyID);
                bundle.putString("CarID", CarID);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

        // if Removing The Car Button Pressed
        RemoveCar.setVisibility(View.VISIBLE);
        RemoveCar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProgressDialog progress = new ProgressDialog(getActivity());
                progress.setTitle("Loading");
                progress.setMessage("Wait while Removing Car...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();

                //Request To PHP File For Read And Write In Database
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Remove_Car, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Car Removed")) {
                            Toast.makeText(getContext(), "Car Removed!", Toast.LENGTH_LONG).show();
                            // Reload the page
                            HomeFragment fragment = new HomeFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            progress.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @androidx.annotation.Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("CarID", CarID);
                        return params;
                    }
                };
                // - Put the Request in a RequestQueue usning singelton
                RequestHandlerSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

            }
        });

    }


    @Override
    public void onViewListClick(int clickedItemIndex, String title) {

    }

    // method get 2 string array list ( Date , Time ) to make Date and save it in (allDate) list
    public void fortmatDate(ArrayList<String> Date, ArrayList<String> Time) {

        for (int i = 0; i < Date.size(); i++) {



            String DateForConvert = Date.get(i);
            String TimeForConvert = Time.get(i);

            switch (TimeForConvert) {
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
            DateForConvert = DateForConvert + " " + TimeForConvert + ":00";
            formatedDate.add(DateForConvert);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dt = sdf.parse(DateForConvert);
                allDate.add(dt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}