package com.example.login22_01_19_h1.Menu;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.*;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.login22_01_19_h1.Constants;
import com.example.login22_01_19_h1.MainActivity;
import com.example.login22_01_19_h1.MainActivityOcr;
import com.example.login22_01_19_h1.RequestHandlerSingleton;
import com.example.login22_01_19_h1.SharedPrefManger;
import com.example.login22_01_19_h1.SpinnerCar.CarItemAdapterSecond;
import com.example.login22_01_19_h1.SpinnerCar.CarRowItem;
import com.example.login22_01_19_h1.SpinnerCompany.CompanyItemAdapter;
import com.example.login22_01_19_h1.R;
import com.example.login22_01_19_h1.SpinnerCompany.RowItem;
import com.example.login22_01_19_h1.SpinnerType.RowItemType;
import com.example.login22_01_19_h1.SpinnerType.TypeItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;


public class AddCarFragment extends Fragment {

    // Decleration Component And Variables

    private ArrayList<RowItem> mCompanyBrandList;
    private CompanyItemAdapter mAdapter;

    private ArrayList<CarRowItem> mCars;
    private CarItemAdapterSecond mAdapterSecond;

    private TypeItemAdapter mTypeAdapter;


    Spinner spinnerComapnires;
    Spinner spinnerCar;
    Spinner spinnerType;

    ArrayList<CarRowItem> filtered;

    Button addCar;

    String UserID;
    String OurCarID;
    String VIDString;


    public String car = "";




    TextView OcrUsing;
    EditText OcrEditText;

    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.activity_spinner_main, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());

        //Decleration  For Component And link it
        spinnerComapnires = (Spinner) getView().findViewById(R.id.spinner_comapnires);
        spinnerCar = (Spinner) getView().findViewById(R.id.spinner_car);
        spinnerType = (Spinner) getView().findViewById(R.id.spinner_type);
        filtered = new ArrayList<>();

        addCar = getView().findViewById(R.id.ButtonSpinnerPage);
        addCar.setVisibility(View.GONE);
        addCar.setBackground(getActivity().getResources().getDrawable(R.drawable.buttondesign));



        OcrUsing = getView().findViewById(R.id.UseOCr);
        OcrEditText = getView().findViewById(R.id.VID_ET);


        // For Retrive Data From OCR Page About VID
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            VIDString = bundle.getString("VID_Readed");

            Toast.makeText(getActivity(), "The Result You Got : \n"+VIDString, Toast.LENGTH_SHORT).show();
            OcrEditText.setText(VIDString);

        }

        // For Move To The Ocr Page
        OcrUsing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intet= new Intent(getActivity(), MainActivityOcr.class);
                startActivity(intet);
                Bundle b = new Bundle();
                String name = b.getString("name");
            }
        });


        // 1- Get cars data from DataBase
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_CompanyCars_RETRIVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String json_re) {
                progressDialog.dismiss();

                try {
                    JSONArray array = new JSONArray(json_re);
                    mCompanyBrandList = new ArrayList<>();
                    mCompanyBrandList.add(new RowItem(null,"Select Company",null));

                    for (int i = 0; i < array.length(); i++) {
                        //getting product object from json array
                        JSONObject jsonData = array.getJSONObject(i);
                        //adding the product to product list
                        mCompanyBrandList.add(new RowItem(
                                jsonData.getString("CompanyID"),
                                jsonData.getString("CompanyName"),
                                jsonData.optString("Logo")
                        ));
                    }

                    showCompanySpinner();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
//                        Toast.makeText(getContext().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }

        }) {

        };

        RequestHandlerSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


        ///////////////////////////////////////////////////////////////////////////////////

        // 1- Get cars data from DataBase
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_CARS_RETRIVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String json_re) {
                System.out.println(json_re);
                progressDialog.dismiss();
                //System.out.println("---------------- response :" + json_re);

                try {
                    JSONArray array = new JSONArray(json_re);
                    mCars = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        //getting product object from json array
                        JSONObject jsonData = array.getJSONObject(i);
                        //adding the product to product list
                        mCars.add(new CarRowItem(
                                jsonData.getString("OurCarID"),
                                jsonData.getString("CompanyID"),
                                jsonData.getString("CarType"),
                                jsonData.getString("CarName"),
                                jsonData.optString("CarImg")
                        ));
                    }
                    System.out.println("len = "+mCars.size());
//                    mAdapterSecond = new CarItemAdapterSecond(getActivity(), mCars);
//                    spinnerCar.setAdapter(mAdapterSecond);


                    spinnerComapnires.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            RowItem clickedItem = (RowItem) parent.getItemAtPosition(position);
                            String clickedItemCompanyNameS = (clickedItem.getCompanyName());

                            filtered = FilterCompany(mCompanyBrandList,mCars , clickedItem.getCompanyName());
                            mAdapterSecond = new CarItemAdapterSecond(getActivity(), filtered);
                            spinnerCar.setAdapter(mAdapterSecond);

                            showTypeSpinner();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            RowItemType clickedItem = (RowItemType) parent.getItemAtPosition(position);
                            ArrayList<CarRowItem> filteredtype = FilterType(filtered , clickedItem.getTypeName());
                            mAdapterSecond = new CarItemAdapterSecond(getActivity(), filteredtype);
                            spinnerCar.setAdapter(mAdapterSecond);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    spinnerCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            CarRowItem clickedItem = (CarRowItem) parent.getItemAtPosition(position);
                            UserID = SharedPrefManger.getInstance(MainActivity.getAppContext()).getUserId();
                            OurCarID = clickedItem.getOurCarID();
                            addCar.setVisibility(View.VISIBLE);

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
//                        Toast.makeText(getContext().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }

        }) {

        };

        RequestHandlerSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest2);

        addCar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_USERCARS, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.contains("Your Car Inforamation Saved successfully")) {
                            System.out.println("--------------------Done -----------");
                            Toast.makeText(getContext(), "Your Car Inforamation Saved successfully!", Toast.LENGTH_LONG).show();
                            //take him to next page
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.fragment_container,
                                    new HomeFragment()).commit();
                        } else {
                            System.out.println("--------------------Full sorry -----------");
                            Toast.makeText(getContext(), "Failed To Save Your Car Inforamation", Toast.LENGTH_LONG).show();
//                            refresh();
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
                        System.out.println("Test Mes -------- edit database");
                        Map<String, String> params = new HashMap<>();
                        params.put("UserID", String.valueOf(UserID));
                        params.put("OurCarID", OurCarID );
                        return params;
                    }
                };

                RequestHandlerSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


            }
        });

    }
    public void showCompanySpinner ( ) {
        mAdapter = new CompanyItemAdapter(getActivity(), mCompanyBrandList);
        spinnerComapnires = (Spinner) getView().findViewById(R.id.spinner_comapnires);
        spinnerComapnires.setAdapter(mAdapter);
    }
    public void showTypeSpinner ( ) {
        ArrayList<RowItemType> types = findAllTypes(filtered);
        mTypeAdapter = new TypeItemAdapter(getActivity(), types);
        spinnerType.setAdapter(mTypeAdapter);
    }

    public ArrayList<CarRowItem> FilterType ( ArrayList <CarRowItem> filteredcar , String type ) {
        ArrayList<CarRowItem> filtered = new ArrayList<>();
        for (int counter = 0; counter < filteredcar.size(); counter++) {
            if (filteredcar.get(counter).getCarType().equalsIgnoreCase(type)){
                filtered.add(filteredcar.get(counter));
            }
        }
        return filtered;
    }
    public ArrayList<CarRowItem> FilterCompany ( ArrayList <RowItem> comp ,ArrayList <CarRowItem> allcars , String Company ) {
        String compID="";
        for (int counter = 0; counter < comp.size(); counter++) {

            if (comp.get(counter).getCompanyName().equalsIgnoreCase(Company)){
                compID= comp.get(counter).getmCompanyID();
                break;
            }
        }
        System.out.println("The sent comp : "+Company);
        ArrayList<CarRowItem> filtered = new ArrayList<>();
        for (int counter = 0; counter < allcars.size(); counter++) {
            System.out.println("the brand name in list : "+allcars.get(counter).getmCompanyID());
            if (allcars.get(counter).getmCompanyID().equalsIgnoreCase(compID)){
                System.out.println("match");
                filtered.add(allcars.get(counter));
            }
        }
        return filtered;
    }

    public ArrayList<RowItemType> findAllTypes ( ArrayList <CarRowItem> allcars ) {
        ArrayList<RowItemType> types = new ArrayList<>();

        for (int counter = 0; counter < allcars.size(); counter++) {
            boolean found = false;
            if (counter==0){
                types.add(new RowItemType(allcars.get(counter).getCarType()));
            }
            for (int i =0 ; i< types.size() ; i++) {
                if (types.get(i).getTypeName().equalsIgnoreCase(allcars.get(counter).getCarType())){
                    found = true;
                    break;
                }
            }

            if(found==false){
                types.add(new RowItemType(allcars.get(counter).getCarType()));
            }
        }
        return types;
    }

}
