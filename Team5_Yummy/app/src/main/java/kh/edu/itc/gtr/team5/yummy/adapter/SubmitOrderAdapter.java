package kh.edu.itc.gtr.team5.yummy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kh.edu.itc.gtr.team5.yummy.R;
import kh.edu.itc.gtr.team5.yummy.model.SubmitOrder;

public class SubmitOrderAdapter extends RecyclerView.Adapter<SubmitOrderAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<SubmitOrder> mSubmitOrderList;
    private SubmitOrderAdapter.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(SubmitOrderAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
    public SubmitOrderAdapter(Context context, ArrayList<SubmitOrder> exampleList) {
        mContext = context;
        mSubmitOrderList = exampleList;
    }
    @Override
    public SubmitOrderAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_submit_order, parent, false);
        return new SubmitOrderAdapter.ExampleViewHolder(v);
    }
    @Override
    public void onBindViewHolder(SubmitOrderAdapter.ExampleViewHolder holder, int position) {
        SubmitOrder currentItem = mSubmitOrderList.get(position);
        String ImageUrl = currentItem.getmImageUrl();
        String Name = currentItem.getmName();
        int Price = currentItem.getmPrice();
        holder.myName.setText(Name);
        holder.myPrice.setText(Price + " $");
        Picasso.get().load(ImageUrl).fit().centerInside().into(holder.myImageUrl);


        holder.txtQuantity.setText(currentItem.txtQuantity + "");
        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBtn(position, holder.txtQuantity, -1);
            }
        });
        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBtn(position, holder.txtQuantity,1);
            }
        });

    }

    private void updateBtn(int position, TextView txtQuantity,  int i) {
        SubmitOrder currentItem = mSubmitOrderList.get(position);
        if(i > 0){
            currentItem.txtQuantity = currentItem.txtQuantity + 1;
        }
        else{
            if(currentItem.txtQuantity > 0){
                currentItem.txtQuantity = currentItem.txtQuantity - 1;
            }
        }
        txtQuantity.setText(currentItem.txtQuantity + "");


    }

    @Override
    public int getItemCount() {
        return mSubmitOrderList.size();
    }
    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView myName;
        public TextView myPrice;
        public ImageView myImageUrl;
        public TextView txtQuantity;
        public TextView txtTotal;
        public Button btnIncrease;
        public Button btnDecrease;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            myName = itemView.findViewById(R.id.id_orderName);
            myPrice = itemView.findViewById(R.id.id_orderPrice);
            myImageUrl = itemView.findViewById(R.id.id_orderImage_url);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            btnIncrease =itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
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
