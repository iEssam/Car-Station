package com.example.login22_01_19_h1;

public class Constants {
    //our database
    public static final String ROOT_URL = "http://192.168.1.196/android/dataru/";

    //Signup and login :
    public static final String URL_REGISTER = ROOT_URL + "SignUp.php";
    public static final String URL_LOGIN = ROOT_URL + "Login.php";

    // For MainActivityNotification
    public static final String URL_RESERVATION_HISTORY = ROOT_URL + "ReservationHistory.php";

    // Making an Appointment proccess :
    public static final String URL_Retrive_Centers = ROOT_URL + "Centers.php"; // geting centers info
    public static final String URL_SCHEDULE_RETRIVE = ROOT_URL + "ScheduleOfMaint.php"; // get Schedule Of selected Center
    public static final String URL_SCHEDULE_Update = ROOT_URL + "InsertAppointment.php"; // insert Appointment

    // For insert car page :
    public static final String URL_CompanyCars_RETRIVE = ROOT_URL + "GetCompanyInfo.php";
    public static final String URL_CARS_RETRIVE = ROOT_URL + "GetCarInfo.php";
    public static final String URL_USERCARS = ROOT_URL + "InsertUserCars.php"; // insert the car

    //Home Page
    public static final String URL_UserCARS_RETRIVE = ROOT_URL + "GetUsersCar.php";
    public static final String URL_Remove_Car = ROOT_URL + "RemoveCar.php";

    //History Page
    public static final String URL_GET_USER_BOOKING_INFO_DATES = ROOT_URL + "GetBookingHistory_Date.php";
    public static final String URL_CANCEL_APPOINTMENT = ROOT_URL + "CancelAppointment.php";

    //Profile Page
    public static final String URL_RESER_PASSWORD = ROOT_URL + "ResetPassword.php";

    //Essam Testing
    public static final String URL_HistoryFAST = ROOT_URL + "HistoryFAST.php";

}
