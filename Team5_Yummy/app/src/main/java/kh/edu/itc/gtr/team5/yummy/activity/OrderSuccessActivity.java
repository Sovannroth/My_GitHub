package kh.edu.itc.gtr.team5.yummy.activity;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import kh.edu.itc.gtr.team5.yummy.R;
import kh.edu.itc.gtr.team5.yummy.activity.Restaurant_Info;
import kh.edu.itc.gtr.team5.yummy.toolbar.ToolbarLogoutActivity;

public class OrderSuccessActivity extends ToolbarLogoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_success);
        Toolbar toolbar = (Toolbar) findViewById(R.id.bar_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order Success");
    }
    public void backToHome(View view){
        startActivity(new Intent(this, Restaurant_Info.class));
    }
}