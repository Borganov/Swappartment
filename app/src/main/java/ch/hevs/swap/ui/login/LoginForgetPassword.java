package ch.hevs.swap.ui.login;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

import ch.hevs.swap.R;

public class LoginForgetPassword extends AppCompatActivity  implements View.OnClickListener {

    private Button mSendLinkButton;
    private EditText mEmailField;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget_password);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mSendLinkButton = findViewById(R.id.LoginFP_ForgetPassword);
        mEmailField = findViewById(R.id.LoginFP_Email);

        mSendLinkButton.setOnClickListener(this);

        String email = getIntent().getStringExtra("email");
        mEmailField.setText(email);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LoginFP_ForgetPassword:
                onSendLinkClicked();
                break;
        }
    }
    private void onSendLinkClicked() {
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Email must not be empty.");

            return;
        }

        sendSignInLink(email);
    }
    private void sendSignInLink(String email) {
        ActionCodeSettings settings = ActionCodeSettings.newBuilder()
                .setAndroidPackageName(
                        getPackageName(),
                        false, /* install if not available? */
                        null   /* minimum app version */)
                .setHandleCodeInApp(true)
                .setUrl("https://swap-appartements.firebaseapp.com")
                .build();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete( Task<Void> task) {

                        if (task.isSuccessful()) {
                            showSnackbar(getResources().getString(R.string.action_send_mail_success));
                        } else {
                            Exception e = task.getException();
                            showSnackbar(getResources().getString(R.string.action_send_mail_failed));

                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                mEmailField.setError(getResources().getString(R.string.action_send_mail_unknown_address));
                            }
                        }
                    }
                });


    }
    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}
