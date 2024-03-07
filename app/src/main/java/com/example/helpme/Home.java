package com.example.helpme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helpme.databinding.ActivityHomeBinding;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView t1,t2;
    ImageView img;
    SharedPreferences sh;
    String url;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
//        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerview = navigationView.getHeaderView(0);
        t1 = headerview.findViewById(R.id.textView);
        t2 = headerview.findViewById(R.id.textView1);
        img = headerview.findViewById(R.id.imageView);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip = sh.getString("ipaddress","");
        String url = "http://" + ip +":8000" + sh.getString("photo","");
        Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()).into(img);
        t1.setText(sh.getString("name",""));
        t2.setText(sh.getString("email",""));

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_settings){
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed = sh.edit();
            ed.commit();
            ed.clear();
            Intent i = new Intent(getApplicationContext(),ip_page.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home){
            Intent i = new Intent(getApplicationContext(),view_complaint.class);
            startActivity(i);
        }
        if(id == R.id.nav_gallery){
            Intent i = new Intent(getApplicationContext(),police_view_reply.class);
            startActivity(i);
        }
        if(id == R.id.nav_slideshow){
            Intent i = new Intent(getApplicationContext(),view_missing.class);
            startActivity(i);
        }
        if(id == R.id.nav_slideshows){
            Intent i = new Intent(getApplicationContext(),view_emergency_contact.class);
            startActivity(i);
        }
        if(id == R.id.nav_slideshowz){
            Intent i = new Intent(getApplicationContext(),view_wanted_details.class);
            startActivity(i);
        }
        if(id == R.id.nav_homes){
            Intent i = new Intent(getApplicationContext(),view_missing_details.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}