package com.shahid.connectify;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
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

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void shareIntent(Bitmap bitmap) {
        String bitmapPath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,"title", null);
        Uri bitmapUri = Uri.parse(bitmapPath);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        context.startActivity(Intent.createChooser(intent, "Share"));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post profile = posts.get(position);
        holder.title.setText(profile.getTitle());
        holder.username.setText(profile.getUsername());
        holder.date.setText(profile.getFormattedTimestamp());
        holder.description.setText(profile.getDescription());
        if (profile.getImageUrl() != null) {
            Glide.with(context).load(profile.getImageUrl()).placeholder(R.drawable.img1).into(holder.profileImage);
        } else  {
            Glide.with(context).load(R.drawable.img1).into(holder.profileImage);
        }

        //holder.profileImage.setImageResource(profile.getImageResId());
        if (profile.getLikes() != null) {
            if (profile.getLikes().containsKey(currentUserUid)) {
                holder.likeTv.setText("Liked");
                holder.likeIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_liked));
            } else {
                holder.likeTv.setText("Like");
                holder.likeIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like));
            }
            if (profile.getLikes().size() == 1) {
                holder.likesCount.setText(String.valueOf(profile.getLikes().size()) + " like");
            } else {
                holder.likesCount.setText(String.valueOf(profile.getLikes().size()) + " likes");
            }
        } else {
            holder.likeTv.setText("Like");
            holder.likeIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like));
            holder.likesCount.setText("0 like");
        }

        holder.likeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iAdapterClick.onLikeClick(position, profile.getPostId());
            }
        });

        holder.shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntent(screenShot(holder.itemView));
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
        TextView likesCount, likeTv;
        LinearLayout likeLayout;
        ImageView likeIv;
        LinearLayout shareLayout;

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
            shareLayout = itemView.findViewById(R.id.shareLayout);
            likeTv = itemView.findViewById(R.id.likeTv);
        }
    }


}
