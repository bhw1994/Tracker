package com.example.bhw_home.tracker.RecyclerClass;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.bhw_home.tracker.Activity.TrackActivity;
import com.example.bhw_home.tracker.Model.Track;
import com.example.bhw_home.tracker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class MyRecyclerAdapter extends RecyclerView.Adapter{

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View row;
        TextView text_name;
        TextView text_token;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            row=itemView.findViewById(R.id.item_row);
            text_name=itemView.findViewById(R.id.name);
            text_token=itemView.findViewById(R.id.token);
        }
    }


    public static int TOKEN_ADAPTER_TYPE=0;
    public static int TRACK_ADAPTER_TYPE=1;

    private ArrayList<HashMap<String,String>> items;
    private  int type;


    public MyRecyclerAdapter(ArrayList<HashMap<String,String>> items,int type){
        this.items=items;
        this.type=type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MyViewHolder holder= (MyViewHolder) viewHolder;

        final int position=i;
        if(type==TOKEN_ADAPTER_TYPE)
        {
            holder.text_name.setText(items.get(i).get("name"));
            holder.text_token.setText(items.get(i).get("token"));
            holder.row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String token=items.get(position).get("token");
                    Intent intent=new Intent(v.getContext(),TrackActivity.class);
                    intent.putExtra("token",token);
                    v.getContext().startActivity(intent);

                }
            });
        }
        else if(type==TRACK_ADAPTER_TYPE)
        {


            String timeInFormat = items.get(i).get(Track.TIME_FIELD_MAME);
            holder.text_name.setText(timeInFormat);
            try{holder.text_token.setText(items.get(i).get(Track.UPLOADTIME_FIELD_MAME));}
            catch (Exception e){};
            final String lat=items.get(position).get(Track.LAT_FIELD_MAME).toString();
            final String lon=items.get(position).get(Track.LON_FIELD_MAME).toString();
            holder.row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:"+lat+","+lon));
                    v.getContext().startActivity(intent);
                }
            });
        }





    }

    @Override
    public int getItemCount() {
        return items.size();
    }



}
