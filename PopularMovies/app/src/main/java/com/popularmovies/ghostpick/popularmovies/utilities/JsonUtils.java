package com.popularmovies.ghostpick.popularmovies.utilities;

import android.content.Context;

import com.popularmovies.ghostpick.popularmovies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class JsonUtils {

    public static ArrayList<Movie> getMoviesFromJson(Context context, String movies) throws JSONException {

        final String jsonResults        = "results";
        final String jsonTitle          = "title";
        final String jsonOverview       = "overview";
        final String jsonReleaseDate    = "release_date";
        final String jsonVoteAverage    = "vote_average";
        final String jsonImage          = "poster_path";

        final String jsonMessageCode    = "cod";

        JSONObject moviesJson = new JSONObject(movies);

        /* Is there an error? */
        if (moviesJson.has(jsonMessageCode)) {
            int errorCode = moviesJson.getInt(jsonMessageCode);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray moviesArray = moviesJson.getJSONArray(jsonResults);
        ArrayList<Movie> arr_movie = new ArrayList<Movie>();

        for (int i = 0; i < moviesArray.length(); i++) {
            Movie movie = new Movie();

            /* Get the JSON object representing the day */
            JSONObject itemMovie = moviesArray.getJSONObject(i);

            movie.setTitle(itemMovie.getString(jsonTitle));
            movie.setOverview(itemMovie.getString(jsonOverview));
            movie.setReleaseDate(itemMovie.getString(jsonReleaseDate));
            movie.setVoteAverage(itemMovie.getString(jsonVoteAverage));
            movie.setPhoto(itemMovie.getString(jsonImage));

            arr_movie.add(movie);
        }

        return arr_movie;
    }
}