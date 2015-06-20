package com.maradroid.fapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mara on 4/1/15.
 */
public class NewsFeedObject {

    private String user_name, user_id, post_message, post_picture, post_link, post_name, post_story, post_likes;
    private JSONObject comments = null;
    private boolean footer;

    public NewsFeedObject(String user_name, String user_id, String post_message,
                          String post_picture, String post_link, String post_name, String post_story, String post_likes, JSONObject comments, boolean footer){
        this.user_name = user_name;
        this.user_id = user_id;
        this.post_message = post_message;
        this.post_picture = post_picture;
        this.post_link = post_link;
        this.post_name = post_name;
        this.post_story = post_story;
        this.post_likes = post_likes;
        this.comments = comments;
        this.footer = footer;

    }

    public String getUserName(){return user_name;}
    public String getUserId(){return user_id;}
    public String getPostMessage(){return post_message;}
    public String getPostPicture(){return post_picture;}
    public String getPostLink(){return post_link;}
    public String getPostName(){return post_name;}
    public String getPostStory(){return post_story;}
    public String getPostLikes(){return post_likes;}
    public JSONObject getComments(){return comments;}
    public String getCommentsNumber(){if(comments==null){return "0";}else{
        try {
            return comments.getJSONObject("summary").getString("total_count");
        } catch (JSONException e) {
            e.printStackTrace();
            return "greska";
        }
    }}
    public boolean isFooter(){return footer;}

}
