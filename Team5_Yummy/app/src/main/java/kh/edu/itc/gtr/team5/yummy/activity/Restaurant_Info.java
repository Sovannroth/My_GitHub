package kh.edu.itc.gtr.team5.yummy.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kh.edu.itc.gtr.team5.yummy.R;
import kh.edu.itc.gtr.team5.yummy.adapter.RestaurantAdapter;
import kh.edu.itc.gtr.team5.yummy.auth.SigninActivity;
import kh.edu.itc.gtr.team5.yummy.model.Restaurant;
import kh.edu.itc.gtr.team5.yummy.toolbar.ToolbarActivity;

import static android.content.ContentValues.TAG;

public class Restaurant_Info extends ToolbarActivity implements RestaurantAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private RestaurantAdapter mRestaurantAdapter;
    private ArrayList<Restaurant> mRestaurantList;
    private RequestQueue mRequestQueue;
    private FirebaseAuth myAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant__info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.bar_layout);
        setSupportActionBar(toolbar);

        myAuth = FirebaseAuth.getInstance();
        FirebaseUser user = myAuth.getCurrentUser();
        if (user != null) {
            String mName = user.getDisplayName();
            getSupportActionBar().setTitle(mName);
        }


        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRestaurantList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }


    private void parseJSON() {
        String url = "http://yummy.akana.xyz/restaurants?lat=11.5995729&lng=104.8950908";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String Name = hit.getString("name");
                                int Rating = hit.getInt("rating");
                                int PrepareTime = hit.getInt("prepare_time");
                                String ImageUrl = hit.getString("image_url");
                                mRestaurantList.add(new Restaurant(Name, Rating, PrepareTime, ImageUrl));
                            }
                            mRestaurantAdapter = new RestaurantAdapter(Restaurant_Info.this, mRestaurantList);
                            mRecyclerView.setAdapter(mRestaurantAdapter);
                            mRestaurantAdapter.setOnItemClickListener(Restaurant_Info.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }
    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(this, SubmitOrderActivity.class);
        i.putExtra("name", mRestaurantList.get(position).getmName());
        startActivity(i);
    }
}