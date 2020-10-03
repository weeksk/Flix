package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.provider.FontsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;




public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  static  int LESS_POPULAR = 0;
    private static int MORE_POPULAR = 1;


    Context context;
    List<Movie> movies;


    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;

    }

//    @Override





    // Usually involves inflating a layout from XML and returning the holder
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d("MovieAdapter", "onCreateViewHolder");
//        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
//        return new ViewHolder(movieView);
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView;
        if (viewType == LESS_POPULAR) {
            movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
            return new ViewHolder(movieView);

        }
        else {
            movieView = LayoutInflater.from(context).inflate(R.layout.item_movie_popular, parent, false);
            return new PopularViewHolder(movieView);

        }

    }



//     Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the View Holder
        if (getItemViewType(position) == LESS_POPULAR) {
            ((ViewHolder) holder).bind(movie);
        }
        else {
            ((PopularViewHolder) holder).bind(movie);
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public int getItemViewType(int position) {
        Movie movie = movies.get(position);
        if (movie.getPopularity() >= 7.5) {
            return MORE_POPULAR;
        }
        else {
            return LESS_POPULAR;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;

            // If phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imageUrl = backdrop image
                imageUrl = movie.getBackdropPath();
            }
            else {
                // then imageUrl = poster image
                imageUrl = movie.getPosterPath();
            }


            // Placeholder and error images
            int radius = 20; // corner radius, higher value = more rounded
            Glide
                    .with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.progress_animation)
                    .transform(new RoundedCorners(radius))
                    .override(342,513)
                    .into(ivPoster);
//            1. Register click listener on the whole row
//            2. Navigate to a new activity on tap
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }



    public class PopularViewHolder extends RecyclerView.ViewHolder {


        ImageView backdrop;
        RelativeLayout container;



        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            backdrop = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
            int radius = 20; // corner radius, higher value = more rounded

            // Placeholder and error images
            Glide
                    .with(context)
                    .load(movie.getBackdropPath())
                    .placeholder(R.drawable.progress_animation)
                    .transform(new RoundedCorners(radius))
                    .override(342,513)
                    .into(backdrop);
//           1. Register click listener on the whole row
//           2. Navigate to a new activity on tap
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}
