package com.example.login22_01_19_h1.SliderUpcoming_Resrv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class UpcomingCardHelper {
    int image;
    int Center_Link_history;
    Bitmap mCarImage;
    String Date;
    String Time;
    String Location;
    String CarType ;
    String CarName;
    String MCenterName;
    String Urls;

    public UpcomingCardHelper(String mCarImage, String date, String time, String location, String carName, String MCenterName, String urls, int Center_Link_history) {
        Date = date;
        Time = time;
        Location = location;
        CarName = carName;
        this.MCenterName = MCenterName;
        Urls = urls;
        this.mCarImage = StringToBitMap(mCarImage);
        this.Center_Link_history = Center_Link_history;
    }

    public int getCenter_Link_history() {
        return Center_Link_history;
    }

    public void setCenter_Link_history(int center_Link_history) {
        Center_Link_history = center_Link_history;
    }

    public Bitmap getmCarImage() {
        return mCarImage;
    }

    public void setmCarImage(Bitmap mCarImage) {
        this.mCarImage = mCarImage;
    }

    public String getUrls() {
        return Urls;
    }

    public void setUrls(String urls) {
        Urls = urls;
    }
    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }

    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }

    public String getMCenterName() {
        return MCenterName;
    }

    public void setMCenterName(String MCenterName) {
        this.MCenterName = MCenterName;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public UpcomingCardHelper() {
    }

    public UpcomingCardHelper(String Date) {
        this.Date = Date;
    }

    public int getImage() {
        return image;
    }


    public void setImage(int image) {
        this.image = image;
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }  catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}

