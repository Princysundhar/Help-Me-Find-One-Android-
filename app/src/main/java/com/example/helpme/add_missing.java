package com.example.helpme;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class add_missing extends AppCompatActivity {
    EditText e1, e2, e3, e4;
    TextView t, t1;
    ImageView img2,img1;
    RadioGroup r1;
    RadioButton rb1, rb2;
    Button b1;
    SharedPreferences sh;
    String url;
    Bitmap bitmap = null,bitmap1 = null;
    ProgressDialog pd;
    String gender = "";
//    String fileName = "", path = "";
//    private static final int FILE_SELECT_CODE = 0;
//    String ip, lid, title, url1;
//    String PathHolder = "", PathHolder1 = "";
//    byte[] filedt = null, filedt1 = null;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missing);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ipaddress", "");
        url = sh.getString("url", "") + "android_add_missing";
        Toast.makeText(this, "yf" + url, Toast.LENGTH_SHORT).show();


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

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 101);
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(add_missing.this, "okkkkkkkk", Toast.LENGTH_SHORT).show();

                if (rb1.isChecked()) {
                    gender = "female";
                }

                String name = e1.getText().toString();
                String place = e2.getText().toString();
                String post = e3.getText().toString();
                String pin = e4.getText().toString();
                int flag = 0;
                if(name.equalsIgnoreCase("")){
                    e1.setError("Null");
                    flag++;
                }
                if(place.equalsIgnoreCase("")){
                    e2.setError("Null");
                    flag++;
                }
                if(post.equalsIgnoreCase("")){
                    e3.setError("Null");
                    flag++;
                }
                if(pin.equalsIgnoreCase("")){
                    e4.setError("Null");
                    flag++;
                }
                if(bitmap == null){
                    Toast.makeText(add_missing.this, "Choose Id proof", Toast.LENGTH_SHORT).show();
                    flag++;
                }
                if(bitmap1 == null){
                    Toast.makeText(add_missing.this, "Choose Image", Toast.LENGTH_SHORT).show();
                    flag++;
                }
//                String gender = ((RadioButton) (findViewById(r1.getCheckedRadioButtonId()))).getText().toString();
                if(flag ==0) {
                    uploadBitmap(name, place, post, pin, gender);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final String name, final String place, final String post, final String pin,final String gender) {


        pd = new ProgressDialog(add_missing.this);
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
                                Toast.makeText(getApplicationContext(), "Missing added", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), view_missing.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "missing Registration failed", Toast.LENGTH_SHORT).show();
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
                params.put("lid",sh.getString("lid",""));
                params.put("name", name);//passing to python
                params.put("place", place);//passing to python
                params.put("post", post);
                params.put("pin", pin);
                params.put("gender", gender);

                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                params.put("id_proof", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap1)));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}

