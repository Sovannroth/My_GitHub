package kh.edu.itc.gtr.team5.yummy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import kh.edu.itc.gtr.team5.yummy.R;
import kh.edu.itc.gtr.team5.yummy.auth.SigninActivity;
import kh.edu.itc.gtr.team5.yummy.auth.SignupActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second);
    }
    public void openSingInPage(View view){
        startActivity(new Intent(this, SigninActivity.class));
    }
    public void openSignUpPage(View view){
        startActivity(new Intent(this, SignupActivity.class));
    }
}