package ch.hevs.swap.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ch.hevs.swap.R;
import ch.hevs.swap.ui.data.models.User;
import ch.hevs.swap.ui.homepage.HomePage;

public class LoginEditProfil extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mNameField;
    private EditText mFirstNameField;
    private EditText mTelField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_edit_profil);

        // Fields
        mEmailField = findViewById(R.id.LoginEP_Email);
        mPasswordField = findViewById(R.id.LoginEP_Password);
        mNameField = findViewById(R.id.LoginEP_Name);
        mFirstNameField = findViewById(R.id.LoginEP_FirstName);
        mTelField = findViewById(R.id.LoginEP_Phone);

        //Button
        findViewById(R.id.LoginEP_CreateLogin).setOnClickListener(this);


        // FireBase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

    }


    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.LoginEP_CreateLogin) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());

        }
    }

    private void createAccount(String email, String password) {

        if (!validateForm()) {
            return;
        }

        // showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            writeNewUser(user.getUid(),user.getEmail(),mTelField.getText().toString(),mNameField.getText().toString(),mFirstNameField.getText().toString());
                            Toast.makeText(LoginEditProfil.this, getResources().getString(R.string.LoginEP_AccountCreated),
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginEditProfil.this, HomePage.class);
                            intent.setFlags(
                                    Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                            Intent.FLAG_ACTIVITY_NO_HISTORY
                            );
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginEditProfil.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        // [END create_user_with_email]
    }
    private void writeNewUser(String userId, String email, String phone,String name,String firstname) {
        User user = new User(name,firstname,email,phone);
        mDatabase.child("users").child(userId).setValue(user);
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
}