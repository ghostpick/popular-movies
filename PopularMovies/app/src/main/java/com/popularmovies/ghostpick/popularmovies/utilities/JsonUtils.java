package com.popularmovies.ghostpick.popularmovies.utilities;

import android.content.Context;
import com.popularmovies.ghostpick.popularmovies.R;
import com.popularmovies.ghostpick.popularmovies.data.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class JsonUtils {

    public static ArrayList<Movie> getMoviesFromJson(Context context, String movies) throws JSONException {

        final String jsonMessageCode    = context.getString(R.string.jsonMovie0);
        final String jsonResults        = context.getString(R.string.jsonMovie1);
        final String jsonTitle          = context.getString(R.string.jsonMovie2);
        final String jsonOverview       = context.getString(R.string.jsonMovie3);
        final String jsonReleaseDate    = context.getString(R.string.jsonMovie4);
        final String jsonVoteAverage    = context.getString(R.string.jsonMovie5);
        final String jsonImage          = context.getString(R.string.jsonMovie6);

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