package com.example.helpme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.helpme.databinding.ActivityPoliceViewReplyBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class police_view_reply extends AppCompatActivity {
    String[]pc_id,complaint,complaint_date,reply,reply_date,police_info;
    ListView li;
    SharedPreferences sh;
    String url;

    private AppBarConfiguration appBarConfiguration;
    private ActivityPoliceViewReplyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPoliceViewReplyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        li = findViewById(R.id.listview3);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ipaddress","");
        url = sh.getString("url","") + "android_view_police_reply";

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
                                pc_id=new String[js.length()];
                                complaint=new String[js.length()];
                                complaint_date=new String[js.length()];
                                reply=new String[js.length()];
                                reply_date=new String[js.length()];
                                police_info=new String[js.length()];
//

                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    pc_id[i]=u.getString("pc_id");
                                    complaint[i]=u.getString("complaint");
                                    complaint_date[i]=u.getString("complaint_date");
                                    reply[i]=u.getString("reply");
                                    reply_date[i]=u.getString("reply_date");
                                    police_info[i]=u.getString("police_info");
//
                                }

                                li.setAdapter(new custom_view_police_reply(getApplicationContext(),pc_id,complaint,complaint_date,reply,reply_date,police_info));

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

                params.put("lid",sh.getString("lid",""));
//                params.put("pid",sh.getString("pid",""));
                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS=100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_police_view_reply);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent i = new Intent(getApplicationContext(),send_complaint_to_police.class);
                startActivity(i);
            }
        });
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_police_view_reply);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),Home.class);
        startActivity(i);
//        super.onBackPressed();
    }
}