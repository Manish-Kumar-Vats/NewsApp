package android.example.guardiannewsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private QueryUtils() {

    }

    public static List<NewsArticle> fetchArticleData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }

        List<NewsArticle> newsList = extractFromJson(jsonResponse);

        return newsList;
    }

    public static List<NewsArticle> extractFromJson(String newsJSON) {

        String author="";
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<NewsArticle> articles = new ArrayList<>();

        try {

            JSONObject root = new JSONObject(newsJSON);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {

                JSONObject currentArticle = results.getJSONObject(i);

                String sectionName = currentArticle.getString("sectionName");

                String title = currentArticle.getString("webTitle");

                String date = currentArticle.getString("webPublicationDate");

                String url = currentArticle.getString("webUrl");

                JSONArray tags = currentArticle.getJSONArray("tags");

                if(tags.length() == 0)
                    author = "Unknown";

                else {

                    for (int j = 0; j < tags.length(); j++) {

                        try {

                            JSONObject authorName = tags.getJSONObject(j);

                            author = authorName.getString("webTitle");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }

                NewsArticle newsItem = new NewsArticle(title, sectionName, date, author, url);

                articles.add(newsItem);
            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the article JSON results", e);
        }

        return articles;
    }

    private static URL createUrl(String stringUrl) {

        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("Exception", "Problem building the URL ", e);
        }

        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        Log.d("Exception", "makeHttpRequest is called...");

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }

            else {
                Log.e("Exception", "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e("Exception", "Problem retrieving the article JSON results.", e);
        }

        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        Log.d("Exception", "readFromStream is called...");

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }
}
