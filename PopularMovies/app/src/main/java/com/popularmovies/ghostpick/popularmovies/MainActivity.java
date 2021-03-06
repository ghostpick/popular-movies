package com.popularmovies.ghostpick.popularmovies;

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
import com.popularmovies.ghostpick.popularmovies.data.Vars;
import com.popularmovies.ghostpick.popularmovies.utilities.NetworkUtils;
import com.popularmovies.ghostpick.popularmovies.utilities.JsonUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity  extends AppCompatActivity implements MovieAdapter.MoviesAdapterOnClickHandler {

    private RecyclerView    rv_movies;
    private TextView        tv_ErrorMessage;
    private MovieAdapter    movieAdapter;
    private ProgressBar     progressBar;

    private static String   viewState = "default";
    static final String STATE = "default";
    private String mState;


    //Mappings movies data onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String onCreateState = "";

        // Probably initialize members with default values for a new instance
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

        // Restore value of members from saved state
        if (savedInstanceState != null) {
            mState = savedInstanceState.getString(STATE);
            onCreateState = mState;
        }
        else if(!viewState.equals("") ) {
            mState = viewState;
            onCreateState = viewState;
        }

        switch (onCreateState ) {
            case "default":
                loadMoviesData();
                break;
            case "sortByPopular":
                loadMoviesData(Vars.movieFilter_Popular);
                break;
            case "sortByTopRated":
                loadMoviesData(Vars.movieFilter_TopRated);
                break;
        }
    }

    //Handle RecyclerView item click.
    @Override
    public void onClick(Movie movie) {

        Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Movie.PARCELABLE_KEY, movie);
        getApplicationContext().startActivity(intent);
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

        // check internet connection then run an selected option
        if (NetworkUtils.isNetworkConnected(this)) {

            int id = item.getItemId();

            if (id == R.id.item_sortByNowPlaying) {
                movieAdapter.setMovieData(null);
                loadMoviesData();
                mState = "default";
                viewState = mState;
                return true;
            } else if (id == R.id.item_sortByPopular) {
                movieAdapter.setMovieData(null);
                loadMoviesData(Vars.movieFilter_Popular);
                mState= "sortByPopular";
                viewState = mState;
                return true;
            } else if (id == R.id.item_sortByTopRated) {
                movieAdapter.setMovieData(null);
                loadMoviesData(Vars.movieFilter_TopRated);
                mState = "sortByTopRated";
                viewState = mState;
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
        else{
            showErrorMessage();
            return false;
        }
    }

    // Save current state
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
          super.onSaveInstanceState(savedInstanceState);
          savedInstanceState.putString(STATE, mState);

    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mState = savedInstanceState.getString(STATE);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    //Get the movies data in the background
    private void loadMoviesData(String... params) {
        String movieFilter = "";

        showMoviesDataView();

        if (params.length == 0)
            movieFilter = Vars.movieFilter_nowPlaying;
        else
            movieFilter = params[0];

        new FetchMovieTask().execute(movieFilter);
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
                return JsonUtils.getMoviesFromJson(jsonMovieResponse);

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