package android.example.guardiannewsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsArticle>> {
    private static final String LOG_TAG = MainActivity.class.getName();

    private static final String API_URL = "https://content.guardianapis.com/search?&show-tags=contributor&api-key=test";
    private static final int LOADER_ID = 1;
    TextView emptyListTextView;
    ProgressBar progressBar;
    ListView listView;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressCircular);
        emptyListTextView = findViewById(R.id.emptyListText);

        mAdapter = new NewsAdapter(this, new ArrayList<NewsArticle>());

        listView.setAdapter(mAdapter);
        listView.setEmptyView(emptyListTextView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsArticle newsItem = mAdapter.getItem(position);
                Uri earthquakeUri = Uri.parse(newsItem.getmUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(websiteIntent);
            }
        });

        Context context = this;
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            emptyListTextView.setText("No internet connection.");
        }

    }


    @NonNull
    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int id, @Nullable Bundle args) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);


        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );
        Uri baseUri = Uri.parse(API_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("limit", "100");
        uriBuilder.appendQueryParameter("orderby", orderBy);

        Log.i("llll", uriBuilder.toString());
        return new NewsLoader(this, uriBuilder.toString());
//        return new NewsLoader(this, API_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsArticle>> loader, List<NewsArticle> data) {
        emptyListTextView.setText("No news articles found.");
        progressBar.setVisibility(View.GONE);
        mAdapter.clear();

        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsArticle>> loader) {
        mAdapter.clear();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}

