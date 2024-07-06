package com.shahid.connectify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> posts;
    private Context context;

    private String currentUserUid;

    private IAdapterClick iAdapterClick;

    public void setiAdapterClick(IAdapterClick iAdapterClick) {
        this.iAdapterClick = iAdapterClick;
    }

    public PostAdapter(Context context, List<Post> posts, String currentUserUid) {
        this.context = context;
        this.posts = posts;
        this.currentUserUid = currentUserUid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post profile = posts.get(position);
        holder.title.setText(profile.getTitle());
        holder.username.setText(profile.getUsername());
        holder.date.setText(profile.getTimestamp());
        holder.description.setText(profile.getDescription());
        if (profile.getImageUrl() != null) {
            Glide.with(context).load(profile.getImageUrl()).placeholder(R.drawable.img1).into(holder.profileImage);
        } else  {
            Glide.with(context).load(R.drawable.img1).into(holder.profileImage);
        }

        //holder.profileImage.setImageResource(profile.getImageResId());
        if (profile.getLikes() != null) {
            if (profile.getLikes().containsKey(currentUserUid)) {
                holder.likeIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_liked));
            } else {
                holder.likeIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like));
            }
            holder.likesCount.setText(String.valueOf(profile.getLikes().size()) + " likes");
        } else {
            holder.likeIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like));
            holder.likesCount.setText("0 likes");
        }

        holder.likeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iAdapterClick.onLikeClick(position, profile.getPostId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView date;
        TextView title;
        TextView description;
        ImageView profileImage;
        TextView likesCount;
        LinearLayout likeLayout;
        ImageView likeIv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.post_title);
            username = itemView.findViewById(R.id.username);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.profile_description);
            profileImage = itemView.findViewById(R.id.profile_image);
            likesCount = itemView.findViewById(R.id.likes_count);
            likeLayout = itemView.findViewById(R.id.likeLayout);
            likeIv = itemView.findViewById(R.id.likeIv);
        }
    }


}
