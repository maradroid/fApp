package com.maradroid.fapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.login.widget.ProfilePictureView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by mara on 18.04.15..
 */
public class NewsFeedFragment extends android.support.v4.app.Fragment implements SetAdapterCallback, NeewsFeedAdapter.ClickListener{

    private ArrayList<NewsFeedObject> newsFeedObjects;
    private NewsFeedAsync newsFeedAsync;
    private String next;
    private String token;
    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private NeewsFeedAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).build();
        ImageLoader.getInstance().init(config);

        newsFeedObjects = new ArrayList<NewsFeedObject>();
        newsFeedObjects.add(new NewsFeedObject(null,null,null,null,null,null,null,null,null,true));

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY", 0);
        token = sharedPreferences.getString("TOKEN","ERROR");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.newsfeed_layout, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        mRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new NeewsFeedAdapter(newsFeedObjects);
        mAdapter.setClickListener(this);
        mRecycler.setAdapter(mAdapter);

        if(!token.equals("ERROR")) {

            getNewsFeed("https://graph.facebook.com/v2.3/me/home?access_token=" + token + "&fields=object_id,likes.summary(true),comments.summary(true),full_picture,id,from,to,message,link,name,story&debug=all&format=json&method=get&pretty=0&suppress_http_code=1");

        }

        return view;
    }

    public void getNewsFeed(String url){
        mAdapter.setLoader(false);
        newsFeedAsync = new NewsFeedAsync();
        newsFeedAsync.setCallback(this);
        newsFeedAsync.execute(url,"newsfeed");

    }


    @Override
    public void onRequestCompleted(Object itemObjectList, Object next) {
        ArrayList<NewsFeedObject> io = new ArrayList<NewsFeedObject>();
        io = (ArrayList<NewsFeedObject>)itemObjectList;
        for(int i=0;i<io.size();i++){
            mAdapter.addItem(io.get(i));
        }
        this.next = (String) next;
        mAdapter.setLoader(true);
    }

    @Override
    public void onClick(View v, int position) {

        if((v.getId()==R.id.blabla || v.getId()==R.id.text) && next!="nope") {

                getNewsFeed(next + "&fields=object_id,likes.summary(true),comments.summary(true),full_picture,id,from,to,message,link,name,story&debug=all&format=json&method=get&pretty=0&suppress_http_code=1");
        }

        if(v.getId() == R.id.comments_ll || v.getId() == R.id.comments_tv){

            if(!newsFeedObjects.get(position).getCommentsNumber().equals("0")) {
                Intent intent = new Intent(getActivity(), CommentsActivity.class);
                intent.putExtra("jstring", newsFeedObjects.get(position).getComments().toString());
                startActivity(intent);
            }
        }

        if(v.getId() == R.id.name_tv || v.getId() == R.id.message_tv || v.getId() == R.id.image_iw){
            if(newsFeedObjects.get(position).getPostLink()!=null){

                Intent in = new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse(newsFeedObjects.get(position).getPostLink()));
                startActivity(in);
            }
        }
    }
}
