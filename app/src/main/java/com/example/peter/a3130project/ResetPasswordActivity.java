package com.example.peter.a3130project;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/** ResetPasswordActivity
 *
 * @author Peter Lee
 * @author Joanna Bistekos
 */
public class ResetPasswordActivity extends AppCompatActivity {
    private String newPassword, email, oldPassword;
    private FirebaseUser user;
    private EditText et_email, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);
        emailField = (EditText) findViewById(R.id.et_email);
        passwordField =(EditText) findViewById(R.id.et_password);
        
    }
    public void clickReset(View view) {
        newPassword = et_password.getText();
        email = et_email.getText();

        //TODO: check to make sure user is valid

        //TODO: check to make sure user is in firebase

        //TODO: check that this password is valid

        //TODO: retrieve old password from database

        changePassword();
        
    }
    public void changePassword() {
        user.updatePassword(newPassword);
        AuthCredential cred = EmailAuthProvider.getCredential(email, oldPassword); //todo oldtask
        user.reauthenticate(cred).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task ) {
                    if (task.isSuccessful()) {
                        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(Task<Void> task) {
                                }
                            });
                    }
                }
            });
        
        
    }

}

