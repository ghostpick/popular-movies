package com.popularmovies.ghostpick.popularmovies.utilities;

import com.popularmovies.ghostpick.popularmovies.data.Movie;
import com.popularmovies.ghostpick.popularmovies.data.Vars;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class JsonUtils {

    public static ArrayList<Movie> getMoviesFromJson(String movies) throws JSONException {

        final String jsonMessageCode    = Vars.jsonMovie0;
        final String jsonResults        = Vars.jsonMovie1;
        final String jsonTitle          = Vars.jsonMovie2;
        final String jsonOverview       = Vars.jsonMovie3;
        final String jsonReleaseDate    = Vars.jsonMovie4;
        final String jsonVoteAverage    = Vars.jsonMovie5;
        final String jsonImage          = Vars.jsonMovie6;

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