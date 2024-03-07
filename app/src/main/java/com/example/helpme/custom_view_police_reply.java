package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class custom_view_police_reply extends BaseAdapter {
    String[]pc_id,complaint,complaint_date,reply,reply_date,police_info;
    private Context context;


    public custom_view_police_reply(Context applicationContext, String[] pc_id, String[] complaint, String[] complaint_date, String[] reply, String[] reply_date, String[] police_info) {
        this.context = applicationContext;
        this.pc_id = pc_id;
        this.complaint = complaint;
        this.complaint_date = complaint_date;
        this.reply = reply;
        this.reply_date = reply_date;
        this.police_info = police_info;

    }

    @Override
    public int getCount() {
        return complaint.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_custom_view_police_reply,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView4);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView6);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView8);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView10);
        TextView tv5=(TextView)gridView.findViewById(R.id.textView21);


        tv1.setTextColor(Color.RED);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);


        tv1.setText(complaint[i]);
        tv2.setText(complaint_date[i]);
        tv3.setText(reply[i]);
        tv4.setText(reply_date[i]);
        tv5.setText(police_info[i]);




        return gridView;
    }
}