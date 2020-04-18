package android.example.guardiannewsapp;


import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsArticle>> {

    private static final String LOG_TAG = NewsLoader.class.getName();

    private String mUrl;


    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<NewsArticle> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<NewsArticle> newsArticleList = QueryUtils.fetchArticleData(mUrl);

        return newsArticleList;

    }
}