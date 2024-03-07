package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class update_missing extends AppCompatActivity {
    EditText e1, e2, e3, e4;
    TextView t, t1;
    ImageView img2,img1;
    RadioGroup r1;
    RadioButton rb1, rb2;
    Button b1;
    SharedPreferences sh;
    String url;
    ProgressDialog pd;
    Bitmap bitmap = null,bitmap1 =null;
    String flg="no",gender = "female";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_missing);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        e1 = findViewById(R.id.editTextTextPersonName7);
        e2 = findViewById(R.id.editTextTextPersonName8);
        e3 = findViewById(R.id.editTextTextPersonName9);
        e4 = findViewById(R.id.editTextNumber3);
        img1 = findViewById(R.id.imageView9);               // id proof
        img2 = findViewById(R.id.imageView8);               // photo
        r1 = findViewById(R.id.radiogroup);
        rb1 = findViewById(R.id.radioButton3);
        rb2 = findViewById(R.id.radioButton4);
        b1 = findViewById(R.id.button5);

      if(custom_view_missing.gender[custom_view_missing.pos].equalsIgnoreCase("Male"))
      {
          rb2.setChecked(true);
      }


        e1.setText(custom_view_missing.name[custom_view_missing.pos]);
        e2.setText(custom_view_missing.place[custom_view_missing.pos]);
        e3.setText(custom_view_missing.post[custom_view_missing.pos]);
        e4.setText(custom_view_missing.pin[custom_view_missing.pos]);

        String image = custom_view_missing.photo[custom_view_missing.pos];
        String id_proof = custom_view_missing.id_proof[custom_view_missing.pos];
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip = sh.getString("ipaddress", "");
        String url1 = "http://" + ip + ":8000" + image;
        String url2 = "http://" + ip + ":8000" + id_proof ;
        Picasso.with(getApplicationContext()).load(url1).transform(new CircleTransform()).into(img2);//circle
        Picasso.with(getApplicationContext()).load(url2).transform(new CircleTransform()).into(img1);//circle





//
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
                flg="yes";
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 101);
                flg="yes";
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = e1.getText().toString();
                String place = e2.getText().toString();
                String post = e3.getText().toString();
                String pin = e4.getText().toString();
                if(rb2.isChecked()){
                    gender = "Male";
                }
               url =  sh.getString("url", "") + "android_update_missing";


                pd = new ProgressDialog(update_missing.this);
                pd.setMessage("Uploading....");
                pd.show();
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    pd.dismiss();


                                    JSONObject obj = new JSONObject(new String(response.data));

                                    if (obj.getString("status").equals("ok")) {
                                        Toast.makeText(getApplicationContext(), "Update success", Toast.LENGTH_SHORT).show();
                                        android.content.Intent i = new Intent(getApplicationContext(), view_missing.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(getApplicationContext(), " failed", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        SharedPreferences o = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        params.put("name", name);//passing to python
                        params.put("place", place);//passing to python
                        params.put("post", post);//passing to python
                        params.put("pin", pin);//passing to python
                        params.put("gender", gender);//passing to python
                        params.put("mid", sh.getString("mid", ""));//passing to python
                        return params;
                    }


                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, DataPart> params = new HashMap<>();
                        long imagename = System.currentTimeMillis();
                        if(bitmap!= null) {
                            params.put("pic", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                        }
                        if(bitmap1!= null) {
                            params.put("file", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap1)));
                        }
                        return params;
                    }
                };
                int MY_SOCKET_TIMEOUT_MS = 100000;

                volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);

            }
        });
    }


    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                img2.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                img1.setImageBitmap(bitmap1);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //converting to bitarray
    public byte[] getFileDataFromDrawable (Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),view_missing.class);
        startActivity(i);
//        super.onBackPressed();
    }
}
