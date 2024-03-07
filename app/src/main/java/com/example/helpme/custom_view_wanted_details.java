package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class custom_view_wanted_details extends BaseAdapter {
    String[]wid,name,gender,age,address,photo,category,police;
    private Context context;


    public custom_view_wanted_details(Context applicationContext, String[] wid, String[] name, String[] gender, String[] age, String[] address, String[] photo, String[] police, String[] category) {
        this.context = applicationContext;
        this.wid = wid;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.photo = photo;
        this.category = category;
        this.police = police;

    }

    @Override
    public int getCount() {
        return name.length;
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
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.activity_custom_view_wanted_details, null);

        } else {
            gridView = (View) view;

        }
        ImageView img = (ImageView)gridView.findViewById(R.id.imageView3);
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView34);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView36);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView38);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView40);
        TextView tv5 = (TextView) gridView.findViewById(R.id.textView42);
        TextView tv6 = (TextView) gridView.findViewById(R.id.textView44);



        tv1.setTextColor(Color.RED);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);
        tv6.setTextColor(Color.BLACK);



        tv1.setText(name[i]);
        tv2.setText(gender[i]);
        tv3.setText(age[i]);
        tv4.setText(address[i]);
        tv5.setText(police[i]);
        tv6.setText(category[i]);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ipaddress", "");
        String url = "http://" + ip + ":8000" + photo[i];
        Picasso.with(context).load(url).transform(new CircleTransform()).into(img);//circle


        return gridView;
    }
}