package com.example.login22_01_19_h1.Menu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.example.login22_01_19_h1.R;
import com.example.login22_01_19_h1.RequestHandlerSingleton;
import com.example.login22_01_19_h1.SharedPrefManger;
import com.example.login22_01_19_h1.SliderHistory.ReservsationHelper;
import com.example.login22_01_19_h1.SliderHistory.adapterCardHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//public class HistoryFragment extends Fragment  {
public class HistoryFragment extends Fragment implements adapterCardHistory.ListItemClickListenerReserv {

    @org.jetbrains.annotations.Nullable

    Button DelteAppointment;

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
    TextView none;
    String BookingIDString ;


    RecyclerView ReservRecycler;
    RecyclerView.Adapter adapter;


    public void onViewCreated(View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //Decleration  For Component And link
        ReservRecycler = getView().findViewById(R.id.my_recycler_history);
        imgtypeview = getView().findViewById(R.id.car_image);
        none = getView().findViewById(R.id.none);
        // Make The Delete Button Unvisible
        DelteAppointment = getView().findViewById(R.id.DeleteReservation);
        DelteAppointment.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(getActivity());
        ReservRecycler();

    }

    private ProgressDialog progressDialog;
    ArrayList<ReservsationHelper> cards = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_card_history, container, false);
    }

    private void ReservRecycler() {
        // Showing Progress Dialog While Loading The Page Data
        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Wait while Loading History...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        cards.clear();

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
                        BookingIDString=jsonData.getString("BookingID");
                        BookingID.add(jsonData.getString("BookingID"));
                        CarName.add(jsonData.getString("CarName"));
                        CarImg.add(jsonData.getString("CarImg"));
                        MCenterName.add(jsonData.getString("MCenterName"));
                        MCenterAddress.add(jsonData.getString("MCenterAddress"));
                        MCenterMaps.add(jsonData.getString("MCenterMaps"));
                        Date.add(jsonData.getString("Date"));
                        Time.add(jsonData.getString("Time"));
                    }

                    if (CarName.isEmpty()){
                        none.setText("You dont have any Appointments yet , You can make an Appointment from home page.");
                    }else {
                        // method to format the date
                        fortmatDate(Date,Time);
                        //formatedDate
                        java.util.Date date = new Date();

                        for (int i=0 ; i<allDate.size() ; i++){
                            String link = "http://www.google.com/maps/place/";
                            link = link+MCenterMaps.get(i).replaceAll("\\s+","");

                            // Add The Data From Database To The Card Of RecyclerView
                            cards.add(new ReservsationHelper(CarImg.get(i),  Date.get(i), Time.get(i),MCenterAddress.get(i)  ,CarName.get(i) ,MCenterName.get(i) ,link, R.drawable.ic_directions, BookingID.get(i) ));

                        }
                        if (cards.isEmpty()){
                            System.out.println("it is empty");

                        }else {
                            // Activate The RecyclerView
                            adapter = new adapterCardHistory(getContext(),cards, HistoryFragment.this);
                            ReservRecycler.setAdapter(adapter);
                            ReservRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                        }


                    }
                    progress.dismiss();

                } catch (JSONException e) {
                    progress.dismiss();
                    e.printStackTrace();
                    Log.println(Log.ASSERT, "HOME_URL_GET_USER_BOOKING_INFO_CENTERS :", "Error : " + e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Log.println(Log.ASSERT, "HOME_URL_GET_USER_BOOKING_INFO :", "onErrorResponser : " + error.toString());

            }
        })
        {
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // Pass The User Id To Php File To Get Info  Related To Him
                params.put("UserID", String.valueOf(SharedPrefManger.getInstance(getActivity()).getUserId()));
                return params;
            }
        };
        //Activate Request
        RequestHandlerSingleton.getInstance(getActivity()).addToRequestQueue(stringRequestUp);
    }


    @Override
    public void onViewListClick(int clickedItemIndex, String title) {
        // Getting The Info About The Card User Click on it
        String CarName = cards.get(clickedItemIndex).getCarName();
        String CarType = cards.get(clickedItemIndex).getCarType();
        String CenterName = cards.get(clickedItemIndex).getMCenterName();
        String Date = cards.get(clickedItemIndex).getDate();
        String Location = cards.get(clickedItemIndex).getLocation();
        String Time = cards.get(clickedItemIndex).getTime();
        String BookingIDS = cards.get(clickedItemIndex).getBookingID();
        int image = cards.get(clickedItemIndex).getImage();

        // When Clicked in the One Of the History cards The Delete Button Will Be Visilbe
        DelteAppointment.setVisibility(View.VISIBLE);
        DelteAppointment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Request To PHP File For Delete
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_CANCEL_APPOINTMENT, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String json_re) {

                        Toast.makeText(getContext(), "Appointment Is Deleted Secssecfuly ", Toast.LENGTH_LONG).show();
                        HistoryFragment fragment = new HistoryFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
//
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                    progressDialog.hide();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }) {
                    @androidx.annotation.Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        System.out.println("Test Mes -------- edit database");
                        Map<String, String> params = new HashMap<>();
                        // For Delete The Reservation With The It's Id
                        params.put("BookingID", BookingIDS);
                        return params;
                    }
                };
                // Activate The Request
                RequestHandlerSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

            }
        });




    }

    // method get 2 string array list ( Date , Time ) to make Date and save it in (allDate) list
    public void fortmatDate(ArrayList<String> Date,ArrayList<String> Time ) {

        for (int i =0 ; i <Date.size() ; i++){
            String DateForConvert = Date.get(i);
            String TimeForConvert = Time.get(i);

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
            DateForConvert = DateForConvert +" " +TimeForConvert+":00";
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