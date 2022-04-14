package com.example.login22_01_19_h1.sliderhome;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class CardHelper {

    private String CarID;
    private String OurCarID;
    private String mCompanyID;
    private String CarType;
    private String CarName;
    private Bitmap mCarImage;

    public CardHelper(String CarID,String ourCarID, String companyBrandName , String cartype, String carname, String carImage) {
        this.CarID = CarID;
        this.OurCarID = ourCarID;
        this.mCompanyID = companyBrandName;
        this.CarType = cartype;
        this.CarName = carname;
        this.mCarImage = StringToBitMap(carImage);
    }

    public CardHelper(String companyBrandName) {
        mCompanyID = companyBrandName;
    }

    public String getCarID() {
        return CarID;
    }

    public void setCarID(String carID) {
        CarID = carID;
    }
    public String getOurCarID() {
        return OurCarID;
    }

    public void setOurCarID(String ourCarID) {
        OurCarID = ourCarID;
    }

    public String getmCompanyID() {
        return mCompanyID;
    }

    public void setmCompanyID(String mCompanyID) {
        this.mCompanyID = mCompanyID;
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

    public Bitmap getmCarImage() {
        return mCarImage;
    }

    public void setmCarImage(Bitmap mCarImage) {
        this.mCarImage = mCarImage;
    }



    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

}