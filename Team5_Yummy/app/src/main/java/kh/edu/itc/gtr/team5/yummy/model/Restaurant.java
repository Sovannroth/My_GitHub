package kh.edu.itc.gtr.team5.yummy.model;

public class Restaurant {
    private String mName;
    private int mRating;
    private int mPrepareTime;
    private String mImageUrl;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmRating() {
        return mRating;
    }

    public void setmRating(int mRating) {
        this.mRating = mRating;
    }

    public int getmPrepareTime() {
        return mPrepareTime;
    }

    public void setmPrepareTime(int mPrepareTime) {
        this.mPrepareTime = mPrepareTime;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public Restaurant(String mName, int mRating, int mPrepareTime, String mImageUrl) {
        this.mName = mName;
        this.mRating = mRating;
        this.mPrepareTime = mPrepareTime;
        this.mImageUrl = mImageUrl;
    }
}
