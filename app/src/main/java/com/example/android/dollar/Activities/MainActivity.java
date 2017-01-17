package com.example.android.dollar.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.dollar.Fragments.BankPricesFragment;
import com.example.android.dollar.Fragments.BestPricesFragment;
import com.example.android.dollar.Fragments.GoldFragment;
import com.example.android.dollar.R;
import com.example.android.dollar.Utilities;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer ;
    NavigationView navigationView ;
    Toolbar toolbar ;
    FragmentManager mFragmentManager ;
    FragmentTransaction mFragmentTransaction ;
    View sherView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.content_main,new BankPricesFragment()).commit();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,drawer, toolbar,R.string.app_name,
                R.string.app_name);

        drawer.addDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
        sherView = getWindow().getDecorView().findViewById(android.R.id.content);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.sher_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {

            String fileName = "p.png";
            Bitmap bitmap = Utilities.getScreenShot(sherView);
            Utilities.store(bitmap ,fileName);
            File dir = new File(Utilities.photoDirPath);
            if(!dir.exists())
                dir.mkdirs();
            File file = new File(Utilities.photoDirPath, fileName);
            Utilities.shareImage(file , this);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bank) {

            BankPricesFragment bankFragment = new BankPricesFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main,
                    bankFragment,
                    bankFragment.getTag())
                    .commit();
            // Handle the camera action
        }

        else if (id == R.id.nav_bestBrise) {

            BestPricesFragment goldFragment = new BestPricesFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main,
                    goldFragment,
                    goldFragment.getTag())
                    .commit();

        }
        else if (id == R.id.nav_gold) {

            GoldFragment goldFragment = new GoldFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main,
                    goldFragment,
                    goldFragment.getTag())
                    .commit();

        }

         else if (id == R.id.nav_help) {

            Intent i = new Intent(MainActivity.this, IntroductionActivity.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
