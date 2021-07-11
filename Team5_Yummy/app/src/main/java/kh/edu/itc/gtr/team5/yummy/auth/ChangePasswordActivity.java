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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kh.edu.itc.gtr.team5.yummy.R;

import static android.service.controls.ControlsProviderService.TAG;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText oldPsw, newPsw, confirmPsw;
    Button changePsw;
    private FirebaseUser myFirebaseUser;
    private FirebaseAuth myFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldPsw=findViewById(R.id.oldPassword);
        newPsw=findViewById(R.id.newPassword);
        confirmPsw=findViewById(R.id.confirmPassword);
        myFirebaseAuth=FirebaseAuth.getInstance();
        myFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        changePsw=findViewById(R.id.changePassword);
        changePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePassword();
            }
        });
    }

    private void ChangePassword() {
        String oldPassword =oldPsw.getText().toString();
        String newPassword =newPsw.getText().toString();
        String confirmPassword =confirmPsw.getText().toString();
        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()){
            showError(oldPsw, "All fields are required!");
        }
        else if (newPassword.length()<7){
            showError(newPsw, "The new password length should be more than 6 character!");
        }
        else if (!confirmPassword.equals(newPassword)){
            showError(confirmPsw, "Confirm password does not match new password!");
        }
        else {
            AuthCredential credential = EmailAuthProvider.getCredential(myFirebaseUser.getEmail(),oldPassword);
            myFirebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        myFirebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    myFirebaseAuth.signOut();
                                    Toast.makeText(ChangePasswordActivity.this, "Change Password Successfully", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ChangePasswordActivity.this, SigninActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                }
                                else {
                                    Toast.makeText(ChangePasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }
    }
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}