package com.example.bhw_home.tracker.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bhw_home.tracker.Model.GPSInfo;
import com.example.bhw_home.tracker.Firebase.MyFirestoreRequest;
import com.example.bhw_home.tracker.RecyclerClass.MyHandler;
import com.example.bhw_home.tracker.R;

public class MainActivity extends AppCompatActivity {

    private Button setSudo;
    private Button sendData;
    private Button showToken;


    private EditText nameEdit;

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    // GPSTracker class
    private GPSInfo gps;
    private MyHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        callPermission();  // 권한 요청을 해야 함

        handler=new MyHandler(getApplicationContext());

        setSudo=findViewById(R.id.setSudo);
        sendData=findViewById(R.id.sendMyData);
        nameEdit=findViewById(R.id.name);
        showToken=findViewById(R.id.showToken);


        sendData.setOnClickListener(new View.OnClickListener() {
            String token;
            @Override
            public void onClick(View view) {
                MyFirestoreRequest.sendRegistrationToServer(handler,nameEdit.getText().toString());
            }
        });

        setSudo.setOnClickListener(new View.OnClickListener() {
            String token;
            @Override
            public void onClick(View view) {
                MyFirestoreRequest.registerAdmin(handler);
            }
        });

        showToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),DeviceTokenActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }
    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }
}
