package com.example.week3;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PostAdapter extends BaseAdapter {

    List<Post> postList;
    private LayoutInflater inflater;
    public PostAdapter(Activity activity, List<Post> posts){
        this.postList = posts;
        inflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView;
        rowView = inflater.inflate(R.layout.row,null);
        EditText txtMessage = rowView.findViewById(R.id.txtMessage);
        TextView location = rowView.findViewById(R.id.txtLocation);
        ImageView imageView = rowView.findViewById(R.id.imageView);

        Post post = postList.get(i);
        txtMessage.setText(post.getMessage());
        imageView.setImageBitmap(post.getImage());
        if(post.getLocation() != null){
            location.setText(post.getLocation().getLatitude() + " " +post.getLocation().getLongitude() );
        }
        return rowView;
    }
}
