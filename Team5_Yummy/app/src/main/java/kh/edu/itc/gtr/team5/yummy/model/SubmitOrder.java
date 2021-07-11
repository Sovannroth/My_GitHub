package kh.edu.itc.gtr.team5.yummy.model;


public class SubmitOrder {
    public int txtQuantity = 0;
    public int txtTotal = 0;
    private String mName;
    private int mPrice;
    private String mImageUrl;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmPrice() {
        return mPrice;
    }

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public SubmitOrder(String mName, int mPrice, String mImageUrl) {
        this.mName = mName;
        this.mPrice = mPrice;
        this.mImageUrl = mImageUrl;
    }
}
