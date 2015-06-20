package com.maradroid.fapp;


import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by mara on 4/1/15.
 */
public class NewsFeedAsync extends AsyncTask<String, Void, Object[]> {

    private ArrayList<NewsFeedObject> newsFeedObjectList;
    private ArrayList<CommentsObject> commentsObjectList;
    private SetAdapterCallback callback;

    public void setCallback(SetAdapterCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Object[] doInBackground(String... params) {

        newsFeedObjectList = new ArrayList<NewsFeedObject>();
        commentsObjectList = new ArrayList<CommentsObject>();
        String user_name, user_id, post_message, post_picture, post_link, post_name, post_likes, post_story;
        JSONObject comments;
        Object[] obj = new Object[3];
        JSONObject jdata = null;

        String response = Http.getInstance().post(params[0]);

        JSONArray data = null;
        try {
            jdata = new JSONObject(response);
            data = jdata.getJSONArray("data");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
         }

        if (params[1].equals("newsfeed")) {
            for (int i = 0; i < data.length(); i++) {

                try {
                    user_name = data.getJSONObject(i).getJSONObject("from").getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                    user_name = null;
                }

                try {
                    user_id = data.getJSONObject(i).getJSONObject("from").getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                    user_id = null;
                }

                try {
                    post_message = data.getJSONObject(i).getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                    post_message = null;
                }

                try {
                    post_picture = data.getJSONObject(i).getString("full_picture");
                } catch (JSONException e) {
                    e.printStackTrace();
                    post_picture = null;
                }

                try {
                    post_link = data.getJSONObject(i).getString("link");
                } catch (JSONException e) {
                    e.printStackTrace();
                    post_link = null;
                }

                try {
                    post_name = data.getJSONObject(i).getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                    post_name = null;
                }

                try {
                    post_story = data.getJSONObject(i).getString("story");
                } catch (JSONException e) {
                    e.printStackTrace();
                    post_story = null;
                }

                try {
                    post_likes = data.getJSONObject(i).getJSONObject("likes").getJSONObject("summary").getString("total_count");
                } catch (JSONException e) {
                    e.printStackTrace();
                    post_likes = null;
                }

                try {
                    comments = data.getJSONObject(i).getJSONObject("comments");
                } catch (JSONException e) {
                    e.printStackTrace();
                    comments = null;
                }

                try {
                    obj[1] = jdata.getJSONObject("paging").getString("next");
                } catch (JSONException e) {
                    e.printStackTrace();
                    obj[1] = "nope";
                }

                newsFeedObjectList.add(new NewsFeedObject(user_name, user_id, post_message, post_picture, post_link, post_name, post_story, post_likes, comments, false));
            }
            obj[0] = newsFeedObjectList;
            obj[2] = params[1];

        } else if (params[1].equals("comments")) {
            try {

                for (int i = 0; i < data.length(); i++) {

                    commentsObjectList.add(new CommentsObject(data.getJSONObject(i).getJSONObject("from").getString("id"),
                            data.getJSONObject(i).getJSONObject("from").getString("name"),
                            data.getJSONObject(i).getString("message"), false));

                }
                obj[0] = commentsObjectList;
                obj[2] = params[1];

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                obj[1] = jdata.getJSONObject("paging").getString("next");
            } catch (JSONException e) {
                e.printStackTrace();
                obj[1] = "nope";
            }
        }

        return obj;
    }

    @Override
    protected void onPostExecute(Object[] objects) {
        super.onPostExecute(objects);

        if (objects[2].equals("newsfeed")) {
            if (callback != null) {
                callback.onRequestCompleted(objects[0], objects[1]);
            } else {
            }
        } else if (objects[2].equals("comments")) {
            if (callback != null) {
                callback.onRequestCompleted(objects[0], objects[1]);
            }
        }
    }
}
