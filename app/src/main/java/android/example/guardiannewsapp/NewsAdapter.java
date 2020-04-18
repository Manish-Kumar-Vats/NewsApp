package android.example.guardiannewsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends ArrayAdapter<NewsArticle> {
    public NewsAdapter( Context context, List<NewsArticle> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item_layout, parent, false);
        }

        NewsArticle currentArticle =  getItem(position);

        TextView articleTitle = convertView.findViewById(R.id.title);

        TextView articleSection = convertView.findViewById(R.id.section);

        TextView articleAuthor = convertView.findViewById(R.id.author);

        TextView articleData = convertView.findViewById(R.id.date);

        articleTitle.setText(currentArticle.getmTitle());

        articleSection.setText(currentArticle.getmNameSection());

        articleAuthor.setText(currentArticle.getmAuthor());

        String formattedDate = formatDate(currentArticle.getmDataPublished());

        articleData.setText(formattedDate);

        return convertView;
    }


    private String formatDate(String date) {
        SimpleDateFormat jsonFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        try {
            Date parsedJson = jsonFormat.parse(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy", Locale.US);
            return dateFormat.format(parsedJson);
        } catch (ParseException e) {
            return null;
        }
    }
}
