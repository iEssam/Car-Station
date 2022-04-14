package com.example.login22_01_19_h1.SpinnerCompany;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.login22_01_19_h1.R;

import java.util.ArrayList;

public class CompanyItemAdapter extends ArrayAdapter<RowItem> {

        public CompanyItemAdapter(Context context, ArrayList<RowItem> countryList) {
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
                        R.layout.spinner_item_row, parent, false
                );
            }

            ImageView imageViewBrand = convertView.findViewById(R.id.image_view_brand);
            TextView textViewName = convertView.findViewById(R.id.text_view_name);

            RowItem currentItem = getItem(position);

            if (currentItem != null) {
                imageViewBrand.setImageBitmap(currentItem.getBrandImage());
                textViewName.setText(currentItem.getCompanyName());
            }

            return convertView;
        }
    }

