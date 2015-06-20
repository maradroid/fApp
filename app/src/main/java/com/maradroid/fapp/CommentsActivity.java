package com.maradroid.fapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mara on 19.04.15..
 */
public class CommentsActivity extends ActionBarActivity implements CommentsAdapter.ClickListener,SetAdapterCallback{

    private JSONObject jobject;
    private NewsFeedAsync newsFeedAsync;
    private String next;
    private ArrayList<CommentsObject> commentsObjects;
    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private CommentsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        commentsObjects = new ArrayList<CommentsObject>();
        commentsObjects.add(new CommentsObject(null,null,null,true));

        mRecycler = (RecyclerView) findViewById(R.id.recycler_view_comments);
        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new CommentsAdapter(commentsObjects);
        mAdapter.setClickListener(this);
        mRecycler.setAdapter(mAdapter);

        try {
            jobject = new JSONObject(getIntent().getStringExtra("jstring"));

            for(int i = 0; i< jobject.getJSONArray("data").length();i++){

                mAdapter.addItem(new CommentsObject(jobject.getJSONArray("data").getJSONObject(i).getJSONObject("from").getString("id"),
                        jobject.getJSONArray("data").getJSONObject(i).getJSONObject("from").getString("name"),
                        jobject.getJSONArray("data").getJSONObject(i).getString("message"),false));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            next = jobject.getJSONObject("paging").getString("next");
        } catch (JSONException e) {
            e.printStackTrace();
            next="nope";
        }

    }

    public void getNewsFeed(String url){
        mAdapter.setLoader(false);
        newsFeedAsync = new NewsFeedAsync();
        newsFeedAsync.setCallback(this);
        newsFeedAsync.execute(url,"comments");

    }

    @Override
    public void onClick(View v, int position) {
        if((v.getId()==R.id.blabla || v.getId()==R.id.text) && next != "nope") {

            getNewsFeed(next+"&debug=all&format=json&method=get&pretty=0&suppress_http_code=1");

        }else{

            Toast.makeText(this, "No more comments!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestCompleted(Object itemObjectList, Object next) {
        ArrayList<CommentsObject> io = new ArrayList<CommentsObject>();
        io = (ArrayList<CommentsObject>)itemObjectList;
        for(int i=0;i<io.size();i++){
            mAdapter.addItem(io.get(i));
        }
        this.next = (String) next;

        mAdapter.setLoader(true);

    }
}
