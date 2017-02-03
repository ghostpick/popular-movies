package com.popularmovies.ghostpick.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String THE_MOVIE_DB_BASE   = "https://api.themoviedb.org/3/movie/";

    private static final String QUERY_API_KEY       = "api_key";
    private static final String QUERY_LANGUAGE      = "language";
    private static final String QUERY_PAGE          = "page";

    private static final String PARAM_API_KEY       = "PARAM_API_KEY";

    static final String param_language              = "en-US";
    static final int param_page                     = 1;


    public static URL buildUrl(String filterMovie) {

        Uri builtUri = Uri.parse(THE_MOVIE_DB_BASE + filterMovie).buildUpon()
                .appendQueryParameter(QUERY_API_KEY, PARAM_API_KEY)
                .appendQueryParameter(QUERY_LANGUAGE, param_language)
                .appendQueryParameter(QUERY_PAGE, Integer.toString(param_page))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}