package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ip_page extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_page);
        e1 = findViewById(R.id.editTextTextPersonName);
        b1 = findViewById(R.id.button3);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1.setText(sh.getString("ipaddress",""));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipaddress = e1.getText().toString();
                int flag = 0;
                if (ipaddress.equalsIgnoreCase("")){
                    e1.setError("Null");
                    flag ++;
                }
                if(flag == 0) {
                    String url = "http://" + ipaddress + ":8000/";
                    String pdfurl = "http://" + ipaddress + ":8000";
                    Toast.makeText(ip_page.this, "welcome", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("ipaddress", ipaddress);
                    ed.putString("url", url);
                    ed.putString("pdfurl", pdfurl);
                    ed.commit();

                    Intent i = new Intent(getApplicationContext(), login.class);
                    startActivity(i);

                }
            }
        });
    }
}