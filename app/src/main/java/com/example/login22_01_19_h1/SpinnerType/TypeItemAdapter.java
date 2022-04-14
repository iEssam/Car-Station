package com.example.login22_01_19_h1.SpinnerType;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.login22_01_19_h1.R;

import java.util.ArrayList;

public class TypeItemAdapter extends ArrayAdapter<RowItemType> {

        public TypeItemAdapter(Context context, ArrayList<RowItemType> countryList) {
            super(context, 0, countryList);
        }



    @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        private View initView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.spinner_type_item_row, parent, false
                );
            }

            TextView textViewName = convertView.findViewById(R.id.text_view_name_Type_Spinner);

            RowItemType currentItem = getItem(position);

            if (currentItem != null) {
                //imageViewBrand.setImageResource(currentItem.getTypeImage());
                textViewName.setText(currentItem.getTypeName());
            }

            return convertView;
        }
    }

