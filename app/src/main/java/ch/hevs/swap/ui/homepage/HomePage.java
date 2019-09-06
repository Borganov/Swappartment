package ch.hevs.swap.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


import ch.hevs.swap.R;
import ch.hevs.swap.data.models.UserController;
import ch.hevs.swap.ui.login.LoginEditProfil;
import ch.hevs.swap.ui.login.LoginForgetPassword;
import ch.hevs.swap.ui.search.SearchApart;


public class HomePage extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPasswordField;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    FirebaseUser user;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Fields
        mEmailField = findViewById(R.id.HomePage_Email);
        mPasswordField = findViewById(R.id.HomePage_Password);
        // Buttons
        findViewById(R.id.HomePage_Login).setOnClickListener(this);
        findViewById(R.id.HomePage_CreateLogin).setOnClickListener(this);
        findViewById(R.id.HomePage_ForgetPassword).setOnClickListener(this);
   //     mDatabase = FirebaseDatabase.getInstance().getReference();
       // FirebaseAuth.getInstance().signOut();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        updateUI(user);
        if(user != null)
        {

            Intent intent = new Intent(HomePage.this, SearchApart.class);

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NO_ANIMATION |
                            Intent.FLAG_ACTIVITY_NO_HISTORY
            );

            startActivity(intent);
        }
    }
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.HomePage_CreateLogin) {
            Intent intent = new Intent(HomePage.this, LoginEditProfil.class);
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NO_ANIMATION |
                            Intent.FLAG_ACTIVITY_NO_HISTORY
            );
            startActivity(intent);

        }else if (i == R.id.HomePage_ForgetPassword)
        {
            Intent intent = new Intent(HomePage.this, LoginForgetPassword.class);
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NO_ANIMATION |
                            Intent.FLAG_ACTIVITY_NO_HISTORY
            );
           // if(!mEmailField.getText().toString().isEmpty())
            intent.putExtra("email", mEmailField.getText());

            startActivity(intent);
        } else if (i == R.id.HomePage_Login)
        {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());

//            user = mAuth.getCurrentUser();

        }
    }

    private void signIn(String email, String password) {

        if (!validateForm()) {
            return;
        }
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            user = mAuth.getCurrentUser();
                            updateUI(user);

                                Intent intent = new Intent(HomePage.this, SearchApart.class);
                                intent.setFlags(
                                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                                Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(HomePage.this,"Try Again",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                   //         mStatusTextView.setText(R.string.auth_failed);
                        }

                    }
                });
        // [END sign_in_with_email]
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {


            Toast.makeText(HomePage.this, getResources().getString(R.string.connected) + user.getEmail(),
                    Toast.LENGTH_SHORT).show();

        }
    }
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    public void onBackPressed() {
        this.startActivity(new Intent(this, HomePage.class));
    }
}
