package com.example.login22_01_19_h1.LoginSingup;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.login22_01_19_h1.Constants;
import com.example.login22_01_19_h1.R;
import com.example.login22_01_19_h1.RequestHandlerSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class signUp_mysqlphp extends AppCompatActivity implements View.OnClickListener {
    //component inital  Declaeration
    EditText EditTextName, EditTextUserName , EditTextNumber, EditTextEmail, EditTextPass;

    TextView loginAcc,PasswordStrength;
    Button BttonSignUpAcc;
    ImageView logo;
    Drawable myDrawable;
    private ProgressDialog progressDialog;
    String encrypt="";
    String temp2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // component Declaeration and link

        setContentView(R.layout.activity_sign_up);
        EditTextName = findViewById(R.id.textName);
        EditTextUserName = findViewById(R.id.username);
        EditTextNumber = findViewById(R.id.textNumber);
        EditTextEmail = findViewById(R.id.textEmail);
        EditTextPass = findViewById(R.id.textPass);
        PasswordStrength=(TextView)findViewById(R.id.Passwrdstrenth);
        EditTextPass.addTextChangedListener(passStrenth);
        progressDialog = new ProgressDialog(this);

        BttonSignUpAcc = findViewById(R.id.btnSignUpAcc);
        loginAcc = findViewById(R.id.loginAcc);

        // Listener Seeiting Up
        BttonSignUpAcc.setOnClickListener(this);
        loginAcc.setOnClickListener(this);

    }

    private void registerUSer() {
        // Getting User Input
        String name = EditTextName.getText().toString().trim();
        String username = EditTextUserName.getText().toString().trim();
        String email = EditTextEmail.getText().toString().trim();
        String phone = EditTextNumber.getText().toString().trim();
        String pass = EditTextPass.getText().toString().trim();

        // Check Validiation
        boolean isValidEmail = isValidEmail(email);
        boolean isValidnum = isValidPhone(phone);
        boolean isValidpaa = isValidPass(pass);

        // Check If Null Or Not
        boolean nulldata = notnull(name,username,email,phone,pass);



        // Ecryptio test code
//        try {
//            SecretKey secret = generateKey("7x!A%D*G-KaPdSgVkYp2s5v8y/B?E(H+");
//            byte [] en = encryptMsg(pass, secret);
//            encrypt = new String(en);
//            temp2 = new String(decryptMsg(en, secret));
//            System.out.println("pass : "+pass);
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


        // If All Valid And Not Null

        if (isValidEmail && isValidnum && isValidpaa && nulldata) {
            // Shew Dialog Msg
            progressDialog.setMessage("REGISTER IS IN PROCCISING ");
            progressDialog.show();

            //Request To PHP File For Read And Write In Database

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        System.out.println(response.toString());
                        // read From Response Decleard in PhP

                        JSONObject jsonObject = new JSONObject(response);

                        // show the output of the response
                        if (response.contains("User name allready used type another one")) {
                            Toast.makeText(getApplicationContext(), "User name allready used type another one", Toast.LENGTH_LONG).show();
                        } else if (response.contains("This Email is allready registered")) {
                            Toast.makeText(getApplicationContext(), "This Email is allready registered", Toast.LENGTH_LONG).show();
                        } else if (response.contains("This Phone number is allready registered")) {
                            Toast.makeText(getApplicationContext(), "This Phone number is allready registered", Toast.LENGTH_LONG).show();
                        } else if (response.contains("This Email is not valid")) {
                            Toast.makeText(getApplicationContext(), "This Email is not valid", Toast.LENGTH_LONG).show();
                        } else if (response.contains("You are registered successfully")) {
                            Toast.makeText(getApplicationContext(), "You are registered successfully", Toast.LENGTH_LONG).show();
                            // Move To Login Page
                            Intent intent1 = new Intent(signUp_mysqlphp.this, Login_MySql.class);
                            startActivity(intent1);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to register you account", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("haha error");
                    progressDialog.hide();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }

            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    // Send Data To Php File

                    params.put("name", name);
                    params.put("username", username);
                    params.put("email", email);
                    params.put("phone", phone);
                    params.put("password", pass);
                    return params;
                }
            };

            // Acitivate The Request
            RequestHandlerSingleton.getInstance(this).addToRequestQueue(stringRequest);
        }else {
            // if null Show Toast Msg
            if (!nulldata) {
                Toast.makeText(getApplicationContext(), "Must fill all fields", Toast.LENGTH_LONG).show();
            }else if (!isValidEmail) {
                Toast.makeText(getApplicationContext(), "This Email is not valid", Toast.LENGTH_LONG).show();
            } else if (!isValidpaa) {
                Toast.makeText(getApplicationContext(), "This Password is not valid", Toast.LENGTH_LONG).show();
            } else if (!isValidnum) {
                Toast.makeText(getApplicationContext(), "This Phone number is not valid", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onClick(View view) {
        if (view == BttonSignUpAcc) { // Move To The Method When Click In Button SignUp
            registerUSer();
        } else if (view == loginAcc) {
            // Move To The Method When Click In Button Login Page If Already Have An account
            Intent intent = new Intent(signUp_mysqlphp.this, Login_MySql.class);
            startActivity(intent);
        }
    }

    // Method For Checking Null
    public static boolean notnull(String name , String pass , String mail , String username , String phone ) {
        return (!name.equalsIgnoreCase("") && !pass.equalsIgnoreCase("") && !mail.equalsIgnoreCase("") && !username.equalsIgnoreCase("") && !phone.equalsIgnoreCase(""));
    }

    // Methods For Checking Validation
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public static boolean isValidPhone(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches() && target.length() >9 && target.length()<11);
    }
    public static boolean isValidPass (CharSequence target) {
        int upper =0;
        for(int i=0;i<target.length();i++){
            if(Character.isUpperCase(target.charAt(i))){
                upper++;
            }
        }
        return (!TextUtils.isEmpty(target) && target.length() >5 && target.length()<26 && upper>=2);
    }

    private final TextWatcher passStrenth = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            // When No Password Entered
            PasswordStrength.setText("Not Entered");
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        public void afterTextChanged(Editable s)
        {
            if(s.length()==0)
                PasswordStrength.setText("Not Entered");
            else if(s.length()<6) {
                PasswordStrength.setText("EASY");
                PasswordStrength.setTextColor(Color.parseColor("#B20600"));
            }
            else if(s.length()<10) {
                PasswordStrength.setText("MEDIUM");
                PasswordStrength.setTextColor(Color.parseColor("#FF5F00"));
            }
            else if(s.length()<15) {
                PasswordStrength.setText("STRONG");
                PasswordStrength.setTextColor(Color.parseColor("#43919B"));
            }
            else {
                PasswordStrength.setText("STRONGEST");
                PasswordStrength.setTextColor(Color.parseColor("#247881"));
            }

            if(s.length()>=25) {
                PasswordStrength.setText("Password Max Length Reached ( max = 25 char )");
                PasswordStrength.setTextColor(Color.parseColor("#B20600"));
            }
        }
    };

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

