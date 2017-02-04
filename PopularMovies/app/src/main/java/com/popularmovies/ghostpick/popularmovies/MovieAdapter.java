package com.popularmovies.ghostpick.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.popularmovies.ghostpick.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private ArrayList<Movie> movieData;

    // Handler that makes it easy for an Activity to interface with the RecyclerView
    private final MoviesAdapterOnClickHandler mClickHandler;

    // Interface that receives onClick messages
    interface MoviesAdapterOnClickHandler {
        void onClick(Movie  movie);
    }

    // Handler is called when an item is clicked
    MovieAdapter(MoviesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    // Caches of the children views for a movies list item
    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        final ImageView img_movie;

         MovieAdapterViewHolder(View view) {
            super(view);
            img_movie = (ImageView) view.findViewById(R.id.tv_movie_data);
            view.setOnClickListener(this);
        }

        // This gets called by the child views during a click
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = movieData.get(adapterPosition);

            mClickHandler.onClick(movie);
        }
    }

    /* Called when each new ViewHolder is created. This happens when the RecyclerView is laid out.
     * Enough ViewHolders will be created to fill the screen and allow for scrolling.*/
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_movies;
        LayoutInflater inflater = LayoutInflater.from(context);
        //boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    /* Called by the RecyclerView to display the data at the specified position. In this method, is updated
     * the contents of the ViewHolder to display the movie details for this particular position.*/
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie movie = movieData.get(position);

        String posterPath = movie.getPhoto();

        Picasso.with(movieAdapterViewHolder.img_movie.getContext())
                .load("http://image.tmdb.org/t/p/w342/" + posterPath)
                .into(movieAdapterViewHolder.img_movie);
    }

    /* Returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.*/
    @Override
    public int getItemCount() {
        if (null == movieData) return 0;
        return movieData.size();
    }

    /* Used to set the movies on a MovieAdapter if we've already created one. This is handy when
     * we get new data from the web but don't want to create a new MovieAdapter to display it.*/
    void setMovieData(ArrayList<Movie> movieData) {
        this.movieData = movieData;
        notifyDataSetChanged();
    }
}