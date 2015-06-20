package com.maradroid.fapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

/**
 * Created by mara on 18.04.15..
 */
public class LoginFragment extends android.support.v4.app.Fragment{

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private LoginFragmentCallback fragmentCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment_layout, container, false);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "read_stream", "user_friends", "user_about_me", "user_actions.music", "user_birthday", "user_hometown", "user_photos", "user_relationships", "user_tagged_places",
                "user_work_history", "user_actions.books", "user_actions.news", "user_education_history", "user_games_activity", "user_likes", "user_posts", "user_religion_politics",
                "user_videos", "user_actions.fitness", "user_actions.video", "user_events", "user_groups", "user_location", "user_relationship_details", "user_status", "user_website"));
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (fragmentCallback != null) {
                    fragmentCallback.onLoginCompleted();
                }
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        return view;
    }

    public interface LoginFragmentCallback{
        public void onLoginCompleted();
    }

    public void setLoginCallback(LoginFragmentCallback callback){this.fragmentCallback = callback;}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
