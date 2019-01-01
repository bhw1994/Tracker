package com.example.bhw_home.tracker.Firebase;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.bhw_home.tracker.RecyclerClass.MyHandler;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class MyFirestoreRequest {
    private static String TAG="FirestoreRequest";


    public static void registerAdmin(final MyHandler handler)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String name="admin";
        String token= FirebaseInstanceId.getInstance().getToken().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("deviceToken", token);


        db.collection("Target").document(name)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(handler.getContext(),"register addmin success",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

//when , device token changed , so auto register to server
    public static void sendRegistrationToServer()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String token=FirebaseInstanceId.getInstance().getToken().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("name", "default");
        user.put("deviceToken", token);
/*

        db.collection("Target").whereEqualTo("deviceToken",token).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().getDocuments().size()==0)
                    {
                        sendRegistrationToServer()
                    }
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

            }
        });
*/
        db.collection("Target").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error writing document", e);
            }
        });

    }

    public static Task<DocumentReference> sendRegistrationToServer(String name)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String token = FirebaseInstanceId.getInstance().getToken().toString();
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("deviceToken", token);

        return db.collection("Target").add(user);
    }

    public static void sendRegistrationToServer(final MyHandler handler, String name) {

        Task <DocumentReference> task=sendRegistrationToServer(name);
        task.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(handler.getContext(),"add success",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error writing document", e);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(handler.getContext(),"add fail",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }

    public static Task<QuerySnapshot> getUserList()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();



        return db.collection("Target").get();

    }

}
