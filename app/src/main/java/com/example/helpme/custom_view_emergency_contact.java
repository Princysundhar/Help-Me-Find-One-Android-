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

public class custom_view_emergency_contact extends BaseAdapter {

    String[] em_id, name, emergency_contact;
    private Context context;


    public custom_view_emergency_contact(Context applicationContext, String[] em_id, String[] name, String[] emergency_contact) {
        this.context = applicationContext;
        this.em_id = em_id;
        this.name = name;
        this.emergency_contact = emergency_contact;

    }

    @Override
    public int getCount() {
        return emergency_contact.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_emergency_contact, null);

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView29);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView30);
        Button b1 = (Button)gridView.findViewById(R.id.button7);
        b1.setTag(i);
        b1.setOnClickListener(new View.OnClickListener() {              // CALL EMERGENCY
            @Override
            public void onClick(View view) {

                try {
                    Intent my_callIntent = new Intent(Intent.ACTION_DIAL);
                    my_callIntent.setData(Uri.parse("tel:"+emergency_contact[i]));
                    //here the word 'tel' is important for making a call...
                    my_callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(my_callIntent);
                }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });


        tv1.setTextColor(Color.RED);
        tv2.setTextColor(Color.BLACK);


        tv1.setText(name[i]);
        tv2.setText(emergency_contact[i]);


        return gridView;
    }
}