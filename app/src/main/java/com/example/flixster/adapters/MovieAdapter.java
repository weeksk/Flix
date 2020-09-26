package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;




public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;


    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;

    }

//    @Override
//    public int getItemViewType(Movie movie) {
//        if (movie.getPopularity() >= 7.5) {
//            return 1;
//        } else if (movie.getPopularity() < 7.5) {
//            return 0;
//        }
//        return -1;
//    }



    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//
//        switch (viewType) {
//            case 1:
//                View movieViewPopular = LayoutInflater.from(context).inflate(R.layout.item_movie_popular, parent, false);
//                ViewHolder viewHolder = new ViewHolder(movieViewPopular);
//                return viewHolder;
//            case 0:
//                View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
//                ViewHolder viewHolderTwo = new ViewHolder(movieView);
//                return viewHolderTwo;
//            default:
//                View movieViewTwo = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
//                ViewHolder viewHolderThree = new ViewHolder(movieViewTwo);
//                return viewHolderThree;
//        }
//
//    }




//     Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the View Holder
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);


        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;

            // If phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imageUrl = backdrop image
                imageUrl = movie.getBackdropPath();
            }

            // If movie is popular
            else if (movie.getPopularity() >= 7.5) {
                // then imageUrl = backdrop image
                imageUrl = movie.getBackdropPath();
            }


            else {
                // else imageUrl = poster image
                imageUrl = movie.getPosterPath();

            }

            int radius = 20; // corner radius, higher value = more rounded

            // Placeholder and error images
            Glide
                    .with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.progress_animation)
                    .transform(new RoundedCorners(radius))
                    .override(342,513)
                    .into(ivPoster);

        }
    }
}
