package com.example.bhw_home.tracker.RecyclerClass;

import android.content.Context;
import android.os.Handler;

public class MyHandler extends Handler {
    private Context context;
    public MyHandler(Context context)
    {
        super();
        this.context=context;
    }

    public Context getContext() {
        return context;
    }
}
