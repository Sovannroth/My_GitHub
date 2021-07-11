package kh.edu.itc.gtr.team5.yummy.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import kh.edu.itc.gtr.team5.yummy.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText myEmail;
    Button myResetPassword;
    private FirebaseAuth myAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        myEmail=findViewById(R.id.email);
        myAuth=FirebaseAuth.getInstance();

        myResetPassword=findViewById(R.id.resetPassword);
        myResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myResetPassword();
            }
        });
    }
    private void myResetPassword() {
        String email = myEmail.getText().toString().trim();
        if (email.isEmpty()){
            myEmail.setError("Email is required!");
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            myEmail.setError("Please provide valid email!");
            myEmail.requestFocus();
            return;
        }
        else {
            myAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ForgotPasswordActivity.this, SigninActivity.class));
                    }
                    else {
                        Toast.makeText(ForgotPasswordActivity.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}