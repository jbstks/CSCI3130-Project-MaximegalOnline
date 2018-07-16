package com.example.peter.a3130project;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;

public class ResetPasswordActivity {
    private String newPassword, email, oldPassword;
    private FirebaseUser user;
    private EditText emailField, passwordField;

    protected void onCreate(Bundle savedInstanceState) {
    }

    public void clickReset() {
        
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
