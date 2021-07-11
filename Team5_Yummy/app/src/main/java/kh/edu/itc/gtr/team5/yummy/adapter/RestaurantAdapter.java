package kh.edu.itc.gtr.team5.yummy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kh.edu.itc.gtr.team5.yummy.R;
import kh.edu.itc.gtr.team5.yummy.model.Restaurant;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ExampleViewHolder>{
    private Context mContext;
    private ArrayList<Restaurant> mRestaurantList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public RestaurantAdapter(Context context, ArrayList<Restaurant> exampleList) {
        mContext = context;
        mRestaurantList = exampleList;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_restaurant, parent, false);
        return new ExampleViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Restaurant currentItem = mRestaurantList.get(position);
        String ImageUrl = currentItem.getmImageUrl();
        String Name = currentItem.getmName();
        int Rating = currentItem.getmRating();
        int prepareTime = currentItem.getmPrepareTime();
        holder.myName.setText(Name);
        holder.myRating.setText("" + Rating);
        holder.myPrepareTime.setText(prepareTime + " mn");
        Picasso.get().load(ImageUrl).fit().centerInside().into(holder.myImageUrl);
    }
    @Override
    public int getItemCount() {
        return mRestaurantList.size();
    }
    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView myName;
        public TextView myRating;
        public TextView myPrepareTime;
        public ImageView myImageUrl;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            myName = itemView.findViewById(R.id.id_name);
            myRating = itemView.findViewById(R.id.id_rating);
            myPrepareTime = itemView.findViewById(R.id.id_prepareTime);
            myImageUrl = itemView.findViewById(R.id.id_image_url);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
