package com.example.login22_01_19_h1.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.login22_01_19_h1.LoginSingup.Login_MySql;
import com.example.login22_01_19_h1.R;
import com.example.login22_01_19_h1.SharedPrefManger;
import com.google.android.material.navigation.NavigationView;


public class Navigation_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Declear Drawer Layout Of the Page
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main);

        // Move it To Login Page If it's Not Logged in
        if(!SharedPrefManger.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this , Login_MySql.class));
        }

        // Navigation View Contain The Fragments
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Declear The Header Of Navigation
        View headerView = navigationView.getHeaderView(0);

        // Declear Compnent Of Header
        TextView txUserName = (TextView) headerView.findViewById(R.id.UserName);
        TextView txUserEmail = (TextView) headerView.findViewById(R.id.UserEmail);

        // Setting Info in Compnent Of Header
        txUserName.setText(SharedPrefManger.getInstance(this).getUserName() + "");
        txUserEmail.setText(SharedPrefManger.getInstance(this).getUserEmail() + "");

        // ToolBar in The Top Of Screen Declaeration
        Toolbar toolbar1 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);

        drawer = findViewById(R.id.drawer_layout);

        // Listener For The Item Of The The Navigation
        navigationView.setNavigationItemSelectedListener(this);

        //Activate The Navigation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar1,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) { // Open Home If The State Null
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Move To Pages Selected
        switch (item.getItemId()) {
            case R.id.nav_person:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_Histroy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HistoryFragment()).commit();

                break;
            case R.id.Logout:
                SharedPrefManger.getInstance(this).Logout();
                Intent intent1 = new Intent(this, Login_MySql.class);
                startActivity(intent1);

                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}