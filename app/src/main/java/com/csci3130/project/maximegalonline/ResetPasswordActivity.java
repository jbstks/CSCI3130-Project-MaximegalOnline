package com.csci3130.project.maximegalonline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/** ResetPasswordActivity
 *
 * @author Peter Lee
 * @author Joanna Bistekos
 */
public class ResetPasswordActivity extends AppCompatActivity {
    private String newPassword, email, oldPassword;
    private FirebaseUser user;
    private EditText et_email, et_password;
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    private String b00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        db = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public void clickReset(View view) {
        newPassword = et_password.getText().toString();
        email = et_email.getText().toString();

        switch(LoginChecker.checkLogin(email,newPassword)) {

        case EMPTY_USER:
            Log.d("emaillen", "0");
            
            et_email.setError(getString(R.string.error_field_required),null);
            et_email.requestFocus();
            break;
            
        case EMPTY_PASSWORD:
            et_password.setError(getString(R.string.error_field_required), null);
            
            et_password.requestFocus();
            Log.d("passwlen", "0");
            break;
            
        case SHORT_USER:
            Log.d("emaillen", "less8");
            et_email.setError(getString(R.string.error_invalid_email),null);
            et_email.requestFocus();
            break;
            
        case SHORT_PASSWORD:
            Log.d("passwlen", "less8");
            et_password.setError(getString(R.string.error_invalid_password), null);
            et_password.requestFocus();
            break;

        case OK:
            db.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    b00 = queryNameInDatabase(dataSnapshot);
                    if (b00!=null) {
                        changePassword();
                    }
                    else{
                        //give feedback
                        Toast.makeText(ResetPasswordActivity.this, "Not updated. User does not exist.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError e){
                }
            });
        }
    }

    /**
     * Obtain the password from the database
     * returns true if name is found; false otherwise
     **/
    private String queryNameInDatabase(DataSnapshot dataSnapshot) {
        Log.d("query","start");
        for (DataSnapshot bentry : dataSnapshot.child("students").getChildren()) {
            Log.d("query","thang");
            if (bentry.child("email").getValue(String.class).equals(email)) {
                oldPassword = bentry.child("password").getValue(String.class);
                return bentry.getKey();
            }
        }
        return null;
    }
    
    public void changePassword() {
        Log.d("change password", oldPassword);
        AuthCredential cred = EmailAuthProvider.getCredential(email, oldPassword);
        mAuth.signInWithEmailAndPassword(email, oldPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task ) {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                db.getReference().child("students").child(b00).child("password").setValue(newPassword);
                user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(Task<Void> task) {
                        Toast.makeText(ResetPasswordActivity.this, "Password updated successfully.", Toast.LENGTH_SHORT).show();
                    }
                });
                mAuth.signOut();
            }
            }
        });
    }
}

