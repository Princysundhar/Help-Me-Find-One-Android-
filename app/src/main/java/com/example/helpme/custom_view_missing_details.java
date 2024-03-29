package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class custom_view_missing_details extends BaseAdapter {
    String[]mid,name,photo,gender,address,id_proof;
    private Context context;


    public custom_view_missing_details(Context applicationContext, String[] mid, String[] name, String[] photo, String[] gender, String[] address, String[] id_proof) {
        this.context =applicationContext;
        this.mid = mid;
        this.name = name;
        this.photo = photo;
        this.gender = gender;
        this.address = address;
        this.id_proof = id_proof;

    }

    @Override
    public int getCount() {
        return address.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_missing_details, null);

        } else {
            gridView = (View) view;

        }
        ImageView img = (ImageView)gridView.findViewById(R.id.imageView5);
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView46);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView48);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView50);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView52);
//
//        tv4.setTag(i);
        tv4.setOnClickListener(new View.OnClickListener() {                 // view pdf(id proof)
            @Override
            public void onClick(View view) {
//                int k =(int)view.getTag();
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                String value=sh.getString("pdfurl", "")+id_proof[i];
                Toast.makeText(context, ""+value, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(value));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        tv1.setTextColor(Color.RED);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.RED);

        tv1.setText(name[i]);
        tv2.setText(gender[i]);
        tv3.setText(address[i]);
//        tv4.setText(id_proof[i]);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ipaddress", "");
        String url = "http://" + ip + ":8000" + photo[i];
        Picasso.with(context).load(url).transform(new CircleTransform()).into(img);//circle


        return gridView;
    }

    private void openFile(String url) {
        Uri uri = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (url.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if (url.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (url.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if (url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            //if you want you can also define the intent type for any other file
            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "/");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}