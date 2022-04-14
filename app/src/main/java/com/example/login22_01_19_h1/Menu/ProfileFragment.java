package com.example.login22_01_19_h1.Menu;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.login22_01_19_h1.Constants;
import com.example.login22_01_19_h1.MainActivity;
import com.example.login22_01_19_h1.R;
import com.example.login22_01_19_h1.RequestHandlerSingleton;
import com.example.login22_01_19_h1.SharedPrefManger;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {
    //    component inital  Declaeration
    TextView username,email;
    EditText oldpass, newpass;
    ImageView avater;
    Drawable myDrawable;
    Button reset;

    String encrypt="";
    String encrypt2="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // component Declaeration and link
        avater = getView().findViewById(R.id.UserImage);
        username = getView().findViewById(R.id.UserName);
        email = getView().findViewById(R.id.theEmail);
        oldpass = getView().findViewById(R.id.editTextoldPassword);
        newpass = getView().findViewById(R.id.editTextnewPassword);
        myDrawable = getResources().getDrawable(R.drawable.useravater);
        reset = getView().findViewById(R.id.restPass);

        avater.setImageDrawable(myDrawable);
        username.setText(SharedPrefManger.getInstance(MainActivity.getAppContext()).getUserName());
        email.setText(SharedPrefManger.getInstance(MainActivity.getAppContext()).getUserEmail());

        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                String oldp = oldpass.getText().toString();
                String newp = newpass.getText().toString();


                System.out.println("here is the oldpass : "+oldp);
                if(!oldp.isEmpty() && !newp.isEmpty()){
                    //Toast.makeText(getContext(), "Good", Toast.LENGTH_LONG).show();
                    //Check if his old pass is correct
                    //If so then change it to the new one
                    //else ? tell him its wrong
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_RESER_PASSWORD, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("All response :"+response);
                            if (response.contains("Password Changed")) {
                                Toast.makeText(getContext(), "Password Changed!", Toast.LENGTH_LONG).show();
                            } else if(response.contains("Wrong Password")) {
                                Toast.makeText(getContext(), "Wrong old Password! Please try again", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }

                    }) {
                        @androidx.annotation.Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("UserID", String.valueOf(SharedPrefManger.getInstance(MainActivity.getAppContext()).getUserId()));
                            params.put("Password",oldp );
                            params.put("NewPassword",newp );
                            return params;
                        }
                    };
                    RequestHandlerSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

                }else{
                    Toast.makeText(getContext(), "Sorry! Please fill the old and new password", Toast.LENGTH_LONG).show();
                }

            }
        });


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