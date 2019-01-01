package com.example.bhw_home.tracker.Model;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Track {
    public static String COLLECTION_NAME="track";

    public static String LAT_FIELD_MAME="lat";
    public static String LON_FIELD_MAME="lon";
    public static String TIME_FIELD_MAME="time";
    public static String UPLOADTIME_FIELD_MAME="uploadtime";
    public static String MILLITIME_FIELD_MAME="ms";

    private String deviceToken;

    private double lat;
    private double lon;
    private long milliTime;
    private String time;




    private String uploadTime;

    public HashMap<String , String> toHashMap(){
        HashMap<String , String> map=new HashMap<>();

        map.put(Track.LAT_FIELD_MAME,String.valueOf(lat));
        map.put(Track.LON_FIELD_MAME,String.valueOf(lon));
        try {
            map.put(Track.TIME_FIELD_MAME,String.valueOf(time));
        }
        catch (Exception e)
        {}
        try {
            map.put(Track.MILLITIME_FIELD_MAME,String.valueOf(milliTime));
        }
        catch (Exception e)
        {}
        try {
            map.put(Track.UPLOADTIME_FIELD_MAME,String.valueOf(uploadTime));
        }
        catch (Exception e)
        {}
        return map;
    }


    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }


    //using write gps
    public Track(){}
    public Track(GPSInfo gpsInfo) {
        deviceToken=FirebaseInstanceId.getInstance().getToken().toString();

        lat=gpsInfo.getLatitude();
        lon=gpsInfo.getLongitude();
        milliTime=System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
        Date timeInDate = new Date(milliTime);
        time = sdf.format(timeInDate);
    }

    //using read gps
    public Track(Map<String,Object> data){
        this.lat=Double.valueOf(data.get(LAT_FIELD_MAME).toString());
        this.lon=Double.valueOf(data.get(LON_FIELD_MAME).toString());

        try {
            this.milliTime=Long.valueOf(data.get(MILLITIME_FIELD_MAME).toString());
        }
        catch (Exception e)
        {}
        try {
            this.time=data.get(TIME_FIELD_MAME).toString();
        }
        catch (Exception e)
        {}
        try {
            this.uploadTime=data.get(UPLOADTIME_FIELD_MAME).toString();
        }
        catch (Exception e)
        {}

    }



    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }




    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getMilliTime() {
        return milliTime;
    }

    public void setMilliTime(long milliTime) {
        this.milliTime = milliTime;
    }


}
