package com.example.bhw_home.tracker.Firebase;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.bhw_home.tracker.Model.GPSInfo;
import com.example.bhw_home.tracker.Model.Track;
import com.example.bhw_home.tracker.R;
import com.example.bhw_home.tracker.Model.TrackDAO;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    private static final String TAG = "BHWLOG-FCMService";

    private void scheduleJob(RemoteMessage message) {
        //Toast.makeText(getApplicationContext(),"scheduleJob",Toast.LENGTH_LONG).show();
        return;
    }
    private void handleNow(RemoteMessage message) {
        //Toast.makeText(getApplicationContext(),message.getData().get("message").toString(),Toast.LENGTH_SHORT).show();
        //Log.d(TAG, message.getData().get("message").toString());

        try{
            if (message.getData().get("pushMessage").toString().equals("requestGPS"))
            {
                GPSInfo gps=new GPSInfo(getApplicationContext());
                Track track=new Track(gps);
                TrackDAO.update(track);
                Log.i(TAG,"RECEIVE requestGPS Message ");
            }
            else{
                Log.i(TAG,"RECEIVE unrecognize Message ");
            }
        }
        catch (Exception e)
        {

        }

        return;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...


        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());






            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob(remoteMessage);
                handleNow(remoteMessage);
            } else {
                // Handle message within 10 seconds
                //handleNow(remoteMessage);
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            //Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            //sendNotification(remoteMessage);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        //sendNotification(remoteMessage.getNotification().getBody());
    }

    private void sendNotification(RemoteMessage message) {
        //Intent intent = new Intent(this, MainActivity.class);
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:"+message.getData().get("lat").toString()+","+message.getData().get("lot").toString()));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder=
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal)
                        .setContentTitle(message.getData().get("timestamp").toString())
                        .setContentText(message.getData().get("lat").toString()+message.getData().get("lot").toString())
                        .setAutoCancel(false)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
/*
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal)
                        .setContentTitle("FCM Message")
                        .setContentText("hello")
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);*/
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, builder.build());
    }



    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String refreshedToken = FirebaseInstanceId.getInstance().getInstanceId().getResult().getToken();
        MyFirestoreRequest.sendRegistrationToServer("default");


    }


}
