package com.maradroid.fapp;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by mara on 3/11/15.
 */
public class NeewsFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<NewsFeedObject> newsFeedObjectList;
    private static ClickListener clickListener;
    private boolean loader = false;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView user_name;
        private TextView story;
        private TextView message;
        private TextView name;
        private ImageView image;
        private TextView likes;
        private TextView comments;
        private LinearLayout commentsLayout;

        ProfilePictureView profilePicture;

        View view;


        public ViewHolder(View v) {
            super(v);

            this.user_name = (TextView) v.findViewById(R.id.user_name_tv);
            this.story = (TextView) v.findViewById(R.id.story_tv);
            this.message = (TextView) v.findViewById(R.id.message_tv);
            this.name = (TextView) v.findViewById(R.id.name_tv);
            this.profilePicture = (ProfilePictureView) v.findViewById(R.id.profile_picture);
            this.image = (ImageView) v.findViewById(R.id.image_iw);
            this.view = v;
            this.likes = (TextView) v.findViewById(R.id.like_tv);
            this.comments = (TextView) v.findViewById(R.id.comments_tv);
            this.commentsLayout = (LinearLayout) v.findViewById(R.id.comments_ll);
            this.comments.setOnClickListener(this);
            this.commentsLayout.setOnClickListener(this);
            this.message.setOnClickListener(this);
            this.name.setOnClickListener(this);
            this.image.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onClick(v, getPosition());
        }
    }

    class ViewHolderFooter extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView text;
        private LinearLayout ll;

        public ViewHolderFooter(View v) {
            super(v);

            text = (TextView) v.findViewById(R.id.text);
            ll = (LinearLayout) v.findViewById(R.id.blabla);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (clickListener != null)
                clickListener.onClick(v, getPosition());
        }

    }


    public interface ClickListener {
        public void onClick(View v, int position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public NeewsFeedAdapter(ArrayList<NewsFeedObject> objects) {
        this.newsFeedObjectList = objects;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        if (viewType == 1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reload_layout, parent, false);
            return new ViewHolderFooter(v);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsfeed_item_layout, parent, false);
            return new ViewHolder(v);
        }
    }


    public void addItem(NewsFeedObject object) {
        newsFeedObjectList.add((newsFeedObjectList.size() - 1), object);
        notifyItemInserted(newsFeedObjectList.size() - 1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).user_name.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).story.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).message.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).name.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).profilePicture.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).image.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).user_name.setText(newsFeedObjectList.get(position).getUserName());
            ((ViewHolder) holder).story.setText(newsFeedObjectList.get(position).getPostStory());

            if (newsFeedObjectList.get(position).getPostMessage() != null) {
                ((ViewHolder) holder).message.setText(newsFeedObjectList.get(position).getPostMessage());
            } else {
                ((ViewHolder) holder).message.setVisibility(View.GONE);
            }

            if (newsFeedObjectList.get(position).getPostName() != null) {
                ((ViewHolder) holder).name.setText(newsFeedObjectList.get(position).getPostName());
            } else {
                ((ViewHolder) holder).name.setVisibility(View.GONE);
            }

            ((ViewHolder) holder).profilePicture.setProfileId(newsFeedObjectList.get(position).getUserId());

            if (newsFeedObjectList.get(position).getCommentsNumber().equals("1")) {
                ((ViewHolder) holder).comments.setText("1 comment");
            } else {
                ((ViewHolder) holder).comments.setText(newsFeedObjectList.get(position).getCommentsNumber() + " comments");
            }

            if (newsFeedObjectList.get(position).getPostLikes().equals("1")) {
                ((ViewHolder) holder).likes.setText("1 like");
            } else {
                ((ViewHolder) holder).likes.setText(newsFeedObjectList.get(position).getPostLikes() + " likes");
            }
            if (newsFeedObjectList.get(position).getPostPicture() != null) {
                ImageLoader.getInstance().displayImage(newsFeedObjectList.get(position).getPostPicture(), ((ViewHolder) holder).image);
            } else {
                ((ViewHolder) holder).image.setVisibility(View.GONE);
            }
        } else if (holder instanceof ViewHolderFooter) {
            if (this.loader == false) {
                ((ViewHolderFooter) holder).text.setText("Loading...");
                ((ViewHolderFooter) holder).text.setClickable(false);
                ((ViewHolderFooter) holder).ll.setClickable(false);
            } else {
                ((ViewHolderFooter) holder).text.setText("Load more...");
                ((ViewHolderFooter) holder).text.setClickable(true);
                ((ViewHolderFooter) holder).ll.setClickable(true);
            }

        }
    }

    @Override
    public int getItemCount() {
        return newsFeedObjectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (newsFeedObjectList.get(position).isFooter()) {
            return 1;
        }
        return 0;
    }

    public void setLoader(boolean loader) {
        this.loader = loader;
        notifyItemChanged(getItemCount() - 1);
    }
}
