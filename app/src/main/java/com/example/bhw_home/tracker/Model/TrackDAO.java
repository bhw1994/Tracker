package com.example.bhw_home.tracker.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.bhw_home.tracker.Firebase.FirebaseFireStoreManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TrackDAO {
    private static String TAG_NAME="TrackDAO";

    public static void update(Track track)
    {

        DocumentReference docRef=FirebaseFireStoreManager.getDocument(Track.COLLECTION_NAME,track.getDeviceToken());
        Map<String,Object> updates = new HashMap<>();
        Map<String, Object> nestedData = new HashMap<>();

        nestedData.put(Track.LAT_FIELD_MAME, track.getLat());
        nestedData.put(Track.LON_FIELD_MAME, track.getLon());
        nestedData.put(Track.TIME_FIELD_MAME, track.getTime());
        nestedData.put(Track.MILLITIME_FIELD_MAME, track.getMilliTime());
        nestedData.put(Track.UPLOADTIME_FIELD_MAME, FieldValue.serverTimestamp());

        updates.put(String.valueOf(track.getMilliTime()),nestedData);






        docRef.set(updates, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG_NAME, "DocumentSnapshot successfully updated!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG_NAME, "Error updating document", e);
                    }
                });









    }
}
