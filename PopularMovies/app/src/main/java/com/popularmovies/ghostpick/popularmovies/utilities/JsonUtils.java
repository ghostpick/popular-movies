package com.popularmovies.ghostpick.popularmovies.utilities;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;

public class JsonUtils {

    public static String[] getMoviesFromJson(Context context, String movies) throws JSONException {

        final String jsonResults        = "results";
        final String jsonTitle          = "title";
        final String jsonMessageCode    = "cod";


        String[] parsedData = null;
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
        parsedData = new String[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {

            String description;

            /* Get the JSON object representing the day */
            JSONObject itemMovie = moviesArray.getJSONObject(i);

            description = itemMovie.getString(jsonTitle);

            parsedData[i] = description;
        }

        return parsedData;
    }
}