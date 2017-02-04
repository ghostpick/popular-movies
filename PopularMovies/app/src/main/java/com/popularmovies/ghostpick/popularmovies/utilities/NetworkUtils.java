package com.popularmovies.ghostpick.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.popularmovies.ghostpick.popularmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    public static URL buildUrl(Context context, String filterMovie) {

        final String THE_MOVIE_DB_BASE  = context.getString(R.string.THE_MOVIE_DB_BASE);
        final String QUERY_API_KEY      = context.getString(R.string.QUERY_API_KEY);
        final String QUERY_LANGUAGE     = context.getString(R.string.QUERY_LANGUAGE);
        final String QUERY_PAGE         = context.getString(R.string.QUERY_PAGE);
        final String PARAM_API_KEY      = context.getString(R.string.API_KEY);
        String param_language           = context.getString(R.string.PARAM_LANGUAGE);
        int param_page = 0;

        try {
            param_page = Integer.parseInt(context.getString(R.string.PARAM_PAGE));
        }catch(Exception e){
            e.printStackTrace();
        }


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

        Log.v("NetworkUtils", "Built URI " + url);

        return url;
    }

    //Returns the entire result from the HTTP response.
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