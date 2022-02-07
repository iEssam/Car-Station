package com.example.login22_01_19_h1.Menu;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login22_01_19_h1.DBHelper;
import com.example.login22_01_19_h1.LoginSingup.Login;
import com.example.login22_01_19_h1.SpinnerCar.CarItemAdapterSecond;
import com.example.login22_01_19_h1.SpinnerCar.CarRowItem;
import com.example.login22_01_19_h1.SpinnerCompany.CompanyItemAdapter;
//import com.example.login22_01_19_h1.MainActivitySpinner;
import com.example.login22_01_19_h1.R;
import com.example.login22_01_19_h1.SpinnerCompany.RowItem;
import com.example.login22_01_19_h1.SpinnerType.RowItemType;
import com.example.login22_01_19_h1.SpinnerType.TypeItemAdapter;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    private ArrayList<RowItem> mCompanyBrandList;
    private CompanyItemAdapter mAdapter;

    private ArrayList<RowItemType> mCarTypeList;
    private TypeItemAdapter mTypeAdapter;


    String spinTextT = "";

    String typecar = "";
    String Company = "";
    public String car = "";
    static int  counterCarId=3;
    DBHelper dbHelper;

    private ArrayList<CarRowItem> mToyotaCar;
    private ArrayList<CarRowItem> mFordCar;
    private ArrayList<CarRowItem> mHyundaiCar;
    private ArrayList<CarRowItem> mMercedesCar;

    private CarItemAdapterSecond mAdapterSecond;
    ////

    ///


//    private boolean clickedItemCompanyName1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.activity_spinner_main, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initList();
        mCarTypeList = new ArrayList<>();
        mCarTypeList.add(new RowItemType("Sedan"));
        mCarTypeList.add(new RowItemType("SUV"));


        Spinner spinnerComapnires = (Spinner) getView().findViewById(R.id.spinner_comapnires);
        //---

        Spinner spinnerCar = (Spinner) getView().findViewById(R.id.spinner_car);
        Spinner spinnerType = (Spinner) getView().findViewById(R.id.spinner_type);
        TextView textViewType = (TextView) getView().findViewById(R.id.text_view_type);
        TextView textViewCompany = (TextView) getView().findViewById(R.id.text_view_company);
        TextView textViewCar = (TextView) getView().findViewById(R.id.text_view_car);
        //Type Spinner
        mTypeAdapter = new TypeItemAdapter(getActivity(), mCarTypeList);
        spinnerType.setAdapter(mTypeAdapter);

        //Company Spinner
        mAdapter = new CompanyItemAdapter(getActivity(), mCompanyBrandList);
        spinnerComapnires.setAdapter(mAdapter);

        mFordCar = new ArrayList<>();
        mFordCar.add(new CarRowItem("EDGE"));
        mFordCar.add(new CarRowItem("EXPEDITION"));
        mFordCar.add(new CarRowItem("EXPLORER" , R.drawable.explorer));
        mFordCar.add(new CarRowItem("F-SERIES"));
        mFordCar.add(new CarRowItem("Fusion", R.drawable.fusion));


        mHyundaiCar = new ArrayList<>();

        mHyundaiCar.add(new CarRowItem("Sonata", R.drawable.sonata));
        mHyundaiCar.add(new CarRowItem("Elantra" , R.drawable.elantra));
        mHyundaiCar.add(new CarRowItem("Accent", R.drawable.accent));
        mHyundaiCar.add(new CarRowItem("Santa fe" , R.drawable.santa_fe));

        mMercedesCar = new ArrayList<>();
        mMercedesCar.add(new CarRowItem("Mercedes-Benz GLA"));
//        mMercedesCar.add(new CarRowItem("Mercedes-Benz E-Class"));
        mMercedesCar.add(new CarRowItem("Mercedes-Benz S-Class" , R.drawable.s_class));
        mMercedesCar.add(new CarRowItem("Mercedes-Benz AMG G 63", R.drawable.a_class));
        mMercedesCar.add(new CarRowItem("Mercedes-Benz G-Class" , R.drawable.g_class));


        mToyotaCar = new ArrayList<>();
        mToyotaCar.add(new CarRowItem("Avalon"));
        mToyotaCar.add(new CarRowItem("Camry", R.drawable.camry));
        mToyotaCar.add(new CarRowItem("Corolla" , R.drawable.corolla));
        mToyotaCar.add(new CarRowItem("Yaris" , R.drawable.yaris));


        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RowItemType clickedItem = (RowItemType) parent.getItemAtPosition(position);
                String clickedItemType = (clickedItem.getTypeName());
                typecar = clickedItemType;

//                if (position == 0) {
//                    mAdapterSecond = new CarItemAdapterSecond(getActivity(), mFordCar);
//                    typecar = "Ford";
//
//
//                }
//                if (position == 1) {
//                    mAdapterSecond = new CarItemAdapterSecond(getActivity(), mHyundaiCar);
//                    typecar = "Hyundai";
//
//                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        spinnerComapnires.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RowItem clickedItem = (RowItem) parent.getItemAtPosition(position);
                String clickedItemCompanyNameS = (clickedItem.getCompanyName());
                if (position == 0) {
                    mAdapterSecond = new CarItemAdapterSecond(getActivity(), mFordCar);
                    Company = "Ford";
//                    textViewResult.setText(car);
//        String spinText = (String) spinnerComapnires.getSelectedItem();

//                    textViewResult.setText(car+" ");
//                     spinTextT = textViewResult.getText().toString();

                }
                if (position == 1) {
                    mAdapterSecond = new CarItemAdapterSecond(getActivity(), mHyundaiCar);
                    Company = "Hyundai";
//                    textViewResult.setText(car+" ");
//                    textViewResult.setText(car);
//                     spinTextT = textViewResult.getText().toString();
                }
                if (position == 2) {
                    mAdapterSecond = new CarItemAdapterSecond(getActivity(), mMercedesCar);
                    Company = "Mercedes";
//                    textViewResult.setText(car+" ");
//                    textViewResult.setText(car);
//                     spinTextT = textViewResult.getText().toString();
                }
                if (position == 3) {
                    mAdapterSecond = new CarItemAdapterSecond(getActivity(), mToyotaCar);
                    Company = "Toyota";
//                    textViewResult.setText(car+" ");
//
//                     spinTextT = textViewResult.getText().toString();
                }

                spinnerCar.setAdapter(mAdapterSecond);
//                Toast.makeText(getActivity(), clickedItemCompanyNameS + " selected", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        spinnerCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CarRowItem clickedItem = (CarRowItem) parent.getItemAtPosition(position);
                String clickedCarName = clickedItem.getCarName();
                car = clickedCarName;
                Toast.makeText(getActivity(), clickedCarName + " selected", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//                Toast.makeText(getActivity(), spinnerComapnires.getSelectedItemPosition() + " : Final", Toast.LENGTH_SHORT).show();
//        TextView textView = (TextView) spinnerComapnires.getSelectedView();
//        String result = textView.getText().toString();
//
//        String spinTextT = textViewResult.getText().toString();
//        textViewGoal.setText(spinnerComapnires.getSelectedItem().toString());


        Button bt = getActivity().findViewById(R.id.ButtonSpinnerPage);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textViewType.setText(typecar + "");
                textViewCompany.setText(Company + "");
                textViewCar.setText(car + "");
                int tp  =5;
                if (typecar.equalsIgnoreCase("Sedan")) {
                tp =0;
                }    if (typecar.equalsIgnoreCase("SUV")) {
                    tp=1;
                }
                dbHelper = new DBHelper(getActivity());
                if (bt.isPressed()) {
//                        boolean b = dbHelper.insetCarDataTest(car, typecar, Company, 100);

                        boolean b = dbHelper.insetCarData(car, tp, ++counterCarId);
                        textViewCar.setText("Done" + "");

                }

//                Intent intent1 = new Intent(Login.this, Navigation_Main.class);
//                startActivity(intent1);
//                    intent.putExtra("email",emailCheck);
//                    email.setText("");
//                    password.setText("");
//                Toast.makeText(getActivity(), spinnerComapnires.getSelectedItem().toString(), Toast.LENGTH_SHORT);
            }
        });

//        Toast.makeText(getActivity(), "The Result String : "+Result, Toast.LENGTH_LONG).show();

        //        getSelectedItemPosition()

//        onSaveInstanceState()
    }


    private void initList() {
        mCompanyBrandList = new ArrayList<>();
        mCompanyBrandList.add(new RowItem("Ford", R.drawable.ic_ford_logo_foreground));
        mCompanyBrandList.add(new RowItem("Hyundai", R.drawable.hundai2_logo));
        mCompanyBrandList.add(new RowItem("Mercedes", R.drawable.mercedes_logo_sec));
        mCompanyBrandList.add(new RowItem("Toyota", R.drawable.toyota_logo));
    }


    private void initListCar() {

    }

    private void carSpinner() {

//        System.out.println("The company is "+ clickedItemCompanyName);
//        initListCar();


//        spinnerCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                RowItemSecond clickedItem = (RowItemSecond) parent.getItemAtPosition(position);
//                String clickedCarName = clickedItem.getCarName();
//                Toast.makeText(getActivity(), clickedCarName + " selected", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }
}