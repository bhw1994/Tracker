package com.example.bhw_home.tracker.Model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class Admin {

    private FirebaseFunctions mFunctions;
    private static Admin adminInstance;

    static {
        adminInstance = new Admin();
    }
    private Admin()
    {
        mFunctions = FirebaseFunctions.getInstance();
    }

    public static Admin getAdminInstance()
    {
        return adminInstance;
    }


    private Task<String> addMessage(String text) {
        // Create the arguments to the callable function, which is just one string
        Map<String, Object> data = new HashMap<>();
        data.put("text", text);

        return mFunctions
                .getHttpsCallable("addMessage")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        String result = (String) task.getResult().getData();
                        return result;
                    }
                });
    }





}
