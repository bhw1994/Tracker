package com.example.bhw_home.tracker.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.bhw_home.tracker.Firebase.MyFirestoreRequest;
import com.example.bhw_home.tracker.RecyclerClass.MyRecyclerAdapter;
import com.example.bhw_home.tracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class DeviceTokenActivity extends AppCompatActivity {

    final String TAG="DeviceTokenActivity";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_devicetoken);


        recyclerView=findViewById(R.id.recycler_view);
        layoutManager= new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        final ArrayList<HashMap<String,String>> items=new ArrayList<>();

        final MyRecyclerAdapter adapter=new MyRecyclerAdapter(items,MyRecyclerAdapter.TOKEN_ADAPTER_TYPE);
        recyclerView.setAdapter(adapter);

        MyFirestoreRequest.getUserList().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        // 아이템 추가.

                        HashMap<String,String> h=new HashMap();
                        h.put("token",document.getData().get("deviceToken").toString());
                        h.put("name",document.getData().get("name").toString());
                        items.add(h);
                    }
                    adapter.notifyDataSetChanged();

                    // listview 갱신
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });



    }
}
