package com.example.go4lunch.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.go4lunch.R;
import com.example.go4lunch.controllers.fragments.MapFragment;
import com.example.go4lunch.controllers.fragments.RestaurantListFragment;
import com.example.go4lunch.controllers.fragments.WorkmatesFragment;
import com.example.go4lunch.databinding.ActivityMainBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private androidx.appcompat.widget.Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private BottomNavigationView mBottomNavigationView;

    private ActivityMainBinding activityMainBinding;


    @Override
    public int getFragmentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.activity_main_your_meal:
                //TODO: montrer le restaurant choisi
                Toast.makeText(this, "Mon repas", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_main_drawer_settings:
                // TODO: ouvrir les paramètres
                Toast.makeText(this, "Les paramètres de l'application", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_main_drawer_logout:
                this.signOutUserFromFirebase();
                break;

        }
        //close navigation drawer after choice
        //drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //manage back button
    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureViewBinding();
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureBottomView();
    }

    // 1 configure ViewBinding
    private void configureViewBinding(){
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view =  activityMainBinding.getRoot();
        setContentView(view);
    }

    // 2 - Configure Toolbar
    private void configureToolBar(){
        this.toolbar =  activityMainBinding.activityMainToolbar;
        setSupportActionBar(toolbar);
    }

    // 3 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.mDrawerLayout = activityMainBinding.activityMainDrawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 4 - Configure NavigationView (left menu drawer)
    private void configureNavigationView(){
        this.mNavigationView = activityMainBinding.activityMainNavigationView;
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    // 5 - configure BottomView
    private void configureBottomView(){
        this.mBottomNavigationView = activityMainBinding.bottomNavigationView;

        // open navigation Fragment by default
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();

        //manage click on nav
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.bottom_navigation_map:
                        selectedFragment = new MapFragment();
                        break;
                    case R.id.bottom_navigation_restaurant_list:
                        selectedFragment = new RestaurantListFragment();
                        break;
                    case R.id.bottom_navigation_workmates_list:
                        selectedFragment = new WorkmatesFragment();
                        break;

                    default:
                        break;
                }
                //display and replace the fragment container with our selected fragment
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }
                return true;
            }
        });
    }


    private Boolean updateMainFragment(Integer integer){
        switch (integer) {
            case R.id.bottom_navigation_map:
                //TODO: ouvrir fragment de la carte
                Toast.makeText(this, "Ouverture de la carte", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_navigation_restaurant_list:
                //TODO: ouvrir fragment de la liste de restaurants
                Toast.makeText(this, "Ouverture de la liste de restaurants", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_navigation_workmates_list:
                //TODO: ouvrir fragment de la liste des collègues
                Toast.makeText(this, "Ouverture de la liste des collègues", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
        return true;
    }

    // configure sign out
    private void signOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(MainActivity.this, ConnectActivity.class));
                        finish();
                    }
                });
    }

}
