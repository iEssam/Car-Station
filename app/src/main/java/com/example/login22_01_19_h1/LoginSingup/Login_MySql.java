package com.example.login22_01_19_h1.LoginSingup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.login22_01_19_h1.Constants;
import com.example.login22_01_19_h1.MainActivity;
import com.example.login22_01_19_h1.Menu.Navigation_Main;
import com.example.login22_01_19_h1.R;
import com.example.login22_01_19_h1.RequestHandlerSingleton;
import com.example.login22_01_19_h1.SharedPrefManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login_MySql extends AppCompatActivity implements View.OnClickListener {
    //component inital  Declaeration
    EditText email, password;
    Button btnSubmit;
    TextView createAcc;
    ImageView logo;
    Drawable myDrawable;

    private ProgressDialog progressDialog;


    String emailCheck ;
    String passCheck ;

    String encrypt="";
    String temp2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Check if the user is logged in and pass them to the Main Page
        if(SharedPrefManger.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this , Navigation_Main.class));
            return;
        }


        // component Declaeration and link
        email = findViewById(R.id.text_email);
        password = findViewById(R.id.text_pass);
        btnSubmit = findViewById(R.id.btnSubmit_login);
        createAcc = findViewById(R.id.createAcc);
        logo = findViewById(R.id.logo);

        // Setting up the ClickListener
        createAcc.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        // Setting Image logo For Login Page
        myDrawable = getResources().getDrawable(R.drawable.car_logo);
        logo.setImageDrawable(myDrawable);


    }

    private void LoginUSer() {


        // Getting User Inputs
        emailCheck = email.getText().toString();
        passCheck = password.getText().toString();
        boolean isValidEmail = isValidEmail(emailCheck);


//        try {
//            SecretKey secret = generateKey("7x!A%D*G-KaPdSgVkYp2s5v8y/B?E(H+");
//            byte [] en = encryptMsg(passCheck, secret);
//            encrypt = new String(en);
//            temp2 = new String(decryptMsg(en, secret));
//            System.out.println("pass : "+passCheck);
//            System.out.println("encrypt : "+encrypt);
//            System.out.println("decript : "+temp2);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        }


        // Check if The User Input ٍData And it's Valid
        if (isValidEmail && !emailCheck.isEmpty() && !passCheck.isEmpty()) {

            // dialog appear
            progressDialog.setMessage("REGISTER IS IN PROCCISING ");
            progressDialog.show();

            //Request To PHP File For Read And Write In Database
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        System.out.println(response);
                        JSONObject jsonObject = new JSONObject(response); // read From Response Decleard in PhP
                        if (jsonObject.getString("message").equalsIgnoreCase("Login Success")) {


                            SharedPrefManger.getInstance(MainActivity.getAppContext()).userLogin(  jsonObject.getString("id"), jsonObject.getString("username"),
                                    jsonObject.getString("email"));



                            Toast.makeText(MainActivity.getAppContext(), "You are Logeed in successfully", Toast.LENGTH_LONG).show();
                            //Pass it to Next Page
                            Intent intent1 = new Intent(Login_MySql.this, Navigation_Main.class);
                            startActivity(intent1);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.getAppContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();
                    Toast.makeText(MainActivity.getAppContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                }

            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    // Input Data For Check password and email it's in Already Registerd
                    params.put("password", passCheck);
                    params.put("email", emailCheck);
                    return params;
                }
            };

            // Activate the Request
            RequestHandlerSingleton.getInstance(this).addToRequestQueue(stringRequest);

        }else {
            // if it's Emptey Show  Toast Msg
            if (emailCheck.equalsIgnoreCase("") || passCheck.equalsIgnoreCase("")){
                Toast.makeText(getApplicationContext(), "Please fill up your email and password", Toast.LENGTH_LONG).show();
            }else if(false == isValidEmail) { //if it's Not Valid Show  Toast Msg
                Toast.makeText(getApplicationContext(), "This Email is not valid", Toast.LENGTH_LONG).show();
            }
        }


    }

    public void onClick(View view) {
        if (view == btnSubmit) {
            LoginUSer(); // Move To The Method When Click In Button Submit
        }else if (view == createAcc) {
//            Move To The Sign Up Page When Click In Button Submit
            Intent intent = new Intent(Login_MySql.this, signUp_mysqlphp.class);
            startActivity(intent);
        }


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

//    public static SecretKey generateKey(String password)
//            throws NoSuchAlgorithmException
//    {
//        SecretKeySpec secret = new SecretKeySpec(password.getBytes(), "AES");
//        return secret ;
//    }
//
//    public static byte[] encryptMsg(String message, SecretKey secret)
//            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
//    {
//        /* Encrypt the message. */
//        Cipher cipher = null;
//        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, secret);
//        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
//        return cipherText;
//    }
//
//    public static String decryptMsg(byte[] cipherText, SecretKey secret)
//            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException
//    {
//        /* Decrypt the message, given derived encContentValues and initialization vector. */
//        Cipher cipher = null;
//        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, secret);
//        String decryptString = new String(cipher.doFinal(cipherText), "UTF-8");
//        return decryptString;
//    }

}