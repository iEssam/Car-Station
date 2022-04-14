package com.example.login22_01_19_h1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login22_01_19_h1.Menu.AddCarFragment;
import com.example.login22_01_19_h1.Menu.HistoryFragment;
import com.example.login22_01_19_h1.Menu.HomeFragment;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
//import com.google.android.odml.image.MlImage;
//import com.google.mlkit.vision.common.InputImage;
//import com.google.mlkit.vision.text.Text;
//import com.google.mlkit.vision.text.TextRecognizer;
//import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MainActivityOcr extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Button btn;
    Button browse;
    int SELECT_PHOTO = 1;
    Uri uri;


    ArrayList<String> re = new ArrayList<>();
    ArrayList<String> reSecond = new ArrayList<>();
    String Result;

    Intent intent;
    Bundle bundle;
    AddCarFragment addCarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ocr);

        addCarFragment = new AddCarFragment();

        imageView = findViewById(R.id.estemarhId);
        textView = findViewById(R.id.TextViewOcr);

        browse = findViewById(R.id.BtnOcrBrowse);

//     intent =new Intent(MainActivityOcr.this , AddCarFragment.class);
//        Intent intent2 = new Intent(MainActivityOcr.this, AddCarFragment.class);
//        startActivity(intent2);
        bundle = new Bundle();
        // set Fragmentclass Arguments

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, SELECT_PHOTO);


            }
        });

        btn = findViewById(R.id.BtnOcr);
        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                re.clear();
                reSecond.clear();
                Result = "Can't Read ";
                getTextFromImage(view);


                btn.setVisibility(View.GONE);
                browse.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.pageCont, addCarFragment).commit();

//                finishAfterTransition();


            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO
                && resultCode == RESULT_OK && data != null
                && data.getData() != null) {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void getTextFromImage(View v) {

//        Bundle bundle = intent.getExtras();

        TextRecognizer textRecognizer = new TextRecognizer.Builder(MainActivityOcr.this).build();

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

        SparseArray<TextBlock> sparseArray = textRecognizer.detect(frame);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < sparseArray.size(); i++) {
            TextBlock tx = sparseArray.get(i);
            stringBuilder.append(tx.getValue());
        }
        textView.setText(stringBuilder);
        String preResult = stringBuilder + "";
        String[] arr = new String[10];
        String[] arrOfStr = preResult.split("N", 5);


        for (String a : arrOfStr) {
            re.add(a);
        }

        for (int i = 0; i < re.size(); i++) {
//        Result = "";

            Log.println(Log.ASSERT, "OCR  PAGE :Re: " + i + " :", re.get(i) + "");
//


            if (re.get(i).contains("REGISTRATIO")) {
                Result = re.get(i + 1);
            }

        }

        if (!(Result.contains("Can't Read"))) {

            String[] arrOfStrSecond = Result.split(" ", 5);

            for (String a : arrOfStrSecond) {
                reSecond.add(a);
            }
            String finalResult = reSecond.get(0);
            Toast.makeText(getApplicationContext(), finalResult, Toast.LENGTH_LONG).show();
//            SharedPrefManger.getInstance(MainActivityOcr.this).setUserVid(finalResult);
            bundle.putString("VID_Readed", finalResult);


            Log.println(Log.ASSERT, "OCR PAGE Result", finalResult + "");
        } else {
            Toast.makeText(getApplicationContext(), Result, Toast.LENGTH_LONG).show();
//            SharedPrefManger.getInstance(MainActivityOcr.this).setUserVid(Result);

            bundle.putString("VID_Readed", Result);
        }

//        FragmentTransaction fragmentTransactiont =getSupportFragmentManager().beginTransaction();

        addCarFragment.setArguments(bundle);
//             setResult(RESULT_OK, intent);


//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,addCarFragment);

//        startActivity(intent);
    }


}


