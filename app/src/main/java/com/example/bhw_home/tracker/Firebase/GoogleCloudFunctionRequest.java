package com.example.bhw_home.tracker.Firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class GoogleCloudFunctionRequest {
    static private FirebaseFunctions mFunctions=FirebaseFunctions.getInstance();;
    static private String TAG="BHWLOG-GoogleCloudFunctionRequest";


    public static void requestGPS(String deviceToken)
    {
        // Create the arguments to the callable function, which is just one string
        Map<String, Object> data = new HashMap<>();
        data.put("registrationToken", deviceToken);

        mFunctions
                .getHttpsCallable("requestGPS")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        //HttpsCallableResult r=task.getResult();
                        //HashMap<String,String> h=(HashMap<String,String>) r.getData();
                        //String result = h.get("responseMessage");
                        String result=task.getResult().getData().toString();
                        Log.d(TAG,"SEND requestGPS Push");
                        Log.d(TAG,result);
                        return result;
                    }
                }).addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                try {
                    checkTaskError(task);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private static void checkTaskError(Task task) throws Exception{
        if (!task.isSuccessful()) {
            Exception e = task.getException();
            if (e instanceof FirebaseFunctionsException) {
                FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                FirebaseFunctionsException.Code code = ffe.getCode();
                Object details = ffe.getDetails();
            }

            // [START_EXCLUDE]

            Log.w(TAG, TAG+":onFailure", e);
            throw new Exception();
            // [END_EXCLUDE]
        }
        return;
    }
}
