package com.shahid.connectify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> profiles;
    private Context context;

    public PostAdapter(Context context, List<Post> profiles) {
        this.context = context;
        this.profiles = profiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post profile = profiles.get(position);
        holder.username.setText(profile.getUsername());
        holder.date.setText(profile.getDate());
        holder.description.setText(profile.getDescription());
        holder.profileImage.setImageResource(profile.getImageResId());
        holder.likesCount.setText(String.valueOf(profile.getLikes()));
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText username;
        EditText date;
        EditText description;
        ImageView profileImage;
        TextView likesCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.profile_description);
            profileImage = itemView.findViewById(R.id.profile_image);
            likesCount = itemView.findViewById(R.id.likes_count);
        }
    }


}
