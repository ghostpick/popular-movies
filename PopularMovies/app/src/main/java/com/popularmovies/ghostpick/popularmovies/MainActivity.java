package com.popularmovies.ghostpick.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.popularmovies.ghostpick.popularmovies.data.Movie;
import com.popularmovies.ghostpick.popularmovies.data.PopularMoviesPreferences;
import com.popularmovies.ghostpick.popularmovies.utilities.NetworkUtils;
import com.popularmovies.ghostpick.popularmovies.utilities.JsonUtils;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity  extends AppCompatActivity implements MovieAdapter.MoviesAdapterOnClickHandler {

    private RecyclerView    rv_movies;
    private TextView        tv_ErrorMessage;
    private MovieAdapter    movieAdapter;
    private ProgressBar     progressBar;

    //Mappings movies data onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        // Error message
        tv_ErrorMessage = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager manager = new GridLayoutManager(this, 2);

        // RecyclerView with moviesGrid
        rv_movies = (RecyclerView) findViewById(R.id.recyclerview_movies);
        rv_movies.setLayoutManager(manager);
        rv_movies.setHasFixedSize(true);

        //Linking movies data with the views
        movieAdapter = new MovieAdapter(this);

        //Setting the adapter attaches it to the RecyclerView
        rv_movies.setAdapter(movieAdapter);

        // Setting loadingIndicator
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // Load movies data
        loadMoviesData();
    }

    //Handle RecyclerView item click.
    @Override
    public void onClick(String weatherForDay) {
        Context context = this;
        Class destinationClass = MovieDetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, weatherForDay);
        startActivity(intentToStartDetailActivity);
    }

    // Toolbar create
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Get a handle on the menu inflater
        MenuInflater inflater = getMenuInflater();
        // Inflate menu layout to this menu
        inflater.inflate(R.menu.movie, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    // Toolbar click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_sortByNowPlaying) {
            movieAdapter.setMovieData(null);
            loadMoviesData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    //Get the movies data in the background
    private void loadMoviesData() {
        showMoviesDataView();

        String defaultFilver = PopularMoviesPreferences.getPreferredWeatherLocation(this);
        new FetchMovieTask().execute(defaultFilver);
    }

    //Show the currently visible data, then hide the error view
    private void showMoviesDataView() {
        tv_ErrorMessage.setVisibility(View.INVISIBLE);
        rv_movies.setVisibility(View.VISIBLE);
    }

    //Hide the currently visible data, then show the error view
    private void showErrorMessage() {
        rv_movies.setVisibility(View.INVISIBLE);
        tv_ErrorMessage.setVisibility(View.VISIBLE);
    }


    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            // If there's nothing to look up
            if (params.length == 0)
                return null;

            String        defaultFilver    = params[0];
            URL           moviesRequestUrl = NetworkUtils.buildUrl(MainActivity.this, defaultFilver);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
                return JsonUtils.getMoviesFromJson(MainActivity.this, jsonMovieResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            progressBar.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMoviesDataView();
                movieAdapter.setMovieData(movies);
            } else {
                showErrorMessage();
            }
        }
    }
}