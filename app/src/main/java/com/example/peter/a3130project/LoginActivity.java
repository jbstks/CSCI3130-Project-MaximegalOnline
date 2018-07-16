/* Note, some portions of code are adapted from https://firebase.google.com/docs/auth/android/password-auth */
package com.example.peter.a3130project;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



/** LoginActivity
 *
 * Main entry point for sign-in
 * @author Peter Lee
 * @author Aecio Cavalcanti
 */
public class LoginActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    // UI references
    private EditText et_password;
    private EditText et_email;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        et_password = (EditText) findViewById(R.id.et_password);

        // TODO: change this
        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        et_email = findViewById(R.id.et_email);
    }

    /**loginButtonClick
     *  Called on email_signin_button
     *  Initiates login
     *  @param view
     */
    public void loginButtonClick(View view) {
        View curview = this.getCurrentFocus();
        if (curview != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        attemptLogin();
    }

    /**
     * Check if user is signed in (non-null) and update UI accordingly
     */
    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /** updateUI
     *
     * Decides whether to switch intent to main activity
     *
     * @param user
     *        the current session, or null if there is none
     */
    private void updateUI(FirebaseUser user) {
        if (user == null) {
            setContentView(R.layout.activity_login);
        } else {
            Intent intent = new Intent(this, TermActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Takes the email and password stores in fields and attempts to login
     */
    private void attemptLogin() {
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        // Reset errors
        et_email.setError(null);
        et_password.setError(null);

        // Store values at the time of the login attempt
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        Log.d("email123456:", " "+email);
        Log.d("password123456:",  " "+password);

        /* Evaluate login result*/
        switch(LoginChecker.checkLogin(email,password)) {
            case EMPTY_USER:
                    Log.d("emaillen", "0");

                    et_email.setError((CharSequence) getString(R.string.error_field_required),null);
                    et_email.requestFocus();
            break;

	        case EMPTY_PASSWORD:
                et_password.setError((CharSequence) getString(R.string.error_field_required), null);

                et_password.requestFocus();
                Log.d("passwlen", "0");
            break;

	        case SHORT_USER:
                Log.d("emaillen", "less8");
                et_email.setError((CharSequence) getString(R.string.error_invalid_email),null);
                et_email.requestFocus();
		    break;

	        case SHORT_PASSWORD:
                Log.d("passwlen", "less8");
                et_password.setError((CharSequence) getString(R.string.error_invalid_password), null);
                et_password.requestFocus();
		    break;

	        case OK:
	            // TODO: refactor this to avoid lambda function
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("B", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("A", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
			    });

            /* Get the currentuser, if logged in and retry */
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
	    }
    }
    
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}

