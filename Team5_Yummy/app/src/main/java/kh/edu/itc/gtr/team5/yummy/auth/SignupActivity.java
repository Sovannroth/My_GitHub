package kh.edu.itc.gtr.team5.yummy.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import kh.edu.itc.gtr.team5.yummy.R;

public class SignupActivity extends AppCompatActivity {
    TextView btn;
    private EditText myUsername, myEmail, myDate, myPassword, myComfirmPassword;
    Button mySignup;
    private FirebaseAuth myAuth;
    private ProgressDialog myLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        btn=findViewById(R.id.signin);
        myUsername=findViewById(R.id.username);
        myEmail=findViewById(R.id.email);
        myDate=findViewById(R.id.dateOfBirth);
        myPassword=findViewById(R.id.password);
        myComfirmPassword=findViewById(R.id.confirmPassword);
        myAuth=FirebaseAuth.getInstance();

        mySignup=findViewById(R.id.signup);
        mySignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCrededentials();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, SigninActivity.class));
            }
        });
    }
    private void checkCrededentials() {
        String username = myUsername.getText().toString();
        String email = myEmail.getText().toString();
        String date = myDate.getText().toString();
        String password = myPassword.getText().toString();
        String comfirmPassword = myComfirmPassword.getText().toString();
        if (username.isEmpty() || username.length()<5){
            showError(myUsername, "Username must be 5 character");
        }
        else if (email.isEmpty() || !email.contains("@")){
            showError(myEmail, " Email is not valid!");
        }
        else if (date.isEmpty() || !date.contains("/")){
            showError(myDate, " Must be have (/) inside date of birth!");
        }
        else if (password.isEmpty() || password.length()<7){
            showError(myPassword, "Password must be 7 character");
        }
        else if (comfirmPassword.isEmpty() || !comfirmPassword.equals(password)){
            showError(myComfirmPassword, "Password not match!");
        }
        else {
            myAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(SignupActivity.this, "This account already created", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        userProfile();
                        Toast.makeText(SignupActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignupActivity.this,SigninActivity.class);
                        startActivity(i);
                    }
                }
            });
        }
    }
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
    private void userProfile()
    {
        FirebaseUser user = myAuth.getCurrentUser();
        if(user!= null)
        {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(myUsername.getText().toString().trim())
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TESTING", "User profile updated.");
                            }
                        }
                    });
        }
    }

}