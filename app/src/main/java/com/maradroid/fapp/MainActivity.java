package com.maradroid.fapp;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;



public class MainActivity extends ActionBarActivity implements LoginFragment.LoginFragmentCallback{



    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        if(AccessToken.getCurrentAccessToken()!=null){

            NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_activity_rl, newsFeedFragment).commit();

        }else {

            LoginFragment loginFragment = new LoginFragment();
            loginFragment.setLoginCallback(this);
            getSupportFragmentManager().beginTransaction().add(R.id.main_activity_rl, loginFragment).commit();

        }
    }

    @Override
    public void onLoginCompleted() {
        NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_rl,newsFeedFragment).commit();
    }
}
