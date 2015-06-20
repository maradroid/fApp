package com.maradroid.fapp;

/**
 * Created by mara on 19.04.15..
 */
public class CommentsObject {

    private String id, name, comment;
    boolean footer;

    public CommentsObject(String id, String name, String comment, boolean footer){
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.footer = footer;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public boolean isFooter() {
        return footer;
    }
}
