package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class user_registeration extends AppCompatActivity {
    EditText e1, e2, e3, e4, e5, e6, e7, e8;
    ImageView img;
    Button b1;
    SharedPreferences sh;
    String url;
    Bitmap bitmap = null;
    ProgressDialog pd;
    String pin_pattern = "[0-9]{6}";
    String email_pattern = "[a-z0-9._%+\\-]+@[a-z0-9.\\-]+\\.[a-z]{2,}$";
    String password_pattern = "[A-Za-z0-9]{3,9}";
    String mobile_pattern = "[0-9]{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registeration);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ipaddress", "");
        url = sh.getString("url", "") + "android_user_registration";

        e1 = findViewById(R.id.editTextTextPersonName3);
        e2 = findViewById(R.id.editTextTextPersonName4);
        e3 = findViewById(R.id.editTextTextPersonName5);
        e4 = findViewById(R.id.editTextNumber);
        e5 = findViewById(R.id.editTextTextEmailAddress);
        e6 = findViewById(R.id.editTextNumber2);
        e7 = findViewById(R.id.editTextTextPassword2);
        e8 = findViewById(R.id.editTextTextPassword3);
        img = findViewById(R.id.imageView2);
        b1 = findViewById(R.id.button);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = e1.getText().toString();
                String place = e2.getText().toString();
                String post = e3.getText().toString();
                String pin = e4.getText().toString();
                String email = e5.getText().toString();
                String contact = e6.getText().toString();
                String password = e7.getText().toString();
                String confirm_password = e8.getText().toString();

                int flag =0;
                if(name.equalsIgnoreCase("")){
                    e1.setError("Null");
                    flag ++;
                }
                if(place.equalsIgnoreCase("")){
                    e2.setError("Null");
                }
                if(post.equalsIgnoreCase("")){
                    e3.setError("Null");
                    flag++;
                }
                if(!pin.matches(pin_pattern)){
                    e4.setError("Null");
                    flag ++;
                }
                if(!email.matches(email_pattern)){
                    e5.setError("Null");
                    flag ++;
                }
                if(!contact.matches(mobile_pattern)){
                    e6.setError("Null");
                    flag ++;
                }
                if(!password.matches(password_pattern)){
                    e7.setError("Null");
                    flag++;
                }
                if(!confirm_password.equals(password)){
                    e8.setError("pass");
                }
                if(bitmap==null){
                    Toast.makeText(user_registeration.this, "choose image", Toast.LENGTH_SHORT).show();
                    flag++;
                }
                if(flag ==0){
                    uploadBitmap(name, place, post, pin, email, contact, password, confirm_password);

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

                img.setImageBitmap(bitmap);


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

    private void uploadBitmap(final String name, final String place, final String post, final String pin, final String email, final String contact, final String password,final String confirm_password) {


        pd = new ProgressDialog(user_registeration.this);
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
                                Toast.makeText(getApplicationContext(), "Registration success", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), login.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
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
                params.put("post", post);
                params.put("pin", pin);
                params.put("email", email);
                params.put("contact", contact);
                params.put("password", password);
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}