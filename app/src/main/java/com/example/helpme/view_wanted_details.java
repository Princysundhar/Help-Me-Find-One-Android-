package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_wanted_details extends AppCompatActivity {
    String[]wid,name,gender,age,address,photo,category,police;
    ListView li;
    SharedPreferences sh;
    String url;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wanted_details);
        li = findViewById(R.id.listview3);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ipaddress","");
        url = sh.getString("url","")+"android_view_wanted_details";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js= jsonObj.getJSONArray("data");
                                wid =new String[js.length()];
                                name=new String[js.length()];
                                gender=new String[js.length()];
                                age=new String[js.length()];
                                address=new String[js.length()];
                                photo=new String[js.length()];
                                police=new String[js.length()];
                                category=new String[js.length()];

                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    wid[i]=u.getString("wid");
                                    name[i]=u.getString("name");
                                    gender[i]=u.getString("gender");
                                    age[i] = u.getString("age");
                                    address[i] = u.getString("place")+"/n"+u.getString("post")+"/n"+u.getString("pin");
                                    photo[i] = u.getString("photo");
                                    police[i] = u.getString("police");
                                    category[i] = u.getString("category");
                                }

                                li.setAdapter(new custom_view_wanted_details(getApplicationContext(),wid,name,gender,age,address,photo,police,category));

                            }


                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        }    catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("pid", sh.getString("pid",""));//passing to python



                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS=100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);



    }
}