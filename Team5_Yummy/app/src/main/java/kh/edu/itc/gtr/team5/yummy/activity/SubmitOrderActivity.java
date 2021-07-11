package kh.edu.itc.gtr.team5.yummy.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import kh.edu.itc.gtr.team5.yummy.R;
import kh.edu.itc.gtr.team5.yummy.adapter.SubmitOrderAdapter;
import kh.edu.itc.gtr.team5.yummy.model.SubmitOrder;
import kh.edu.itc.gtr.team5.yummy.toolbar.ToolbarLogoutActivity;

public class SubmitOrderActivity extends ToolbarLogoutActivity implements SubmitOrderAdapter.OnItemClickListener{
    private RecyclerView mOrderRecyclerView;
    private SubmitOrderAdapter mSubmitOrderAdapter;
    private ArrayList<SubmitOrder> mSubmitOrderList;
    private RequestQueue mRequestQueue;
    private String mName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.bar_layout);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        this.mName = i.getStringExtra("name");
        getSupportActionBar().setTitle(this.mName);

        mOrderRecyclerView = findViewById(R.id.order_recycler_view);
        mOrderRecyclerView.setHasFixedSize(true);
        mOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSubmitOrderList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }
    private void parseJSON() {
        String url = "http://yummy.akana.xyz/restaurants/60a7a897d0c4e30c14a0acec/menus";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String Name = hit.getString("name");
                                int Price = hit.getInt("price");
                                String ImageUrl = hit.getString("image_url");
                                mSubmitOrderList.add(new SubmitOrder(Name, Price, ImageUrl));
                            }
                            mSubmitOrderAdapter = new SubmitOrderAdapter(SubmitOrderActivity.this, mSubmitOrderList);
                            mOrderRecyclerView.setAdapter(mSubmitOrderAdapter);
                            mSubmitOrderAdapter.setOnItemClickListener(SubmitOrderActivity.this);
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

    }

    public void openOrderSuccess(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(SubmitOrderActivity.this);
        builder.setMessage("Are you sure to place the order?");
        builder.setCancelable(false);
        builder.setNegativeButton("BUY NOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(SubmitOrderActivity.this, OrderSuccessActivity.class);
                startActivity(i);
            }
        });

        builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        positiveButton.setTextColor(Color.parseColor("#FF4081"));
        negativeButton.setTextColor(Color.parseColor("#FF6200EE"));
    }
    private void sendWorkPostRequest() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://yummy.akana.xyz/orders";
            String Token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjNkOWNmYWE4OGVmMDViNDI0YmU2MjA1ZjQ2YjE4OGQ3MzI1N2JjNDIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20veXVtbXktZm9vZC1kZWxpdmVyeSIsImF1ZCI6Inl1bW15LWZvb2QtZGVsaXZlcnkiLCJhdXRoX3RpbWUiOjE2MjE2NDg4MDksInVzZXJfaWQiOiJDMlJ4U05mZVFjUmI3SDdOR093TU5jRVBXRncyIiwic3ViIjoiQzJSeFNOZmVRY1JiN0g3TkdPd01OY0VQV0Z3MiIsImlhdCI6MTYyMTY0ODgxMCwiZXhwIjoxNjIxNjUyNDEwLCJlbWFpbCI6ImFrYW5hQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJha2FuYUBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.vgTCSqYZwC38V_XAaw_aGnOF4_cHkJyzShxY9X3ju9tLmKItKaHFSHaOpmsRkCEgpq7_mYHo9p5MnT0hV5bof_wXbM-VtTpNVC66aoMBbY3PbQLEA7UdNl6IgZupW9LmhufxbklHU790P8PazPsyoJRXXiuy6_gWykQXpq5lBnwoaCC_VPa_U06RMRxlgxv_ZSvRJTDbSNAzjhayrcrxAlJH-GeK4m2ZTeIAyqYzYMJCfbjtmwxIHiqTkoo1wCw6v72Mx-cd0sd3h50mSKTLN2amiXv3zaMsjCPqQGBdr9Iidl0ar6Z1YCrslpNL2CMjrN_wB00FVuG5X1ko5nEG9w";
            List<String> number = new ArrayList<>();
            number.add("1");
            number.add("2");
            number.add("3");
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("token", Token);
            jsonBody.put("menus", number);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}