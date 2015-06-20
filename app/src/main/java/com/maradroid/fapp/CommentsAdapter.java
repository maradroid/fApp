package com.maradroid.fapp;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;

/**
 * Created by mara on 19.04.15..
 */
public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CommentsObject> commentsObjectList;
    private boolean loader = true;
    private static ClickListener clickListener;

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView user_name;
        private TextView comments;
        private ProfilePictureView profilePicture;

        public ViewHolder(View v) {
            super(v);

            this.user_name = (TextView) v.findViewById(R.id.user_name_tv);
            this.profilePicture = (ProfilePictureView) v.findViewById(R.id.profile_picture);
            this.comments = (TextView) v.findViewById(R.id.comment_tv);
        }
    }

    class ViewHolderFooter extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView text;
        private LinearLayout ll;

        public ViewHolderFooter(View v) {
            super(v);

            this.text = (TextView) v.findViewById(R.id.text);
            this.ll = (LinearLayout) v.findViewById(R.id.blabla);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(clickListener != null)
                clickListener.onClick(v, getPosition());
        }
    }



    public interface ClickListener {

        public void onClick(View v, int position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public CommentsAdapter(ArrayList<CommentsObject> objects) {
        this.commentsObjectList = objects;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        if(viewType == 1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reload_layout, parent, false);
            return new ViewHolderFooter(v);
        }else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_item_layout, parent, false);
            return new ViewHolder(v);
        }

    }

    public void addItem(CommentsObject object){
        commentsObjectList.add((commentsObjectList.size()-1),object);
        notifyItemInserted(commentsObjectList.size()-1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof ViewHolder) {
            ((ViewHolder) holder).user_name.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).profilePicture.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).user_name.setText(commentsObjectList.get(position).getName());
            ((ViewHolder) holder).profilePicture.setProfileId(commentsObjectList.get(position).getId());
            ((ViewHolder) holder).comments.setText(commentsObjectList.get(position).getComment());

        }else if(holder instanceof ViewHolderFooter){
            if(this.loader == false) {
                ((ViewHolderFooter) holder).text.setText("Loading...");
                ((ViewHolderFooter) holder).text.setClickable(false);
                ((ViewHolderFooter) holder).ll.setClickable(false);
            }else{
                ((ViewHolderFooter) holder).text.setText("Load more...");
                ((ViewHolderFooter) holder).text.setClickable(true);
                ((ViewHolderFooter) holder).ll.setClickable(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return commentsObjectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(commentsObjectList.get(position).isFooter()){
            return 1;
        }
        return 0;

    }

    public void setLoader(boolean loader){
        this.loader = loader;
        notifyItemChanged(getItemCount()-1);
    }
}
