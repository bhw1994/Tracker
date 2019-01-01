package com.example.bhw_home.tracker.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.bhw_home.tracker.Firebase.FirebaseFireStoreManager;
import com.example.bhw_home.tracker.Firebase.GoogleCloudFunctionRequest;
import com.example.bhw_home.tracker.RecyclerClass.MyRecyclerAdapter;
import com.example.bhw_home.tracker.R;
import com.example.bhw_home.tracker.Model.Track;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TrackActivity extends AppCompatActivity {

    public static String TAG="TrackActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        Intent intent=getIntent();
        final String token=intent.getStringExtra("token");

        Button requestButton=findViewById(R.id.request_button);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleCloudFunctionRequest.requestGPS(token);
            }
        });

        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;

        recyclerView=findViewById(R.id.track_recyclerView);
        layoutManager= new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        final ArrayList<HashMap<String,String>> items=new ArrayList<>();

        final MyRecyclerAdapter adapter=new MyRecyclerAdapter(items,MyRecyclerAdapter.TRACK_ADAPTER_TYPE);
        recyclerView.setAdapter(adapter);


        DocumentReference ref=FirebaseFireStoreManager.getDocument(Track.COLLECTION_NAME,token);


        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String,Object> data=document.getData();
                        TreeMap<String,Object> tm = new TreeMap<String,Object>(data);
                        HashMap<String,String> item;

                        Iterator<String> iteratorKey = tm.keySet( ).iterator( );   //키값 오름차순 정렬(기본)
                        while(iteratorKey.hasNext()) {
                            String key = iteratorKey.next();


                            Track track=new Track( ( (Map<String,Object>)data.get(key) ) );
                            item=track.toHashMap();

                            items.add(item);

                        }
                        adapter.notifyDataSetChanged();

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
