package android.example.guardiannewsapp;

public class NewsArticle {

    String mTitle;

    String mNameSection;

    String mDataPublished;

    String mAuthor;

    String mUrl;

    public NewsArticle(String mTitle, String mNameSection, String mDataPublished, String mAuthor, String mUrl) {
        this.mTitle = mTitle;
        this.mNameSection = mNameSection;
        this.mDataPublished = mDataPublished;
        this.mAuthor = mAuthor;
        this.mUrl = mUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmNameSection() {
        return mNameSection;
    }

    public void setmNameSection(String mNameSection) {
        this.mNameSection = mNameSection;
    }

    public String getmDataPublished() {
        return mDataPublished;
    }

    public void setmDataPublished(String mDataPublished) {
        this.mDataPublished = mDataPublished;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "mTitle='" + mTitle + '\'' +
                ", mNameSection='" + mNameSection + '\'' +
                ", mDataPublished='" + mDataPublished + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
