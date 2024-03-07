package com.example.helpme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class custom_view_missing extends BaseAdapter {
    public static String[] mid, name, photo, gender, address, id_proof, type, status,place,post,pin;
    private Context context;
    SharedPreferences sh;
    String url;
    public  static  int pos=0;

    public custom_view_missing(Context applicationContext, String[] mid, String[] name, String[] photo, String[] gender, String[] address, String[] id_proof, String[] type, String[] status, String[] place, String[] post, String[] pin) {
        this.context = applicationContext;
        this.mid = mid;
        this.name = name;
        this.photo = photo;
        this.gender = gender;
        this.address = address;
        this.id_proof = id_proof;
        this.type = type;
        this.status = status;
        this.place = place;
        this.post = post;
        this.pin = pin;

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
            gridView = inflator.inflate(R.layout.activity_custom_view_missing, null);

        } else {
            gridView = (View) view;

        }
        ImageView img = (ImageView) gridView.findViewById(R.id.imageView4);
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView14);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView21);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView18);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView12);
        TextView tv5 = (TextView) gridView.findViewById(R.id.textView32);
        TextView tv6 = (TextView) gridView.findViewById(R.id.textView16);

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                String value = sh.getString("pdfurl", "") + id_proof[i];
                Toast.makeText(context, "" + value, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(value));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        Button b1 = (Button) gridView.findViewById(R.id.button8); // update
        b1.setTag(i);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = (int) view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("mid", mid[pos]);
                ed.commit();
                Intent i = new Intent(context, update_missing.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
        Button b2 = (Button) gridView.findViewById(R.id.button6); //deltele
        b2.setTag(i);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int pos = (int) view.getTag();
                sh = PreferenceManager.getDefaultSharedPreferences(context);
                sh.getString("ipaddress", "");
                url = sh.getString("url", "") + "android_delete_missing";

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(context.getApplicationContext(), view_missing.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(i);


                                    } else {
                                        Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {

                    //                value Passing android to python
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("mid", mid[pos]);//passing to python
//                        params.put("lid",sh.getString("lid",""));
                        return params;
                    }
                };


                int MY_SOCKET_TIMEOUT_MS = 100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);


            }
        });


        tv1.setTextColor(Color.RED);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.RED);
        tv5.setTextColor(Color.BLACK);
        tv6.setTextColor(Color.BLACK);


        tv1.setText(name[i]);
        tv2.setText(gender[i]);
        tv3.setText(address[i]);
//        tv4.setText(id_proof[i]);
        tv5.setText(type[i]);
        tv6.setText(status[i]);

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
